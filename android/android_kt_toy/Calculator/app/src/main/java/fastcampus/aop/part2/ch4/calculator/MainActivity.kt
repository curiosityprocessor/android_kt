package fastcampus.aop.part2.ch4.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import fastcampus.aop.part2.ch4.calculator.model.History

class MainActivity : AppCompatActivity() {

    private val expressionTextView: TextView by lazy {
        findViewById(R.id.expressionTextView)
    }
    private val resultTextView: TextView by lazy {
        findViewById(R.id.resultTextView)
    }
    private val historyLayout: View by lazy {
        findViewById(R.id.historyLayout)
    }
    private val historyLinearLayout: LinearLayout by lazy {
        findViewById(R.id.historyLinearLayout)
    }

    lateinit var db: AppDatabase

    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()
    }

    fun buttonClicked(view: View){
//        when(view.id) {
//            R.id.no0 -> numberButtonClicked()
//
//        }
        val clickedButton: Button = view as Button
        val param: String = clickedButton.text.toString()
        Log.d("validateCalc", "buttonClicked() $param")
        val paramToInt = param.toIntOrNull()
        when(paramToInt) {
            null -> operatorButtonClicked(param) //toInt 실패 = 연산자
            else -> numberButtonClicked(param) //toInt 성공 = 숫자 키
        }
    }


    private fun numberButtonClicked(number:String) {
        if(isOperator){
            //연산자 다음 숫자에는 앞에 공백 추가
            expressionTextView.append(" ")
        }
        isOperator = false
        val expressionTexts = expressionTextView.text.split(" ")
        Log.d("validateCalc","expressionText: $expressionTexts")
        if(expressionTexts.isNotEmpty() && expressionTexts.last().length >= 15){
            Toast.makeText(this, "15자리 까지만 사용 가능", Toast.LENGTH_SHORT).show()
            return
        } else if(expressionTexts.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 사용 불가", Toast.LENGTH_SHORT).show()
            return
        }

        //정상적인 경우 text view 에 클릭한 숫자 append
        expressionTextView.append(number)

        //연산 결과 result view에 표현
        resultTextView.text = calculateExpression()
    }

    private fun operatorButtonClicked(operator: String) {
        if(expressionTextView.text.isEmpty()){
            //연산자로 시작 불가 = return 예외처리
            Log.d("validateCalc", "연산자로 시작 불가")
            return
        }

        when {
            isOperator -> {
                //숫자 -> 연산자 -> 연산자 case
                val text = expressionTextView.text.toString()
                Log.d("validateCalc", "last operator: ${text.substring(text.length-1)}")
                Log.d("validateCalc", "before: $text")
                expressionTextView.text = text.dropLast(1) + operator //연산자 연달아 사용 시
                Log.d("validateCalc", "after: ${expressionTextView.text}")
            }
            hasOperator -> {
                //숫자 -> 연산자 -> 숫자 -> 연산자 case
                Toast.makeText(this, "연산자는 한번만 사용 가능", Toast.LENGTH_SHORT).show()
                return
            }
            else -> expressionTextView.append(" $operator")
        }

        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTextView.text.length - 1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true
    }

    fun clearButtonClicked(view: View){
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    fun historyButtonClicked(view:View){
        historyLayout.isVisible = true

        //기존 historyLinearLayout 하위에 있는 모든 view 제거 해 초기화
        historyLinearLayout.removeAllViews()

        // db에서 모든 기록 조회
        Thread(Runnable {
            db.historyDao().getAll().reversed().forEach{
                runOnUiThread {
                    //동적으로 VIEW 생성
                    val historyView = LayoutInflater.from(this).inflate(R.layout.history_row, null, false)

                    //db에서 조회해온 값으로 데이터 바인딩
                    historyView.findViewById<TextView>(R.id.expressionTextView).text = it.expression
                    historyView.findViewById<TextView>(R.id.resultTextView).text = "= ${it.result}"

                    //생성한 view ui에 추가
                    historyLinearLayout.addView(historyView)
                }
            }
        }).start()

    }

    fun closeHistoryButtonClicked(view:View){
        historyLayout.isVisible = false
    }

    fun clearHistoryButtonClicked(view:View){
        //view에서 모든 기록 삭제
        historyLinearLayout.removeAllViews()

        //db에서 모든 기록 삭제
        Thread(Runnable {
            db.historyDao().deleteAll()
        }).start()
    }

    fun resultButtonCLicked(view:View){
        val expressionTexts = expressionTextView.text.split(" ")
        if(expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }
        if(expressionTexts.size < 3 && hasOperator) {
            Toast.makeText(this, "아직 완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            //앞단에서 막혀서 여기까지 오면 안됨
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        //새로운 thread 생성해 result db에 저장 작업 처리
        Thread(Runnable {
            db.historyDao().insertHistory(History(null, expressionText, resultText))
        }).start()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false
    }

    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")
        if(hasOperator.not() || expressionTexts.size != 3) {
            //예외처리: 연산자를 사용한 적이 없거나 3개 요소로 구성되지 않은 경우
            return ""
        } else if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            //예외처리: [0], [2] 요소가 숫자가 아닌 경우
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val operator = expressionTexts[1]

        return when(operator) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> "" //허용되지 않는 연사자 사용 시
        }
    }


}

fun String.isNumber() : Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}