package com.empathy.empathy_android.ui.myfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.empathy.empathy_android.R
import kotlinx.android.synthetic.main.fragment_notification.*

internal class NotificationFragment : DialogFragment() {

    interface OnItemDeleteListener {
        fun onDeleteClicked()
    }

    private var listener: OnItemDeleteListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_notification, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListener()
    }

    fun setOnDeleteListener(listener: OnItemDeleteListener) {
        this.listener = listener
    }

    private fun initializeListener() {
        cancel.setOnClickListener {
            dismiss()
        }

        delete.setOnClickListener {
            listener?.onDeleteClicked()
        }
    }
}