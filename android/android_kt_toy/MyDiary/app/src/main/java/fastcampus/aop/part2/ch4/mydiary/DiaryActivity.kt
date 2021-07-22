package fastcampus.aop.part2.ch4.mydiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {
    private val diaryEditText:EditText by lazy {
        findViewById<EditText>(R.id.diaryEditText)
    }
    
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("  diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail",""))

        val runnable = Runnable {
            detailPreferences.edit {
                putString("detail", diaryEditText.text.toString())
            }
            Log.d("TextChange", "Saved:: ${diaryEditText.text.toString()}")
        }

        diaryEditText.addTextChangedListener {
            Log.d("TextChange", "TextChanged:: $it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }

    }
}