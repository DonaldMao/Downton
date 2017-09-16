package com.cndownton.app.downton

import android.app.Application
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import permissions.dispatcher.RuntimePermissions
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Administrator_mpf on 2017/9/13.
 */
class MyApplication : Application() {
    companion object {
        val APP_ID: String = "wx5269ea51c51983b1"
        lateinit var api: IWXAPI
    }


    override fun onCreate() {
        super.onCreate()
        regToWx()//regist weixin
        initOkHttp()
    }

    private fun initOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
                //                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build()

        OkHttpUtils.initClient(okHttpClient)
    }

    private fun regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true)
        api.registerApp(APP_ID)
    }
}