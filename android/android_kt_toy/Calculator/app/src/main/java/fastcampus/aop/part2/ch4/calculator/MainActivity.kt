package fastcampus.aop.part2.ch4.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val expressionTextView: TextView by lazy {
        findViewById(R.id.expressionTextView)
    }
    private val resultTextView: TextView by lazy {
        findViewById(R.id.resultTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var isOperator = false
    private var hasOperator = false
    
    fun buttonClicked(view: View){
//        when(view.id) {
//            R.id.no0 -> numberButtonClicked()
//
//        }
        val tmpButton: Button = view as Button
        val param: String = tmpButton.text.toString()
        Log.d("validateCalc", "buttonClicked() $param")
        val paramToInt = param.toIntOrNull()
        when(paramToInt) {
            null -> operatorButtonClicked(param)
            else -> numberButtonClicked(param)
        }
    }


    private fun numberButtonClicked(number:String) {
        if(isOperator){
            expressionTextView.append(" ")
        }
        isOperator = false
        val expressionText = expressionTextView.text.split(" ")
        Log.d("validateCalc","expressionText: $expressionText")
        if(expressionText.isNotEmpty() && expressionText.last().length >= 15){
            Toast.makeText(this, "15자리 까지만 사용 가능", Toast.LENGTH_SHORT).show()
            return
        } else if(expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 사용 불가", Toast.LENGTH_SHORT).show()
            return
        }

        expressionTextView.append(number)

        //TODO resultTextView 에 실시간 계산 결과 표현
    }

    private fun operatorButtonClicked(operator: String) {
        if(expressionTextView.text.isEmpty()){
            return
        }

        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }
            hasOperator -> {
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

    }

    fun historyButtonClicked(view:View){

    }

    fun resultButtonCLicked(view:View){

    }
}