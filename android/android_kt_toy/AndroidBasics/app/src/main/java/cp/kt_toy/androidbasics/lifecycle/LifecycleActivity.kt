package cp.kt_toy.androidbasics.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cp.kt_toy.androidbasics.R
import cp.kt_toy.androidbasics.databinding.LifecycleActivityBinding

class LifecycleActivity : AppCompatActivity() {
    private val tagLifecycle: String = "LifecycleLogger"
    private lateinit var binding: LifecycleActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tagLifecycle, "${this.javaClass.simpleName}.onCreate()")
        super.onCreate(savedInstanceState)
        binding = LifecycleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity1.setOnClickListener {
            startActivity(Intent(this, Lifecycle1Activity::class.java))
        }
        binding.activity2.setOnClickListener {
            startActivity(Intent(this, Lifecycle2Activity::class.java))
        }
        binding.activity3.setOnClickListener {
            startActivity(Intent(this, Lifecycle3Activity::class.java))
        }
    }

    override fun onStart() {
        Log.d(tagLifecycle, "${this.javaClass.simpleName}.onStart()")
        super.onStart()
    }

    override fun onResume() {
        Log.d(tagLifecycle, "${this.javaClass.simpleName}.onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.d(tagLifecycle, "${this.javaClass.simpleName}.onPause()")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tagLifecycle, "${this.javaClass.simpleName}.onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(tagLifecycle, "${this.javaClass.simpleName}.onDestroy()")
        super.onDestroy()
    }
}