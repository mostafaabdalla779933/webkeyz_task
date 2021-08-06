package com.example.webkeyz_task.util

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

// get progressbar as drawable
fun getLoading(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}


// add ripple effect to the view
fun setClickableAnimation(context: Context, view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground, outValue, true
        )
        view.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
    }
}