package com.cndownton.app.downton.data.bean

import java.util.*

/**
 * Created by Administrator_mpf on 2017/9/20.
 */
data class WXUserInfo(val openid:String, val nickname:String, val sex:Int, val province:String,
                      val city:String, val country:String, val headimgurl:String, val privilege:Array<String>,
                      val unionid:String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WXUserInfo

        if (openid != other.openid) return false
        if (nickname != other.nickname) return false
        if (sex != other.sex) return false
        if (province != other.province) return false
        if (city != other.city) return false
        if (country != other.country) return false
        if (headimgurl != other.headimgurl) return false
        if (!Arrays.equals(privilege, other.privilege)) return false
        if (unionid != other.unionid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = openid.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + sex
        result = 31 * result + province.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + headimgurl.hashCode()
        result = 31 * result + Arrays.hashCode(privilege)
        result = 31 * result + unionid.hashCode()
        return result
    }
}

//{
//    "openid":"OPENID",
//    "nickname":"NICKNAME",
//    "sex":1,
//    "province":"PROVINCE",
//    "city":"CITY",
//    "country":"COUNTRY",
//    "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//    "privilege":[
//    "PRIVILEGE1",
//    "PRIVILEGE2"
//    ],
//    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//
//}