package cp.kt_toy.androidbasics.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cp.kt_toy.androidbasics.R
import cp.kt_toy.androidbasics.databinding.Lifecycle2ActivityBinding

class Lifecycle2Activity : AppCompatActivity() {
    private val tagLifecycle: String = "LifecycleLogger"
    private lateinit var binding: Lifecycle2ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tagLifecycle, "Activity 2.onCreate()")
        super.onCreate(savedInstanceState)
        binding = Lifecycle2ActivityBinding.inflate(layoutInflater)
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
        Log.d(tagLifecycle, "Activity 2.onStart()")
        super.onStart()
    }

    override fun onResume() {
        Log.d(tagLifecycle, "Activity 2.onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.d(tagLifecycle, "Activity 2.onPause()")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tagLifecycle, "Activity 2.onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(tagLifecycle, "Activity 2.onDestroy()")
        super.onDestroy()
    }
}