package io.valueof.constraintlayoutanimation.ui.main

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import io.valueof.constraintlayoutanimation.R
import io.valueof.constraintlayoutanimation.databinding.MainFragmentBinding
import kotlinx.coroutines.delay

private const val BANNER_ANIMATION_DELAY = 1000L
private const val BANNER_ANIMATION_DURATION = 800L

class MainFragment : Fragment(R.layout.main_fragment) {

  companion object {
    fun newInstance() = MainFragment()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val binding = MainFragmentBinding.bind(view)

    val animateBannerUpConstraintSet = ConstraintSet().apply {
      clone(binding.constraintLayout)
      connect(
        binding.animatedBanner.id,
        ConstraintSet.BOTTOM,
        ConstraintSet.PARENT_ID,
        ConstraintSet.TOP,
        0
      )
      clear(binding.animatedBanner.id, ConstraintSet.TOP)
    }

    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
      delay(BANNER_ANIMATION_DELAY)
      val transition = AutoTransition().apply {
        interpolator = AccelerateDecelerateInterpolator()
        duration = BANNER_ANIMATION_DURATION
      }

      TransitionManager.beginDelayedTransition(binding.constraintLayout, transition)
      animateBannerUpConstraintSet.applyTo(binding.constraintLayout)
    }
  }
}