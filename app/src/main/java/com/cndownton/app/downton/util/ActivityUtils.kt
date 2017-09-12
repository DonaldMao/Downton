package com.cndownton.app.downton.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by Administrator_mpf on 2017/9/12.
 */
object ActivityUtils{
    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId:Int) {

        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

}