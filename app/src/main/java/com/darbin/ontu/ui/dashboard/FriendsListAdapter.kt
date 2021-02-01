package com.darbin.ontu.ui.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.darbin.ontu.R
import com.darbin.ontu.data.models.Friend

class FriendsListAdapter() : RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {

    private var friendsList: MutableList<Friend> = ArrayList()

    private var onItemClickListener : OnItemClickListener?=null
    private var onAddFriendClickListener:OnAddFriendClickListener?=null

    fun updateItems(list: MutableList<Friend>) {
        friendsList.clear()
        friendsList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFriend: ImageView =view.findViewById(R.id.img_friend)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_friend, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendsList[position]
        if (position!=0){
            holder.imgFriend.setImageResource(friend.image!!)
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(friend)
            }
        }else{
            holder.itemView.setOnClickListener {
                onAddFriendClickListener?.onAddFriendClicked()
            }
        }
    }
    override fun getItemCount() = friendsList.size

    interface OnItemClickListener{
        fun onItemClick(friend: Friend)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
    }

    interface OnAddFriendClickListener{
        fun onAddFriendClicked()
    }
    fun setOnAddFriendClickListener(onAddFriendClickListener: OnAddFriendClickListener){
        this.onAddFriendClickListener=onAddFriendClickListener
    }
}