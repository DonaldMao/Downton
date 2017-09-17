package com.cndownton.app.downton.util

import android.provider.SyncStateContract.Helpers.update
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and


/**
 * Created by Administrator_mpf on 2017/9/16.
 */
class EncryptUtil {
    fun shaEncrypt(strSrc: String): String? {
        val md: MessageDigest?
        val strDes: String?
        val bt = strSrc.toByteArray()
        try {
            md = MessageDigest.getInstance("SHA-256")// 将此换成SHA-1、SHA-512、SHA-384等参数
            md!!.update(bt)
            strDes = bytes2Hex(md.digest()) // to HexString
        } catch (e: NoSuchAlgorithmException) {
            return null
        }

        return strDes
    }

    private fun bytes2Hex(bts: ByteArray): String {
        var des = ""
        var tmp: String?
        for (i in bts.indices) {
            tmp = Integer.toHexString(((bts[i] and 0xFF.toByte()).toInt()))
            if (tmp!!.length == 1) {
                des += "0"
            }
            des += tmp
        }
        return des
    }
}