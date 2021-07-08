package com.darbin.ontu.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.darbin.ontu.R
import com.darbin.ontu.base.MainApplication
import com.darbin.ontu.custom.StartSnapHelper
import com.darbin.ontu.data.models.Friend
import com.darbin.ontu.data.prefs.AppPreferenceImp
import com.darbin.ontu.databinding.ActivityHomeBinding
import com.darbin.ontu.ui.Router
import com.darbin.ontu.utils.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(),FriendsListAdapter.OnItemClickListener,FriendsListAdapter.OnAddFriendClickListener {

    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var appPreferenceImp: AppPreferenceImp

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var router : Router

    private fun getViewModel(): HomeViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding:ActivityHomeBinding
    private var adapter:FriendsListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        appComponents.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViews()
    }

    private fun initViews() {
        val snapHelper =StartSnapHelper()
        binding.rvFriends.layoutManager=LayoutUtils.getHorizontalLayoutManager(this)
        adapter = FriendsListAdapter()
        adapter?.setOnItemClickListener(this)
        adapter?.setOnAddFriendClickListener(this)
        binding.rvFriends.adapter=adapter
        snapHelper.attachToRecyclerView(binding.rvFriends)
        adapter?.updateItems(getViewModel().getFriendsList())


        binding.btnPhotos.setOnClickListener {
            router.showFilesListing(this, TAB_IMAGES)
        }

        binding.btnFiles.setOnClickListener {
            router.showFilesListing(this, TAB_FILES)
        }

        binding.btnMusic.setOnClickListener {
            router.showFilesListing(this, TAB_MUSIC)
        }

        binding.btnVideos.setOnClickListener {
            router.showFilesListing(this, TAB_VIDEOS)
        }

        if (appPreferenceImp.getUser()!=null){
            binding.tvUserName.text= appPreferenceImp.getUser()!!.userName
        }
    }

    override fun onItemClick(friend: Friend) {
        toast("Friend Clicked.")
    }

    override fun onAddFriendClicked() {
        toast("Add Friend")
    }
}