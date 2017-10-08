package com.cndownton.app.downton

import android.app.Activity
import android.app.Application
import com.cndownton.app.downton.data.bean.UserInfo
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import permissions.dispatcher.RuntimePermissions
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


/**
 * Created by Administrator_mpf on 2017/9/13.
 */
class MyApplication : Application() {
    companion object {
        val APP_ID: String = "wx5269ea51c51983b1"
        lateinit var api: IWXAPI
        var user: UserInfo? = null
        var isLogin:Boolean=false
        var needFreshMeFrag=false
        val activityList: ArrayList<Activity> = ArrayList()
        fun addActivity(act:Activity){
            activityList.add(act)
        }
        fun removeActivity(){
            for(act in activityList){
                act.finish()
            }
        }
    }

    fun logIn(userInfo: UserInfo){
        user=userInfo
        isLogin=true
    }

    fun logOut(){
        user=null
        isLogin=false
    }




    override fun onCreate() {
        super.onCreate()
        regToWx()
        initOkHttp()
        ZXingLibrary.initDisplayOpinion(this)
    }
    //初始化OkHttp
    private fun initOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
                //                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build()

        OkHttpUtils.initClient(okHttpClient)
    }
    //regist weixin
    private fun regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true)
        api.registerApp(APP_ID)
    }
}