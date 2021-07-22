package fastcampus.aop.part2.ch4.mydiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    //view 가 완전히 생성되기 전에 선언하기 때문에 lazy init
    private val numberPicker1:NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker2:NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker3:NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePwdButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePwdButton)
    }

    private var changePwdMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //lazy init 된 numberpicker를 호출해 실제로 init 처리
        numberPicker1
        numberPicker2
        numberPicker3

        //lazy init & bind event to buttons
        openButton.setOnClickListener {
            if(changePwdMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            //string template & string concat
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            //retrieve value from preferences with key
            val password = passwordPreferences.getString("password", "000")

            if(password.equals(passwordFromUser)) {
                //success
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                //fail
                showErrorAlertDialog()
            }
        }

        changePwdButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            val password = passwordPreferences.getString("password", "000")
            if(changePwdMode){
                //설정한 번호 저장
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }

                changePwdMode = false
                changePwdButton.setBackgroundColor(Color.BLACK)
            } else {
                if(password.equals(passwordFromUser)) {
                    //success
                    changePwdMode = true
                    Toast.makeText(this, "변경할 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    changePwdButton.setBackgroundColor(Color.RED)
                } else {
                    //fail
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> } //no further action on positive button
            .create()
            .show()
    }
}