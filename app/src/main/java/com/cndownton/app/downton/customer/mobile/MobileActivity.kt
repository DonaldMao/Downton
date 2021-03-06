package com.cndownton.app.downton.customer.mobile

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.cndownton.app.R
import com.cndownton.app.downton.MyApplication
import com.cndownton.app.downton.data.bean.CommonCode
import com.cndownton.app.downton.data.bean.SMScode
import com.cndownton.app.downton.util.CommonUtil
import com.cndownton.app.downton.util.HMACSHA256
import com.cndownton.app.downton.util.JsonUitl
import com.cndownton.app.downton.util.setupActionBar
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import org.jetbrains.anko.find


import java.lang.Exception

@Suppress("DEPRECATION")
class MobileActivity : AppCompatActivity() {
    private lateinit var bt_send_cod:Button
    private lateinit var bt_confirm:Button
    private lateinit var et_phonenumber:EditText
    private lateinit var et_verificationcode:EditText
    private lateinit var tv_binded:TextView
    private lateinit var phoneNumber:String
    private lateinit var verificationCode:String
    private lateinit var nowTime:String
    private  var userId:Int?= MyApplication.user?.id
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)
        setupActionBar(R.id.toolbar_mobile) {
            setTitle("绑定手机号码")
            setHomeAsUpIndicator(R.drawable.setting_backarrow)
            setDisplayHomeAsUpEnabled(true)
        }
        initComponent()
    }

    private fun initComponent() {
        bt_send_cod=find(R.id.bt_sendcode)
        bt_send_cod.setOnClickListener {
            sendCode()
            bt_send_cod.isClickable=false
            bt_send_cod.isEnabled=false
            bt_send_cod.setTextColor(resources.getColor(R.color.white_gray))
            cdt.start()
        }
        bt_confirm=find(R.id.bt_confirm)
        bt_confirm.setOnClickListener {
            confirmBind()
        }
        et_phonenumber=find(R.id.et_phonenumber)
        et_verificationcode=find(R.id.et_verificationcode)

        tv_binded=find(R.id.tv_binded)
        if(MyApplication.user?.mobile!=""){
            tv_binded.text="当前已绑定手机号码${MyApplication.user?.mobile}！"
        }

    }

    private fun confirmBind() {
        verificationCode=et_verificationcode.text.toString()
        nowTime=CommonUtil.getCurrentTime()
        val str=CommonUtil.getRealShaStr("txtMobile=$phoneNumber","txtCode=$verificationCode","user_id=$userId","time=$nowTime")
        Log.i("mpf",str)

        OkHttpUtils
                .post()
                .url("http://www.cndownton.com/tools/app_api.ashx?action=user_mobile_bind")
                .addParams("time",nowTime)
                .addParams("sign", HMACSHA256.sha256_HMAC(this,str))
                .addParams("txtMobile",phoneNumber)
                .addParams("txtCode",verificationCode)
                .addParams("user_id", userId.toString())
                .build()
                .execute(object :StringCallback(){
                    override fun onResponse(response: String?, id: Int) {
                        Log.i("mpf",response)
                        val rep=JsonUitl.stringToObject(response, CommonCode::class.java)as CommonCode
                        Toast.makeText(this@MobileActivity,rep.msg,Toast.LENGTH_LONG).show()
                        if(rep.status==1){
                            finish()
                        }

                    }

                    override fun onError(call: Call?, e: Exception?, id: Int) {
                        Log.i("mpf",e?.message)

                    }

                })

    }

    private var count=59
    private val cdt=object:CountDownTimer(60000,1000){
        override fun onFinish() {
            bt_send_cod.isClickable=true
            bt_send_cod.isEnabled=true
            bt_send_cod.text="发送验证码"
            bt_send_cod.setTextColor(resources.getColor(R.color.orangered))
            count=59
        }

        override fun onTick(p0: Long) {
            bt_send_cod.text= "${count--}s后重发"


        }
    }



    private fun sendCode(){
        phoneNumber= et_phonenumber.text.toString()
        nowTime=CommonUtil.getCurrentTime()
        val str=CommonUtil.getRealShaStr("time=$nowTime","mobile=$phoneNumber")
        OkHttpUtils
                .post()
                .url("http://www.cndownton.com/tools/app_api.ashx?action=user_verify_smscode")
                .addParams("time",nowTime)
                .addParams("sign", HMACSHA256.sha256_HMAC(this,str))
                .addParams("mobile",phoneNumber)
                .build()
                .execute(object:StringCallback(){
                    override fun onResponse(response: String?, id: Int) {
                        val code=JsonUitl.stringToObject(response, SMScode::class.java)as SMScode
                        Toast.makeText(this@MobileActivity,code.msg,Toast.LENGTH_LONG).show()
                        Log.i("mpf",code.msg)
                    }

                    override fun onError(call: Call?, e: Exception?, id: Int) {
                        Toast.makeText(this@MobileActivity, e?.message,Toast.LENGTH_LONG).show()

                    }

                })
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
