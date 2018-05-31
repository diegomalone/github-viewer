package com.diegomalone.githubviewer.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.diegomalone.githubviewer.R
import kotlinx.android.synthetic.main.view_list_title.view.*

class ListTitleCard : FrameLayout {

    var title: String? = null
        set(value) {
            field = value
            updateTitle()
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_list_title, this, true)
    }

    private fun updateTitle() {
        listTitle.text = title
    }
}