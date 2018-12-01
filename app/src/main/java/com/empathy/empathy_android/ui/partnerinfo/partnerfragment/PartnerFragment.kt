package com.empathy.empathy_android.ui.partnerinfo.partnerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.empathy.empathy_android.BaseFragment
import com.empathy.empathy_android.R
import com.empathy.empathy_android.extensions.loadImage
import com.empathy.empathy_android.extensions.observe
import com.empathy.empathy_android.http.appchannel.FragmentLifeCycle
import kotlinx.android.synthetic.main.fragment_partner_type_a.*
import java.util.*

internal class PartnerFragment: BaseFragment() {

    companion object {
        fun newInstance() = PartnerFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    private lateinit var backgroundDeco: ImageView

    private var isTypeB = false

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel(PartnerViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewId = when(Random().nextInt(2)) {
            0    -> R.layout.fragment_partner_type_a
            1    -> {
                isTypeB = true

                R.layout.fragment_partner_type_b
            }
            else -> R.layout.fragment_partner_type_a
        }

        return inflater.inflate(viewId, container, false).apply {
            if(isTypeB) {
                backgroundDeco = findViewById(R.id.partner_deco_image)

                val decoResource = when(Random().nextInt(4)) {
                    0    -> R.drawable.mask_r
                    1    -> R.drawable.mask_g
                    2    -> R.drawable.mask_b
                    3    -> R.drawable.mask_p
                    else -> R.drawable.mask_r
                }

                backgroundDeco.setImageResource(decoResource)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeLooknFeel()

        viewModel.channel.accept(FragmentLifeCycle.OnActivityCreated())
    }

    private fun subscribeLooknFeel() {
        observe(viewModel.showPartnerInfo, ::handleShowPartnerInfo)
    }

    private fun handleShowPartnerInfo(looknFeel: PartnerLooknFeel.ShowPartnerInfo) {
        val partner = looknFeel.partner

        partner_image.loadImage(partner.imageURL)

        title.text = partner.name
        category.text = partner.kind
        address.text = partner.locatiionStr
    }

    private fun initializeListener() {
        container_view.setOnClickListener {

        }
    }

}
