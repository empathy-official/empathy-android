package com.empathy.empathy_android.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String = "", frameId: Int)
        = supportFragmentManager.beginTransaction()
        .replace(frameId, fragment)
        .commit()