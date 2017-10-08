package com.cndownton.app.downton.main

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.cndownton.app.R
import com.cndownton.app.downton.BaseFragment
import com.cndownton.app.downton.MyApplication
import com.cndownton.app.downton.data.bean.UserInfo
import com.cndownton.app.downton.main.community.CommunityFragment
import com.cndownton.app.downton.main.home.HomeFragment
import com.cndownton.app.downton.main.me.MeFragment
import com.cndownton.app.downton.main.surround.SurroundFragment
import com.cndownton.app.downton.util.*
import com.cndownton.app.wxapi.WXEntryActivity
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import ezy.boost.update.UpdateManager
import okhttp3.Call
import org.jetbrains.anko.toast
import org.json.JSONObject
import permissions.dispatcher.*

@RuntimePermissions()
class MainActivity : AppCompatActivity() {
    private lateinit var iv_home_tab: ImageView
    private lateinit var iv_mall_tab: ImageView
    private lateinit var iv_community_tab: ImageView
    private lateinit var iv_surround_tab: ImageView
    private lateinit var iv_me_tab: ImageView
    private lateinit var iv_selected: ImageView
    private lateinit var vp_main: ViewPager
    private val mFragments: ArrayList<Fragment> = ArrayList()

    private var mCheckUrl = "http://192.168.1.99/update.php"
    private var nowTime: String? = null
    private var signStr: String? = null

    private lateinit var mIntent: Intent

//    private lateinit var mProgress:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mIntent = intent
        MainActivityPermissionsDispatcher.getPermissionsWithCheck(this)
        checkUpdate()
        iv_home_tab = findViewById(R.id.iv_home_tab)
        vp_main = findViewById(R.id.vp_home)

        iv_selected = iv_home_tab
        iv_home_tab.setOnClickListener {
            if (iv_selected != iv_home_tab) {
                unSelect(iv_selected)
                iv_selected = iv_home_tab
                iv_home_tab.setImageResource(R.drawable.home_selected)
                vp_main.setCurrentItem(0, false)
            }
        }

//        iv_mall_tab = findViewById(R.id.iv_mall_tab)
//        iv_mall_tab.setOnClickListener {
//            if (iv_selected != iv_mall_tab) {
//                unSelect(iv_selected)
//                iv_selected = iv_mall_tab
//                iv_mall_tab.setImageResource(R.drawable.mall_selected)
//                vp_main.setCurrentItem(1, false)
//            }
//
//        }

        iv_community_tab = findViewById(R.id.iv_community_tab)
        iv_community_tab.setOnClickListener {
            if (iv_selected != iv_community_tab) {
                unSelect(iv_selected)
                iv_selected = iv_community_tab
                iv_community_tab.setImageResource(R.drawable.community_selected)
                vp_main.setCurrentItem(1, false)
            }
        }
        iv_surround_tab = findViewById(R.id.iv_surround_tab)
        iv_surround_tab.setOnClickListener {
            if (iv_selected != iv_surround_tab) {
                unSelect(iv_selected)
                iv_selected = iv_surround_tab
                iv_surround_tab.setImageResource(R.drawable.surround_selected)
                vp_main.setCurrentItem(2, false)
            }
        }
        iv_me_tab = findViewById(R.id.iv_me_tab)
        iv_me_tab.setOnClickListener {
            if (iv_selected != iv_me_tab) {
                unSelect(iv_selected)
                iv_selected = iv_me_tab
                iv_me_tab.setImageResource(R.drawable.me_selected)
                vp_main.setCurrentItem(3, false)
            }
        }

        mFragments.add(HomeFragment())
//        mFragments.add(MallFragment())

        mFragments.add(CommunityFragment())
        mFragments.add(SurroundFragment())
        mFragments.add(MeFragment())


