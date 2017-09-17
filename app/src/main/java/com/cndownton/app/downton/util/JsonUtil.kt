package com.cndownton.app.downton.util

import com.google.gson.JsonParser
import com.google.gson.Gson



/**
 * Created by Mpf on 2017/9/17.
 */
object JsonUitl {

    private val mGson = Gson()

    /**
     * 将json字符串转化成实体对象
     * @param json
     * @param classOfT
     * @return
     */
    fun stringToObject(json: String?, classOfT: Class<*>): Any {
        return mGson.fromJson(json, classOfT)
    }

    /**
     * 将对象准换为json字符串 或者 把list 转化成json
     * @param object
     * @param <T>
     * @return
    </T> */
    fun <T> objectToString(`object`: T): String {
        return mGson.toJson(`object`)
    }

    /**
     * 把json 字符串转化成list
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> stringToList(json: String, cls: Class<T>): List<T> {
        val gson = Gson()
        val list = ArrayList<T>()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

}