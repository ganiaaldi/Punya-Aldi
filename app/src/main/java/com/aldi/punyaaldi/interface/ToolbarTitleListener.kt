package com.aldi.punyaaldi.`interface`

import android.graphics.drawable.Drawable
import android.view.View

interface ToolbarTitleListener {

    fun updateTitle(title: String)
    fun toolbarAction(onClickListener: View.OnClickListener)
    fun updateNavIcon(drawable: Drawable)
    fun showToolbar(show: Boolean)
}