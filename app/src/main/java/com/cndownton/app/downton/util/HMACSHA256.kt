package com.cndownton.app.downton.util

import android.content.Context

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and
import com.tencent.wxop.stat.common.l.n



/**
* Created by Administrator_mpf_mpf on 2017/9/16.
*/

object HMACSHA256 {
    //   SECRET KEY
    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private fun byteArrayToHexString(b: ByteArray?): String {
        val hs = StringBuilder()
        var stmp: String
        var n = 0
        while (b != null && n < b.size) {
            stmp = Integer.toHexString(b[n].toInt() and 0XFF)
            if (stmp.length == 1)
                hs.append('0')
            hs.append(stmp)
            n++
        }
        return hs.toString().toLowerCase()
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     *
     * @return 加密后字符串
     */
    fun sha256_HMAC(context: Context, message: String): String {
        var hash = ""
        try {
            val sha256_HMAC = Mac.getInstance("HmacSHA256")
            val secret_key = SecretKeySpec(CommonUtil.getAssetsFile(context).toByteArray(), "HmacSHA256")
            sha256_HMAC.init(secret_key)
            val bytes = sha256_HMAC.doFinal(message.toByteArray())
            hash = byteArrayToHexString(bytes)
            println(hash)
        } catch (e: Exception) {
            println("Error HmacSHA256 ===========" + e.message)
        }

        return hash
    }
}