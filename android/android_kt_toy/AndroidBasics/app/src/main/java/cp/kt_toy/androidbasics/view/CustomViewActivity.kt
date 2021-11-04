package cp.kt_toy.androidbasics.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cp.kt_toy.androidbasics.R
import cp.kt_toy.androidbasics.databinding.CustomViewActivityBinding

class CustomViewActivity : AppCompatActivity() {

    private val binding: CustomViewActivityBinding by lazy {
        CustomViewActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rateStarIndicator.apply {
            this.setOnClickListener {
                var current = this.getSelected()
                when(current) {
                    4 -> this.setSelected(0)
                    else -> this.setSelected(++current)
                }
            }
        }
    }
}