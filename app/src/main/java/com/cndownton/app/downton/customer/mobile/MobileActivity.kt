package com.cndownton.app.downton.customer.mobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cndownton.app.R
import com.cndownton.app.downton.util.EncryptUtil
import com.cndownton.app.downton.util.HMACSHA256
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception

class MobileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)

        var str="MOBILE=18358900385&TIME=20170916151630"
        HMACSHA256.sha256_HMAC(str)
        OkHttpUtils
                .post()
                .url("http://www.cndownton.com/tools/app_api.ashx?action=user_verify_smscode")
                .addParams("time", "20170916151630")
                .addParams("sign", HMACSHA256.sha256_HMAC(str))
                .addParams("mobile","18395921280")
                .build()
                .execute(object:StringCallback(){
                    override fun onResponse(response: String?, id: Int) {
                        Log.i("mpf",response)
                    }

                    override fun onError(call: Call?, e: Exception?, id: Int) {

                    }

                })

    }
}
