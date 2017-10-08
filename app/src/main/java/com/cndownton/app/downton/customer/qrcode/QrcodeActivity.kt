package com.cndownton.app.downton.customer.qrcode

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import com.cndownton.app.R
import com.cndownton.app.downton.MyApplication
import com.cndownton.app.downton.util.setupActionBar
import com.uuzuche.lib_zxing.activity.CodeUtils
import org.jetbrains.anko.find

class QrcodeActivity : AppCompatActivity() {
    private lateinit var qrcode:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        setupActionBar(R.id.toolbar_qrcode) {
            setTitle("我的二维码")
            setHomeAsUpIndicator(R.drawable.setting_backarrow)
            setDisplayHomeAsUpEnabled(true)
        }
        qrcode=find(R.id.qrcode)
        var mBitmap = CodeUtils.createImage(MyApplication.user!!.id.toString(), 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
        qrcode.setImageBitmap(mBitmap)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
