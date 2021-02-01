package com.darbin.ontu.ui.onboarding

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darbin.ontu.R
import com.darbin.ontu.data.models.AvatarModel
import kotlinx.android.synthetic.main.item_avatar.view.*

class AvatarAdapter(val itemClick: (position:Int,item:AvatarModel) -> Unit) : RecyclerView.Adapter<ItemViewHolder>() {

    private var items: List<AvatarModel> = listOf()
    private var lastFilterSelectedPosition:Int = -1

    fun setSelectedItem(itemId:Int){
        lastFilterSelectedPosition=itemId
        notifyItemChanged(itemId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])

        if (lastFilterSelectedPosition==position){
            holder.itemView.img_avatar.borderWidth=4
            holder.itemView.img_avatar.borderColor=Color.parseColor("#0D619D")
        }else{
            holder.itemView.img_avatar.borderWidth=4
            holder.itemView.img_avatar.borderColor=Color.parseColor("#FFFFFF")
        }

        holder.itemView.setOnClickListener {
            itemClick(position,items[position])
            if (lastFilterSelectedPosition!=-1){
               notifyItemChanged(lastFilterSelectedPosition)
            }
            setSelectedItem(position)
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<AvatarModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}

class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: AvatarModel) {
        view.img_avatar.setImageResource(item.image)
    }
}