        vp_main.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

        }
        vp_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                unSelect(iv_selected)
                when (position) {
                    0 -> {
                        iv_home_tab.setImageResource(R.drawable.home_selected)
                        iv_selected = iv_home_tab
                    }
//                    1 -> {
//                        iv_mall_tab.setImageResource(R.drawable.mall_selected)
//                        iv_selected = iv_mall_tab
//                    }

                    1 -> {
                        iv_community_tab.setImageResource(R.drawable.community_selected)
                        iv_selected = iv_community_tab
                    }
                    2 -> {
                        iv_surround_tab.setImageResource(R.drawable.surround_selected)
                        iv_selected = iv_surround_tab
                    }
                    3 -> {
                        iv_me_tab.setImageResource(R.drawable.me_selected)
                        iv_selected = iv_me_tab
                    }

                }
            }

        })

    }


    private fun unSelect(iv_selected: ImageView?) {
        when (iv_selected) {
            iv_home_tab -> iv_selected.setImageResource(R.drawable.home)
//            iv_mall_tab -> iv_selected.setImageResource(R.drawable.mall)
            iv_community_tab -> iv_selected.setImageResource(R.drawable.community)
            iv_surround_tab -> iv_selected.setImageResource(R.drawable.surround)
            iv_me_tab -> iv_selected.setImageResource(R.drawable.me)
        }
    }

    override fun onBackPressed() {
        if(!(mFragments[vp_main.currentItem] as BaseFragment).onBackPressed()){
            Log.i("mpf","back")
            return}
        super.onBackPressed()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getPermissions() {

    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForContact(request: PermissionRequest) {
        showRationaleDialog("我们需要权限连接手机中微信，从而能够使用微信登录", request)
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun onDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, "微信登录功能无法使用", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun onNeverAskAgain() {
        Toast.makeText(this, "由于禁止了部分权限，程序功能无法正常使用", Toast.LENGTH_SHORT).show()
    }

    private fun showRationaleDialog(string: String, request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton("同意") { _, _ -> request.proceed() }
                .setNegativeButton("拒绝") { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(string)
                .show()
    }

    private fun checkUpdate() {
        mCheckUrl="http://www.cndownton.com/tools/app_unsign.ashx?action=get_app_version_info"
        UpdateManager.setDebuggable(true)
        UpdateManager.setWifiOnly(false)
        UpdateManager.setUrl(mCheckUrl, "main")
        UpdateManager.check(this)
    }

    override fun onRestart() {
        super.onRestart()
        log("restart")
    }



    override fun onResume() {
        log("resume")
        super.onResume()
        val data = SharedPreferencesUtil(this, "user")

        if (!MyApplication.isLogin && data.contain("unionid")) {
//            toast("unionid")
            log("resume login ${MyApplication.isLogin}   ${data.contain("unionid")}")
            loginUser(data.getSharedPreference("unionid","") as String)

        } else if (MyApplication.needFreshMeFrag) {
            (mFragments[3] as MeFragment).setView(MeFragment.MEFRAGMENT_FIRSTVIEW)
            MyApplication.needFreshMeFrag = false
            (mFragments[3] as MeFragment).refreshView()
        }

    }

    override fun onPause() {
        log("pause")
        super.onPause()
    }

    override fun onStop() {
        log("stop")
        super.onStop()
    }

    private fun loginUser(unionid:String) {
        nowTime = CommonUtil.getCurrentTime()
        signStr = CommonUtil.getRealShaStr("time=" + nowTime, "unionid=" + unionid)

        OkHttpUtils.post().url("http://www.cndownton.com/tools/app_api.ashx?action=user_login_weixin_unionid")
                .addParams("time",nowTime).addParams("unionid",unionid).addParams("sign", HMACSHA256.sha256_HMAC(this, signStr!!))

                .build().execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                 Log.i("mpf",e.message)
            }
            override fun onResponse(response: String, id: Int) {
                Log.i("mpf",response)

                try {
                    val mObject = JSONObject(response)
                    val info = JsonUitl.stringToObject(mObject.getString("msg"), UserInfo::class.java) as UserInfo
                    val application = application as MyApplication
                    application.logIn(info)
                } catch (e: Exception) {
                    runOnUiThread {
                    }
                    e.printStackTrace()
                }

            }
        })
    }
}

