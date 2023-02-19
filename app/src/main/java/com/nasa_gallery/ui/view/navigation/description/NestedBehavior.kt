package com.nasa_gallery.ui.view.navigation.description

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.nasa_gallery.R

class NestedBehavior(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout
.Behavior<NestedScrollView>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        return (dependency.id == R.id.app_bar)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.app_bar) {
            child.y = dependency.y + dependency.height

        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}