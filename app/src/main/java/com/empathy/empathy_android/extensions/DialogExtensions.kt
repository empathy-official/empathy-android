package com.empathy.empathy_android.extensions

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.showDialogFragment(fragment: Fragment, tag: String = "", commitAllowStateLoss: Boolean = true) {
    val fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .add(fragment, tag)

    if(commitAllowStateLoss) {
        fragmentTransaction.commitAllowingStateLoss()
    } else {
        fragmentTransaction.commit()
    }
}

fun FragmentActivity.hideDialogFragment(vararg tags: String) {
    val fragmentList = mutableListOf<DialogFragment>()

    tags.forEach {
        val fragment = supportFragmentManager.findFragmentByTag(it)

        if(fragment != null && fragment is DialogFragment) {
            fragmentList.add(fragment)
        }
    }

    fragmentList.forEach {
        it.dismissAllowingStateLoss()
    }
}
