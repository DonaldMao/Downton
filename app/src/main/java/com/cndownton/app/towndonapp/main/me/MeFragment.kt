package com.cndownton.app.towndonapp.main.me

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import com.cndownton.app.towndonapp.R
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MeFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var rootView: View? = null
    private lateinit var mBadgeviewContainer: View

    private lateinit var ib_message_toolbar: ImageButton
    private lateinit var ib_setting:ImageButton
    private lateinit var ib_avatar:ImageButton
    private lateinit var tv_focus:TextView
    private lateinit var tv_fans:TextView
    private lateinit var tv_favorite:TextView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
            rootView = inflater!!.inflate(R.layout.fragment_me, container, false)
        mBadgeviewContainer = rootView!!.find(R.id.badgeview_container)
        QBadgeView(activity).bindTarget(mBadgeviewContainer).setBadgeNumber(6)
                .setBadgeGravity(Gravity.CENTER or  Gravity.END)
                .setBadgeTextSize(16f,true)
                .setOnDragStateChangedListener { _, _, _ -> }
        ib_message_toolbar = rootView!!.find(R.id.ib_message_toolbar)
        QBadgeView(activity).bindTarget(ib_message_toolbar).setBadgeText("2")
                .setBadgeTextSize(5f,true)
                .setBadgeGravity(Gravity.CENTER )
        ib_setting=rootView!!.find(R.id.ib_setting)
        ib_avatar=rootView!!.find(R.id.ib_avatar)
        tv_focus=rootView!!.find(R.id.tv_focus)
        tv_fans=rootView!!.find(R.id.tv_fans)
        tv_favorite=rootView!!.find(R.id.tv_favorite)

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

        ib_message_toolbar.setOnClickListener{toast("消息")}
        ib_avatar.setOnClickListener { toast("头像设置")  }
        ib_setting.setOnClickListener { toast("设置") }
        tv_focus.setOnClickListener{toast("关注")}
        tv_fans.setOnClickListener{toast("粉丝")}
        tv_favorite.setOnClickListener { toast("收集") }

        ll_order.setOnClickListener{toast("订单")}
        ll_cart.setOnClickListener{toast("购物车")}
        ll_surround.setOnClickListener{toast("周边")}
        ll_shop.setOnClickListener{toast("开店")}

        ll_bill.setOnClickListener{toast("账单")}
        ll_wallet.setOnClickListener{toast("钱包")}
        ll_card.setOnClickListener{toast("银行卡")}

        ll_team.setOnClickListener{toast("团队")}
        ll_friend.setOnClickListener{toast("好友")}
        ll_invite.setOnClickListener{toast("邀请")}
        ll_zone.setOnClickListener{toast("地区")}

        return rootView
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