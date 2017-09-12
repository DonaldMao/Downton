package com.cndownton.app.downton.customer.setting


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cndownton.app.downton.R


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment(),SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_setting, container, false)
    }

}// Required empty public constructor
