package com.darbin.ontu.ui.fileslisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.darbin.ontu.R
import com.darbin.ontu.custom.ViewPagerAdapter
import com.darbin.ontu.databinding.ActivityFilesListingBinding
import com.darbin.ontu.ui.fileslisting.fragments.FilesFragment
import com.darbin.ontu.ui.fileslisting.fragments.ImagesFragment
import com.darbin.ontu.ui.fileslisting.fragments.MusicFragment
import com.darbin.ontu.ui.fileslisting.fragments.VideosFragment
import com.darbin.ontu.utils.*
import com.google.android.material.tabs.TabLayout

class ActivityFilesListing : AppCompatActivity() {

    private lateinit var binding: ActivityFilesListingBinding

    private lateinit var adapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files_listing)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_files_listing)
        setStatePageAdapter()

        intent.extras?.getString(CURRENT_TAB)?.let { switchToTab(it) }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.filesListingViewPager.currentItem = tab.position
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setStatePageAdapter() {
        adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(VideosFragment(),"Videos")
        adapter.addFragment(MusicFragment(),"Music")
        adapter.addFragment(FilesFragment(),"Files")
        adapter.addFragment(ImagesFragment(),"Photos")

        binding.filesListingViewPager.adapter=adapter
        binding.tabLayout.setupWithViewPager(binding.filesListingViewPager,true)
    }

    private fun switchToTab(tab: String) {
        when (tab) {
            TAB_VIDEOS -> {
                binding.filesListingViewPager.currentItem = 0
            }
            TAB_MUSIC -> {
                binding.filesListingViewPager.currentItem = 1
            }
            TAB_FILES -> {
                binding.filesListingViewPager.currentItem = 2
            }
            TAB_IMAGES -> {
                binding.filesListingViewPager.currentItem = 3
            }
            else -> {
                binding.filesListingViewPager.currentItem = 0
            }
        }
    }
}