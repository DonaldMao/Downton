package com.cndownton.app.downton

import android.support.v4.app.Fragment

/**
 * Created by Mpf on 2017/9/24.
 */
abstract class BaseFragment: Fragment() {
     abstract fun onBackPressed(): Boolean

}