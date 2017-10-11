package com.cndownton.app.downton.main.me

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.cndownton.app.R
import com.cndownton.app.downton.BaseFragment
import com.cndownton.app.downton.MyApplication
import com.cndownton.app.downton.customer.message.MessageActivity
import com.cndownton.app.downton.customer.mobile.MobileActivity
import com.cndownton.app.downton.customer.order.OrderActivity
import com.cndownton.app.downton.customer.qrcode.QrcodeActivity
import com.cndownton.app.downton.customer.setting.SettingActivity
import com.cndownton.app.downton.customer.upgrade.UpgradeActivity
import com.cndownton.app.downton.main.MainActivity
import com.cndownton.app.downton.util.SharedPreferencesUtil
import com.makeramen.roundedimageview.RoundedImageView
import com.tencent.mm.opensdk.modelmsg.SendAuth
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import q.rorbin.badgeview.QBadgeView

@Suppress("DEPRECATION")
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MeFragment : BaseFragment() {
    override fun onBackPressed(): Boolean {
        return true
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var rootView: View? = null
    private lateinit var mBadgeviewContainer: View

    private lateinit var tv_ID_score:TextView
    private lateinit var tv_nickname:TextView


    private lateinit var ib_message_toolbar: ImageButton
    private lateinit var ib_setting:ImageButton
    private lateinit var ib_mobile:ImageButton
    private lateinit var ib_avatar: RoundedImageView
    private lateinit var tv_focus:TextView
    private lateinit var tv_fans:TextView
    private lateinit var tv_favorite:TextView

    private lateinit var ll_upgrade:LinearLayout
    private lateinit var ll_order:LinearLayout
    private lateinit var ll_cart:LinearLayout
    private lateinit var ll_surround:LinearLayout
    private lateinit var ll_shop:LinearLayout

    private lateinit var ll_bill:LinearLayout
    private lateinit var ll_wallet:LinearLayout
    private lateinit var ll_card:LinearLayout

    private lateinit var ll_team:LinearLayout
    private lateinit var ll_friend:LinearLayout
    private lateinit var ll_invite:LinearLayout
    private lateinit var ll_zone:LinearLayout

    private lateinit var vs_content:ViewSwitcher

    private lateinit var bt_login:Button
    private lateinit var weixin_login:ImageView
    private lateinit var ib_star:ImageButton
    private lateinit var bt_logout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("mpf","me create")
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i("mpf","me onCreateView")
        rootView = inflater!!.inflate(R.layout.fragment_me, container, false)
        mBadgeviewContainer = rootView!!.find(R.id.badgeview_container)
        QBadgeView(activity).bindTarget(mBadgeviewContainer).setBadgeNumber(6)
                .setBadgeGravity(Gravity.CENTER or  Gravity.END)
                .setBadgeTextSize(16f,true)
                .setOnDragStateChangedListener { _, _, _ -> }
        ib_message_toolbar = rootView!!.find(R.id.ib_message_toolbar)
//        QBadgeView(activity).bindTarget(ib_message_toolbar).setBadgeText("2")
//                .setBadgeTextSize(5f,true)
//                .setBadgeGravity(Gravity.CENTER )

        tv_ID_score= rootView!!.find(R.id.tv_ID_score)
        tv_nickname= rootView!!.find(R.id.tv_nickname)

        ib_star= rootView!!.find(R.id.ib_star)
        ib_setting=rootView!!.find(R.id.ib_setting)
        ib_mobile= rootView!!.find(R.id.ib_mobile)
        ib_avatar=rootView!!.find(R.id.ib_avatar)
        tv_focus=rootView!!.find(R.id.tv_focus)
        tv_fans=rootView!!.find(R.id.tv_fans)
        tv_favorite=rootView!!.find(R.id.tv_favorite)

        ll_upgrade=rootView!!.find(R.id.ll_upgrade)
        ll_order=rootView!!.find(R.id.ll_order)
        ll_cart=rootView!!.find(R.id.ll_cart)
        ll_surround=rootView!!.find(R.id.ll_surround)
        ll_shop=rootView!!.find(R.id.ll_shop)

        ll_bill=rootView!!.find(R.id.ll_bill)
        ll_wallet=rootView!!.find(R.id.ll_wallet)
        ll_card=rootView!!.find(R.id.ll_card)

        ll_team=rootView!!.find(R.id.ll_team)
        ll_friend=rootView!!.find(R.id.ll_friend)
        ll_invite=rootView!!.find(R.id.ll_invite)
        ll_zone=rootView!!.find(R.id.ll_zone)

        bt_login= rootView!!.find(R.id.email_sign_in_button)
        weixin_login= rootView!!.find(R.id.weixin_login)
        vs_content= rootView!!.find(R.id.vs_content)
        bt_logout=rootView!!.find(R.id.bt_logout)
        if(!MyApplication.isLogin){
            vs_content.showNext()
            Log.i("mpf","me showNext")
        }else{
            refreshView()
            Log.i("mpf","me refreshView")
        }

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ib_message_toolbar.setOnClickListener{startActivity(Intent(activity,MessageActivity::class.java))}
        ib_avatar.setOnClickListener { toast("头像设置")  }
        ib_setting.setOnClickListener { startActivity(Intent(activity,SettingActivity::class.java))}
        ib_mobile.setOnClickListener{startActivity(Intent(activity,MobileActivity::class.java))}
//        tv_focus.setOnClickListener{toast("关注")}
//        tv_fans.setOnClickListener{toast("粉丝")}
//        tv_favorite.setOnClickListener { toast("收集") }

        ll_upgrade.setOnClickListener { startActivity(Intent(activity, UpgradeActivity::class.java)) }
        ll_order.setOnClickListener{startActivity(Intent(activity,OrderActivity::class.java))}
        ll_cart.setOnClickListener{toast("购物车")}
        ll_surround.setOnClickListener{toast("周边")}
        ll_shop.setOnClickListener{toast("开店")}

        ll_bill.setOnClickListener{toast("账单")}
        ll_wallet.setOnClickListener{toast("钱包")}
        ll_card.setOnClickListener{toast("银行卡")}

        ll_team.setOnClickListener{toast("团队")}
        ll_friend.setOnClickListener{toast("好友")}
        ll_invite.setOnClickListener{startActivity(Intent(activity,QrcodeActivity::class.java))}
        ll_zone.setOnClickListener{toast("地区")}

        bt_login.setOnClickListener{}
        weixin_login.setOnClickListener{
            wxLogin()
        }
        bt_logout.setOnClickListener{
            (activity.application as MyApplication).logOut()
            SharedPreferencesUtil(activity,"user").remove("unionid")
            vs_content.showNext()
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun wxLogin(){
        MyApplication.addActivity(activity as MainActivity)
        if (!MyApplication.api.isWXAppInstalled){
            toast("微信没有安装")
            return
        }
        val req:SendAuth.Req=SendAuth.Req()
        req.scope="snsapi_userinfo"
        req.state="towndon_wx_login"
        MyApplication.api.sendReq(req)
    }

    fun setView(index:Int){
        when(index){
            0->vs_content.showPrevious()
            1->vs_content.showNext()

        }
    }

    @SuppressLint("WrongConstant")
    fun refreshView(){
        Glide.with(activity).load(MyApplication.user?.avatar).into(ib_avatar)
        tv_ID_score.setText("编号：${MyApplication.user?.user_name} 积分：${MyApplication.user?.point} 金豆：${MyApplication.user?.coin}")
        tv_nickname.setText(MyApplication.user?.nick_name)
//        if(MyApplication.user?.mobile!=""){
//            ib_mobile.visibility=View.GONE
//        }
        when(MyApplication.user?.group_id){
            1->{

            }
            in 2..6->{
                ib_star.image=resources.getDrawable(R.drawable.bg_star_repeat)
                ib_star.scaleType=ImageView.ScaleType.FIT_XY
            }
            in 7..11->{
                ib_star.image=resources.getDrawable(R.drawable.bg_moon_repeat)
                ib_star.scaleType=ImageView.ScaleType.FIT_XY

            }

        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        val MEFRAGMENT_FIRSTVIEW:Int=0
        val MEFRAGMENT_SECONDTVIEW:Int=1


        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment MeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MeFragment {
            val fragment = MeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor