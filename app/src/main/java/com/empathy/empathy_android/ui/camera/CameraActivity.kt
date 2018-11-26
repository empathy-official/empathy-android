package com.empathy.empathy_android.ui.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.empathy.empathy_android.R

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        if (null == savedInstanceState) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, CameraBasicFragment.newInstance())
                    .commit()
        }
    }
}
