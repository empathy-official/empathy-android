package com.empathy.empathy_android.ui.mypage

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class MyPageActivity: BaseActivity<MyPageViewModel.ViewModel>() {


    override fun getLayoutRes(): Int = R.layout.activity_my_page
    override fun getViewModel(): Class<MyPageViewModel.ViewModel> = MyPageViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}