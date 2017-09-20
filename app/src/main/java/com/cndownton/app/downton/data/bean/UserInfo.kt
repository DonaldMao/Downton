package com.cndownton.app.downton.data.bean

/**
 * Created by Mpf on 2017/9/19.
 */
data class UserInfo(val id:Int, val group_id:Int, val user_name:String, val salt:String, val password:String, val mobile:String,
                    val email:String, val avatar:String, val nick_name:String, val sex:String, val birthday:String, val telphone:String,
                    val area:String, val address:String, val qq:String, val wechat:String, val amount:Int, val point:Int, val coin:Int,
                    val exp:Int, val status:Int, val reg_time:String, val reg_ip:String, val Boss:Int, val Partner:Int, val QRCode:String,
                    val FaYan:Int, val BianHao:Int, val isagent:Int, val is_agent:Int, val is_gongpai:Int, val gongpai_amount:Int,
                    val zhitui_num:Int, val alipay_account:String)
