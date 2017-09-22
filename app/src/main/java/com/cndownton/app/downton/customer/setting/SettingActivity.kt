package com.cndownton.app.downton.customer.setting

import android.app.DatePickerDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import chihane.jdaddressselector.AddressSelector
import com.cndownton.app.R
import com.cndownton.app.downton.util.setupActionBar
import com.jaredrummler.materialspinner.MaterialSpinner

import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*
import java.util.Arrays.asList
import com.cndownton.app.downton.main.MainActivity
import chihane.jdaddressselector.BottomDialog
import chihane.jdaddressselector.OnAddressSelectedListener
import chihane.jdaddressselector.model.City
import chihane.jdaddressselector.model.County
import chihane.jdaddressselector.model.Province
import chihane.jdaddressselector.model.Street
import com.cndownton.app.downton.MyApplication
import com.cndownton.app.downton.data.bean.UserInfo


class SettingActivity : AppCompatActivity() {
    private lateinit var sl_refresh:SwipeRefreshLayout
    private lateinit var mSettingPresenter:SettingPresenter

    private lateinit var tv_birthday:TextView
    private lateinit var tv_address:TextView
    private lateinit var tv_username:TextView
    private lateinit var et_nickname:EditText
    private lateinit var ms_sex:MaterialSpinner
    val user: UserInfo? =MyApplication.user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setupActionBar(R.id.toolbar_setting) {
            setTitle("我的资料")
           setHomeAsUpIndicator(R.drawable.setting_backarrow)
            setDisplayHomeAsUpEnabled(true)
        }
        sl_refresh=find(R.id.sl_refresh)
        sl_refresh.setColorSchemeResources(R.color.colorAccent)
        sl_refresh.setOnRefreshListener {
            toast("refresh")
            //异步获取网络数据，随后UI更新
            sl_refresh.isRefreshing=false
        }
        ms_sex=find(R.id.ms_sex)
        ms_sex.setItems("男","女")
        ms_sex.setOnItemSelectedListener { view, position, id, item ->
        }

        tv_username=find(R.id.tv_username)
        tv_username.text = user?.user_name
        et_nickname=find(R.id.et_nickname)
        et_nickname.setText(user?.nick_name)


        tv_birthday=find(R.id.tv_birthday)
        tv_birthday.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
                tv_birthday.text = "$p1-${p2+1}-$p3"
            },1980,1,1).show()
        }

        tv_address=find(R.id.tv_address)
        tv_address.setOnClickListener {
            val dialog = BottomDialog(this@SettingActivity)
            dialog.setOnAddressSelectedListener(object :OnAddressSelectedListener{
                override fun onAddressSelected(p0: Province?, p1: City?, p2: County?, p3: Street?) {
                    tv_address.text="${p0?.name} ${p1?.name} ${if(p2==null) "" else p2!!.name } ${if(p3==null) "" else p3!!.name }"
                    dialog.dismiss()
                }

            })
            dialog.show()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_toolbar,menu)
        return true
    }

     override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->finish()
            R.id.action_refresh->toast("refresh")
        }
         return super.onOptionsItemSelected(item)
    }

}
