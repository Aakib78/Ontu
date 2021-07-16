package com.darbin.ontu.data.models

/**
 * Created by Aakib
 * Date : 08/July/2021
 * Project : Ontu
 */
data class GalleryImage(
    val path: String,
    val name: String,
    val size:String
) {
    var isSelected = false
}