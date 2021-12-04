package com.githubuserprofile.view.utils

import android.view.View

fun View.visible(isVisible: Boolean, ifNot: Int = View.GONE) {
    visibility = if (isVisible) View.VISIBLE else ifNot
}

