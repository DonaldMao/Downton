package com.cndownton.app.downton.customer.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import com.cndownton.app.downton.R
import com.cndownton.app.downton.util.setupActionBar
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class SettingActivity : AppCompatActivity() {
    private lateinit var sl_refresh:SwipeRefreshLayout
    private lateinit var mSettingPresenter:SettingPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setupActionBar(R.id.toolbar) {
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
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_toobar,menu)
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
