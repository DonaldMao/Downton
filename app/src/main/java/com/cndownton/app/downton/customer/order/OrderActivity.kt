package com.cndownton.app.downton.customer.order

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.MenuItem
import android.widget.LinearLayout
import com.cndownton.app.R
import com.cndownton.app.downton.util.setupActionBar
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.jetbrains.anko.find

class OrderActivity : AppCompatActivity() {
    private val titles= arrayOf("购买的订单","出售的订单")
    private val fragments= arrayListOf(BuyFragment(),SellFragment())
    private lateinit var mViewPager:ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        initView()
    }

    @Suppress("DEPRECATION")
    private fun initView() {
        mViewPager=find(R.id.vp_order)
        mViewPager.adapter=object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }
            override fun getCount(): Int {
                return fragments.size
            }
        }
        setupActionBar(R.id.toolbar_order) {
            setTitle("我的订单")
            setHomeAsUpIndicator(R.drawable.setting_backarrow)
            setDisplayHomeAsUpEnabled(true)
        }
        val magicIndicator = findViewById<MagicIndicator>(R.id.indicator_order)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode=true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, p1: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(p0)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = resources.getColor(R.color.red)
                colorTransitionPagerTitleView.text = titles[p1]
                colorTransitionPagerTitleView.setOnClickListener{
                    mViewPager.currentItem = p1
                }
                return  colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return titles.size
            }
            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(p0)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(resources.getColor(R.color.red))
                return indicator
            }
        }
        magicIndicator.navigator=commonNavigator
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerPadding = UIUtil.dip2px(this, 15.0)
        titleContainer.dividerDrawable = resources.getDrawable(R.drawable.simple_splitter)
        ViewPagerHelper.bind(magicIndicator,mViewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
