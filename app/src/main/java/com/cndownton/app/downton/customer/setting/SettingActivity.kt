package com.cndownton.app.downton.customer.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cndownton.app.downton.R

class SettingActivity : AppCompatActivity() {

    private lateinit var mSettingPresenter:SettingPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}
