package com.darbin.ontu.custom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by Aakib
 * Date : 07/July/2021
 * Project : Ontu
 */

internal class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT )
{
    private val fragmentList:MutableList<Fragment> = ArrayList<Fragment>()
    private val fragmentTitleList:MutableList<String> = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title:String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}