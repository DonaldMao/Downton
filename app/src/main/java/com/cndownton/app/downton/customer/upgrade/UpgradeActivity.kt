package com.cndownton.app.downton.customer.upgrade

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.cndownton.app.R
import com.cndownton.app.downton.MyApplication
import com.cndownton.app.downton.data.bean.UpdateCode
import com.cndownton.app.downton.util.CommonUtil
import com.cndownton.app.downton.util.HMACSHA256
import com.cndownton.app.downton.util.JsonUitl
import com.cndownton.app.downton.util.setupActionBar
import com.cndownton.app.downton.util.zhifu.OrderInfoUtil2_0
import com.cndownton.app.downton.util.zhifu.PayResult
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import org.jetbrains.anko.find
import org.json.JSONObject
import java.lang.Exception

class UpgradeActivity : AppCompatActivity() {
    private lateinit var et_star_desc:TextView
    private lateinit var et_star:TextView
    private lateinit var et_moon:TextView
    private lateinit var et_moon_desc:TextView
 private lateinit var tv_desc:TextView

    private lateinit var ll_star:LinearLayout
    private lateinit var ll_moon:LinearLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade)
        initView()
        initData()
    }

    private fun initData() {
        val nowTime= CommonUtil.getCurrentTime()
        val str= CommonUtil.getRealShaStr("time=$nowTime","user_id=${MyApplication.user?.id}")
        OkHttpUtils .post()
                .url("http://www.cndownton.com/tools/app_api.ashx?action=user_uplevel_list")
                .addParams("time",nowTime)
                .addParams("sign",HMACSHA256.sha256_HMAC(this,str))
                .addParams("user_id", MyApplication.user?.id.toString())
                .build()
                .execute(object: StringCallback(){
                    override fun onResponse(response: String?, id: Int) {
                        Log.i("mpf",response)
                        var lists=JsonUitl.stringToList(response!!,UpdateCode::class.java)
                        et_star_desc.text="成为${lists[0].level_name}级会员"
                        et_star.text="￥${lists[0].amount}"
                        et_moon_desc.text="成为${lists[1].level_name}级会员"
                        et_moon.text="￥${lists[1].amount}"
                    }
                    override fun onError(call: Call?, e: Exception?, id: Int) {

                    }

                })


    }

    private fun initView() {

        setupActionBar(R.id.toolbar_update) {
            setTitle("我要升级")
            setHomeAsUpIndicator(R.drawable.setting_backarrow)
            setDisplayHomeAsUpEnabled(true)
        }
        et_star=find(R.id.tv_update_star)
        et_star_desc=find(R.id.tv_update_star_desc)
        et_moon=find(R.id.tv_update_moon)
        et_moon_desc=find(R.id.tv_update_moon_desc)
        tv_desc=find(R.id.tv_desc)

        ll_star=find(R.id.ll_star)
        ll_moon=find(R.id.ll_moon)

        when(MyApplication.user!!.group_id ){
            1->tv_desc.text="您现在是游客"
            in 2..6->tv_desc.text="您现在是5星星级会员"
             else ->tv_desc.text="您现在是5月亮级会员"
        }

        if(MyApplication.user!!.group_id >=6){
            ll_star.visibility= View.GONE
        }
        if(MyApplication.user!!.group_id >=11){
            ll_moon.visibility= View.GONE
        }
        ll_star.setOnClickListener {
            val nowTime= CommonUtil.getCurrentTime()
            val str= CommonUtil.getRealShaStr("time=$nowTime","user_id=${MyApplication.user?.id}","lv=6")
            OkHttpUtils.post()
                    .url("http://www.cndownton.com/tools/app_api.ashx?action=add_uplevel_order")
                    .addParams("time",nowTime)
                    .addParams("lv", "6")
                    .addParams("user_id", MyApplication.user?.id.toString())
                    .addParams("sign",HMACSHA256.sha256_HMAC(this,str))
                    .build().execute(object:StringCallback(){
                override fun onResponse(response: String?, id: Int) {
                    Log.i("mpf",response)
                    val jsonO=JSONObject(response)
                    if (jsonO.getInt("status")==0){
                        Toast.makeText(this@UpgradeActivity,jsonO.getString("msg"),Toast.LENGTH_LONG).show()
                    }else{
                        val msg=jsonO.getJSONObject("msg");
                        payV2(msg.getInt("amount"),"升级费用", "升级为5星星用户",msg.getString("recharge_no"))
                    }
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                }

            })
        }
        ll_moon.setOnClickListener {
            val nowTime= CommonUtil.getCurrentTime()
            val str= CommonUtil.getRealShaStr("time=$nowTime","user_id=${MyApplication.user?.id}","lv=11")
            OkHttpUtils.post()
                    .url("http://www.cndownton.com/tools/app_api.ashx?action=add_uplevel_order")
                    .addParams("time",nowTime)
                    .addParams("lv", "11")
                    .addParams("user_id", MyApplication.user?.id.toString())
                    .addParams("sign",HMACSHA256.sha256_HMAC(this,str))
                    .build().execute(object:StringCallback(){
                override fun onResponse(response: String?, id: Int) {
                    Log.i("mpf",response)
                    val jsonO=JSONObject(response)
                    if (jsonO.getInt("status")==0){
                        Toast.makeText(this@UpgradeActivity,jsonO.getString("msg"),Toast.LENGTH_LONG).show()
                    }else{
                        val msg=jsonO.getJSONObject("msg");
                        payV2(msg.getInt("amount"),"升级费用", "升级为5星星用户",msg.getString("recharge_no"))
                    }
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                }

            })
        }


    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private var RSA2_PRIVATE = "MIIEpAIBAAKCAQEAsusBgHNWtXXIyaRM3X36JBjbkVtGrRJ36uRxpXsnrM82h/uIrUaPMs4V5gze/RNVim7qYQ/jIZRTfLzBU9VJvr76nFOZ1qShOrV60GJsTC+mMSTHdJB8iIOVaqWnBfund9wtTZazzki+h8ipXwg4LuVJU4DNfqASWoB0fNOefF+vTQW9ZZmG2yl4B/N09hiemUlJ3CvKvB7ifh5af4jclm7GaDyXXKF9qI4RAQU6vwAnZdb+9JmUBs1PGvXSQ/ZjvFaXX+ZloVyhVx49Zxh0DvvarF5kZTsN3zNZj4uwxUhzVFkW1yLvK0kPTLw0IitjHY3z11PifMAjJ24VNHCTMwIDAQABAoIBAQCIk/S1hVYvdxYrNwymF8CXRZJ2x2KQQ4Te/jvywqxqslNCQrT28OHnOoJk9nZSYivuXPT12xbbT0rb8Yry3+JHhanxI7BPtaSOGHMEF93r2oPXB+hqBtHJtAifPIzfeFdCnATJMb1YPxeSt47zSGb7BXSQFcbIXpEkHOLPVwuojGnWmx4H54nm26JpzIiqJ1ZWRxUtn5ZmWE1DOoBnnUnoU4Ici4NApxgc1R1xhp4wHZwzm5YVTSqH0Qd3TJQ5HFlhDJ6SrfUe1+6GQibgncDPe+xOL+sj72+xQSSPZqf07bZOsiQi/YWt3XbeqaZ6vpIZHIRhZEJcSmYuGcN1XtrBAoGBAO1xJVECmt/1SNSBqsxi+vIYroH92KrL1SfnCGDkgE1rjDpms08/0JfLLRrh2cWK23DxXAMiXdlLhwo70W2NuCIgbe8fIIgP5iE6tgaywOR6yaYt6uoHN242hUGw7KqAGVbv8UzUtU2KD5p8CqyPE40N+bRzcQ6Dt+mFOWYnx7YpAoGBAMDm4kMlySiNyyPqlcV2ZgcRP53dCAFOzEI80h+qj3WnzBSXn6zg2gXgv/G6XKUq/rizR2ou23d8xA1HBQ77IynmOEgi6BcE8OUxoSa+zFjW9fJeqYyQhG8f3TPY6hs7brXndpLHkp1Vo3WH7Ndw2AgL8Vny1cU2T6CRPNe+zlH7AoGAH3OSVtW6gBn0iLmYTzR5OyJL00o1rgIL/RocQQFjgV3SmFXMQJ4aWlD+a4YpG4KWKLK/1mZx+fuIKrHbvuA72MFk7ny4CW3ZtOMbA24gIOOm3E1OxaspC39LTXVLFBadxKdJ1jyCu3+jK3psy4i0NUX5oo58FkkaTmNoRADW8mkCgYEAnmt/GmInx9RbJk37XkWUGO3DbyQc9OX/vc4xSqxKUPfTFH5JNjMbGoSvXhgNCeA7fPhPCBcfSgv+/mB6+QiqojhdtHBmZrE6HSAbFrTPIY64QaoYWEY/XPuwMGpOKLswetdye6zG7cttCJLuoSgdYs5cEeDqSqIF1vowG4ap7o0CgYAGU2jmLmqCUQqieRIqaqrbLHiJC77FWewD3S2BAeG0sJLuU7xFbLIVoBCJYhD0SilEz94OAhdEbWQ45WPj5uM5fw+KT3/ic4spLE/BJVNgzdrPrAsRyNWIC1KS430Y03CRewykwtX9lVqjznkSDIfS+Snp7nSo7Z8+dngItzV83Q==";
    private val APPID = "2016050401364388"
    fun payV2(amount:Int,subject:String,body:String,outTradeNo:String) {
        val rsa2 = true
        val params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,amount,subject,body,outTradeNo)
        val orderParam = OrderInfoUtil2_0.buildOrderParam(params)

        val privateKey = RSA2_PRIVATE
        val sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2)
        val orderInfo = orderParam + "&" + sign

        val payRunnable = Runnable {
            val alipay = PayTask(this@UpgradeActivity)
            val result = alipay.payV2(orderInfo, true)
            Log.i("msp", result.toString())
            val payResult = PayResult(result as Map<String, String>)
            /**
            对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            val resultInfo = payResult.getResult()// 同步返回需要验证的信息
            val resultStatus = payResult.getResultStatus()
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知
                Toast.makeText(this@UpgradeActivity, "支付成功", Toast.LENGTH_SHORT).show()
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(this@UpgradeActivity, "支付失败", Toast.LENGTH_SHORT).show()
            }

        }

        val payThread = Thread(payRunnable)
        payThread.start()
    }
}
