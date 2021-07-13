package fastcampus.aop.part2.ch2.randomnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val random = java.util.Random()
        val numberSet = mutableSetOf<Int>()
        while(numberSet.size < 6) {
            val randomNo = random.nextInt(45) + 1
            numberSet.add(randomNo)
        }

        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45){this.add(i)}
        }
        numberList.shuffle()

        numberList.subList(0, 6)


    }
}