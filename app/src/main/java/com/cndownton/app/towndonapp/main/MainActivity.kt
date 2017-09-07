package com.cndownton.app.towndonapp.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.widget.ImageView
import com.cndownton.app.towndonapp.R
import com.cndownton.app.towndonapp.main.community.CommunityFragment
import com.cndownton.app.towndonapp.main.home.HomeFragment
import com.cndownton.app.towndonapp.main.mall.MallFragment
import com.cndownton.app.towndonapp.main.me.MeFragment
import com.cndownton.app.towndonapp.main.surround.SurroundFragment

class MainActivity : AppCompatActivity() {
    private lateinit var iv_home_tab: ImageView
    private lateinit var iv_mall_tab: ImageView
    private lateinit var iv_community_tab: ImageView
    private lateinit var iv_surround_tab: ImageView
    private lateinit var iv_me_tab: ImageView
    private lateinit var iv_selected: ImageView
    private lateinit var vp_main: ViewPager
    private val mFragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        iv_mall_tab = findViewById(R.id.iv_mall_tab)
        iv_mall_tab.setOnClickListener {
            if (iv_selected != iv_mall_tab) {
                unSelect(iv_selected)
                iv_selected = iv_mall_tab
                iv_mall_tab.setImageResource(R.drawable.mall_selected)
                vp_main.setCurrentItem(1, false)
            }

        }

        iv_community_tab = findViewById(R.id.iv_community_tab)
        iv_community_tab.setOnClickListener {
            if (iv_selected != iv_community_tab) {
                unSelect(iv_selected)
                iv_selected = iv_community_tab
                iv_community_tab.setImageResource(R.drawable.community_selected)
                vp_main.setCurrentItem(2, false)
            }
        }
        iv_surround_tab = findViewById(R.id.iv_surround_tab)
        iv_surround_tab.setOnClickListener {
            if (iv_selected != iv_surround_tab) {
                unSelect(iv_selected)
                iv_selected = iv_surround_tab
                iv_surround_tab.setImageResource(R.drawable.surround_selected)
                vp_main.setCurrentItem(3, false)
            }
        }
        iv_me_tab = findViewById(R.id.iv_me_tab)
        iv_me_tab.setOnClickListener {
            if (iv_selected != iv_me_tab) {
                unSelect(iv_selected)
                iv_selected = iv_me_tab
                iv_me_tab.setImageResource(R.drawable.me_selected)
                vp_main.setCurrentItem(4, false)
            }
        }

        mFragments.add(HomeFragment())
        mFragments.add(MallFragment())
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
                    1 -> {
                        iv_mall_tab.setImageResource(R.drawable.mall_selected)
                        iv_selected = iv_mall_tab
                    }

                    2 -> {
                        iv_community_tab.setImageResource(R.drawable.community_selected)
                        iv_selected = iv_community_tab
                    }
                    3 -> {
                        iv_surround_tab.setImageResource(R.drawable.surround_selected)
                        iv_selected = iv_surround_tab
                    }
                    4 -> {
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
            iv_mall_tab -> iv_selected.setImageResource(R.drawable.mall)
            iv_community_tab -> iv_selected.setImageResource(R.drawable.community)
            iv_surround_tab -> iv_selected.setImageResource(R.drawable.surround)
            iv_me_tab -> iv_selected.setImageResource(R.drawable.me)
        }
    }
}

