package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.empathy.empathy_android.R

internal class PartnerFragment: Fragment() {

    companion object {
        fun newInstance() = PartnerFragment().apply {
            arguments = Bundle().apply {
//                putInt()
            }
        }
    }

    private lateinit var containerView: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments.getInt()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewId = when(1) {
            0    -> R.layout.fragment_partner_type_a
            1    -> R.layout.fragment_partner_type_b
            else -> R.layout.fragment_partner_type_a
        }

        return inflater.inflate(viewId, container, false).apply {
            containerView = findViewById(R.id.container_view)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListener()
    }

    private fun initializeListener() {
        containerView.setOnClickListener {
            
        }
    }

}
