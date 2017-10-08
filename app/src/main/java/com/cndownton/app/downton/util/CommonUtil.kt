package com.cndownton.app.downton.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
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
    fun getCurrentTime2(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
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
    fun getRealShaStr(vararg args:String): String {
        val result= StringBuilder()
        Arrays.sort(args)
        for(item in args){
            result.append("&")
            result.append(item)
        }
        result.deleteCharAt(0)
        return result.toString().toUpperCase()
    }

}