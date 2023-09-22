package com.nasa_gallery.ui.pages.navigation.description

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.nasa_gallery.R
import java.lang.Math.abs

class MyBehaviorButton(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout
.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency.id == R.id.app_bar)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.app_bar) {

            child.y = dependency.y + dependency.height - child.height / 2
            child.x = (dependency.width - child.width).toFloat()
            child.alpha = 1 - (abs(dependency.y) / (dependency.height / 2))

        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}