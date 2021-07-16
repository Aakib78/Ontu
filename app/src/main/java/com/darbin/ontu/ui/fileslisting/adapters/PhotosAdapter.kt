package com.darbin.ontu.ui.fileslisting.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darbin.ontu.R
import com.darbin.ontu.data.models.GalleryImage
import com.darbin.ontu.ui.fileslisting.viewholders.PhotosViewHolder
import kotlinx.android.synthetic.main.item_image_listing.view.*

/**
 * Created by Aakib
 * Date : 08/July/2021
 * Project : Ontu
 */


class PhotosAdapter(private val list: List<GalleryImage>) : RecyclerView.Adapter<PhotosViewHolder>() {

    init {
        initSelectedIndexList()
    }

    constructor(list: List<GalleryImage>, selectionLimit: Int) : this(list) {
        setSelectionLimit(selectionLimit)
    }

    private lateinit var onClick: (GalleryImage) -> Unit
    private lateinit var afterSelectionCompleted: () -> Unit
    private var isSelectionEnabled = false
    private lateinit var selectedIndexList: ArrayList<Int> // only limited items are selectable.
    private var selectionLimit = 0

    private fun initSelectedIndexList() {
        selectedIndexList = ArrayList(selectionLimit)
    }

    private fun setSelectionLimit(selectionLimit: Int) {
        this.selectionLimit = selectionLimit
        removedSelection()
        initSelectedIndexList()
    }

    fun setOnClickListener(onClick: (GalleryImage) -> Unit) {
        this.onClick = onClick
    }

    fun setAfterSelectionListener(afterSelectionCompleted: () -> Unit) {
        this.afterSelectionCompleted = afterSelectionCompleted
    }

    private fun checkSelection(position: Int) {
        if (isSelectionEnabled) {
            if (getItem(position).isSelected)
                selectedIndexList.add(position)
            else {
                selectedIndexList.remove(position)
                isSelectionEnabled = selectedIndexList.isNotEmpty()
            }
        }
    }

    //    Useful Methods to provide delete feature.

//    fun deletePicture(picture: GalleryPicture) {
//        deletePicture(list.indexOf(picture))
//    }
//
//    fun deletePicture(position: Int) {
//        if (File(getItem(position).path).delete()) {
//            list.removeAt(position)
//            notifyItemRemoved(position)
//        } else {
//            Log.e("GalleryPicturesAdapter", "Deletion Failed")
//        }
//    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotosViewHolder {
        val vh =
            PhotosViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_image_listing, p0, false))
        vh.containerView.setOnClickListener {
            val position = vh.adapterPosition
            val picture = getItem(position)
            if (isSelectionEnabled) {
                handleSelection(position, it.context)
                notifyItemChanged(position)
                checkSelection(position)
                afterSelectionCompleted()

            } else
                onClick(picture)

        }
        vh.containerView.setOnLongClickListener {
            val position = vh.adapterPosition
            isSelectionEnabled = true
            handleSelection(position, it.context)
            notifyItemChanged(position)
            checkSelection(position)
            afterSelectionCompleted()

            isSelectionEnabled
        }
        return vh
    }

    private fun handleSelection(position: Int, context: Context) {

        val picture = getItem(position)

        picture.isSelected = if (picture.isSelected) {
            false
        } else {
            val selectionCriteriaSuccess = getSelectedItems().size < selectionLimit
            if (!selectionCriteriaSuccess)
                selectionLimitReached(context)

            selectionCriteriaSuccess
        }
    }

    fun getSelectionLimit() = selectionLimit

    private fun selectionLimitReached(context: Context) {
        Toast.makeText(
            context,
            "${getSelectedItems().size}/$selectionLimit selection limit reached.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getItem(position: Int) = list[position]

    override fun onBindViewHolder(p0: PhotosViewHolder, p1: Int) {
        val picture = list[p1]
        Glide.with(p0.containerView).load(picture.path).into(p0.itemView.ivImg)

        if (picture.isSelected) {
            p0.itemView.vSelected.visibility = View.VISIBLE
        } else {
            p0.itemView.vSelected.visibility = View.GONE
        }
    }

    override fun getItemCount() = list.size

    fun getSelectedItems() = selectedIndexList.map {
        list[it]
    }

    private fun removedSelection(): Boolean {
        return if (isSelectionEnabled) {
            selectedIndexList.forEach {
                list[it].isSelected = false
            }
            isSelectionEnabled = false
            selectedIndexList.clear()
            notifyDataSetChanged()
            true

        } else false
    }
}