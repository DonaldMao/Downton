package com.cndownton.app.downton.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mpf on 2017/9/17.
 */
object CommonUtil {
    fun getCurrentTime(): String {
        val format = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        return format.format(System.currentTimeMillis())
    }
}