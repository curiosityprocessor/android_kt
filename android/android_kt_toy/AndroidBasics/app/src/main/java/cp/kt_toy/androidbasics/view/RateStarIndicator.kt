package cp.kt_toy.androidbasics.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import cp.kt_toy.androidbasics.R
import cp.kt_toy.androidbasics.databinding.RateStarIndicatorBinding

class RateStarIndicator(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private lateinit var binding: RateStarIndicatorBinding
    private var selectedView: Int = 0
    private val tagRateStar: String = "RateStarLogger"

    init {
        initialiseViews(context, attrs)
    }

    /**
     * layout initialisation
     */
    private fun initialiseViews(context: Context, attrs: AttributeSet?){
        Log.d(tagRateStar, "RateStarIndicator.initialiseViews(context=$context, attrs=$attrs)")
        val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RateStarIndicatorBinding.inflate(inflater, this, true)
        attrs?.let{
            val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RateStarIndicator)
            selectedView = attributes.getInteger(0, 0)
            attributes.recycle()
        }
    }

    /**
     * callback once layout inflate finishes
     */
    override fun onFinishInflate() {
        Log.d(tagRateStar, "RateStarIndicator.onFinishInflate()")

        super.onFinishInflate()
        star1 = binding.star1
        star2 = binding.star2
        star3 = binding.star3
        star4 = binding.star4
        star5 = binding.star5
        setSelected(selectedView, true)
    }

    /**
     * select view by its number (internal use)
     */
    private fun setSelected(paramSelected:Int, isForceUpdate:Boolean) {
        Log.d(tagRateStar, "RateStarIndicator.setSelected(paramSelected=$paramSelected, isForceUpdate=$isForceUpdate)")
        if(isForceUpdate || paramSelected != selectedView) {
            if(paramSelected > 4 || paramSelected < 0) {
                Log.d(tagRateStar, "invalid param:$paramSelected")
                return
            }
            selectedView = paramSelected
            when(selectedView) {
                0 -> {
                    star1.setImageResource(R.drawable.ic_baseline_star_24)
                    star2.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    star3.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    star4.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    star5.setImageResource(R.drawable.ic_baseline_star_outline_24)
                }
                1 -> {
                    star1.setImageResource(R.drawable.ic_baseline_star_24)
                    star2.setImageResource(R.drawable.ic_baseline_star_24)
                    star3.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    star4.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    star5.setImageResource(R.drawable.ic_baseline_star_outline_24)
                }
                2 -> {
                    star1.setImageResource(R.drawable.ic_baseline_star_24)
                    star2.setImageResource(R.drawable.ic_baseline_star_24)
                    star3.setImageResource(R.drawable.ic_baseline_star_24)
                    star4.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    star5.setImageResource(R.drawable.ic_baseline_star_outline_24)
                }
                3 -> {
                    star1.setImageResource(R.drawable.ic_baseline_star_24)
                    star2.setImageResource(R.drawable.ic_baseline_star_24)
                    star3.setImageResource(R.drawable.ic_baseline_star_24)
                    star4.setImageResource(R.drawable.ic_baseline_star_24)
                    star5.setImageResource(R.drawable.ic_baseline_star_outline_24)
                }
                4 -> {
                    star1.setImageResource(R.drawable.ic_baseline_star_24)
                    star2.setImageResource(R.drawable.ic_baseline_star_24)
                    star3.setImageResource(R.drawable.ic_baseline_star_24)
                    star4.setImageResource(R.drawable.ic_baseline_star_24)
                    star5.setImageResource(R.drawable.ic_baseline_star_24)
                }

            }
        }
    }

    /**
     * select view by its number
     */
    fun setSelected(paramSelected: Int) {
        setSelected(paramSelected, false)
    }

    fun getSelected(): Int {
        return selectedView
    }
}
