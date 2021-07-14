package fastcampus.aop.part2.ch2.randomnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }

    private var didRun = false

    private val pickedNumberSet = hashSetOf<Int>()

    private val numberTextViewlist: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.numTextView0),
            findViewById(R.id.numTextView1),
            findViewById(R.id.numTextView2),
            findViewById(R.id.numTextView3),
            findViewById(R.id.numTextView4),
            findViewById(R.id.numTextView5)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed{ index, number ->
                val textView = numberTextViewlist[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumberBg(number, textView)
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45){
                if(pickedNumberSet.contains(i)){
                    continue
                } else {
                    this.add(i)
                }
            }
        }
        numberList.shuffle()
        return pickedNumberSet.toList() + numberList.subList(0, 6 - pickedNumberSet.size).sorted()
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if(didRun) {
                Toast.makeText(this, getString(R.string.alert_try_after_init), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickedNumberSet.size >= 6) {
                Toast.makeText(this, getString(R.string.alert_choose_upto_3), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickedNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, getString(R.string.alert_alreay_chosen), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //예외처리 끝
            val textView = numberTextViewlist[pickedNumberSet.size]
            textView.text = numberPicker.value.toString()
            textView.isVisible = true
            setNumberBg(numberPicker.value, textView)

            pickedNumberSet.add(numberPicker.value)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickedNumberSet.clear()
            numberTextViewlist.forEach{
                it.isVisible = false
            }
            didRun = false
        }
    }

    private fun setNumberBg(number:Int, textView:TextView) {
        when(number) {
            in (1..10) -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in (11..20) -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in (21..30) -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in (31..40) -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }
}