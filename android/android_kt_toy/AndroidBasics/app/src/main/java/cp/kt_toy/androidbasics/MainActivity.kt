package cp.kt_toy.androidbasics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import cp.kt_toy.androidbasics.databinding.MainActivityBinding
import cp.kt_toy.androidbasics.lifecycle.LifecycleActivity

class MainActivity : AppCompatActivity() {

    private val log_this = "MainActivityLogger"
    private lateinit var binding: MainActivityBinding
    private lateinit var editText: EditText
    private lateinit var btnActivityLifecycle: Button
    private var textContent: String? = null

    /**
     * initialisation, view inflation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(log_this,"${this.javaClass.simpleName}.onCreate()")
        setTheme(R.style.Theme_AndroidBasics)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        initEvents()

    }

    /**
     * any task that needs to be called when app comes/returns to foreground
     * ex) refresh contents, start animation etc
     */
    override fun onResume() {
        Log.d(log_this,"${this.javaClass.simpleName}.onResume()")
        super.onResume()
    }

    /**
     * any data not saved will be lost after onPaused() reaches STOPPED state
     * save any necessary data to persistence level in this callback
     */
    override fun onPause() {
        Log.d(log_this,"${this.javaClass.simpleName}.onPause()")
        super.onPause()
    }

    /**
     * any task that needs to be called before app finishes
     * ex) close resources, prevent memory leaks etc
     */
    override fun onDestroy() {
        Log.d(log_this,"${this.javaClass.simpleName}.onDestroy()")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(log_this,"${this.javaClass.simpleName}.onSaveInstanceState()")
        super.onSaveInstanceState(outState)
        //save current input in bundle
        outState.putString("EDIT_TEXT_CONTENT", editText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(log_this,"${this.javaClass.simpleName}.onRestoreInstanceState()")
        super.onRestoreInstanceState(savedInstanceState)
        //get input from bundle
        editText.setText(savedInstanceState.getString("EDIT_TEXT_CONTENT", ""))
    }

    private fun initView(){
        editText = binding.editText
        btnActivityLifecycle = binding.btnActivityLifecycle
    }

    private fun initEvents(){
        btnActivityLifecycle.setOnClickListener {
            val intent = Intent(this, LifecycleActivity::class.java).apply {
//                putExtra()
//                addFlags()
            }
            startActivity(intent)
        }
    }
}