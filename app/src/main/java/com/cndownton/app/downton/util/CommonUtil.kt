package com.cndownton.app.downton.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Created by Mpf on 2017/9/17.
 */
object CommonUtil {
    fun getCurrentTime(): String {
        val format = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        return format.format(System.currentTimeMillis())
    }
    fun getAssetsFile(context: Context):String{
        try {
            val inputReader = InputStreamReader(context.resources.assets.open("ShaKey"))
            val bufReader = BufferedReader(inputReader)
            return bufReader.use(BufferedReader::readText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null.toString()
    }

}