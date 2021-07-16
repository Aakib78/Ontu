package com.darbin.ontu.ui.fileslisting.viewmodels

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import com.darbin.ontu.data.models.GalleryImage
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Aakib
 * Date : 08/July/2021
 * Project : Ontu
 */

class ImagesViewModel @Inject constructor(
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var startingRow = 0
    private var rowsToLoad = 0
    private var allLoaded = false

    fun getImagesFromGallery(
        context: Context,
        pageSize: Int,
        list: (List<GalleryImage>) -> Unit
    ) {
        compositeDisposable.add(
            Single.fromCallable {
                fetchGalleryImages(context, pageSize)
            }.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getGallerySize(context: Context): Int {
        val cursor = getGalleryCursor(context)
        val rows = cursor!!.count
        cursor.close()
        return rows
    }

    private fun fetchGalleryImages(context: Context, rowsPerLoad: Int): List<GalleryImage> {
        val galleryImageUrls = LinkedList<GalleryImage>()
        val cursor = getGalleryCursor(context)

        if (cursor != null && !allLoaded) {
            val totalRows = cursor.count

            allLoaded = rowsToLoad == totalRows
            if (rowsToLoad < rowsPerLoad) {
                rowsToLoad = rowsPerLoad
            }

            for (i in startingRow until rowsToLoad) {
                cursor.moveToPosition(i)
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.MediaColumns._ID)
                val name = cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)
                val size = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)
                galleryImageUrls.add(GalleryImage(getImageUri(cursor.getString(dataColumnIndex)).toString(),name.toString(),size.toString())) //get Image path from column index

            }
            Log.i("TotalGallerySize", "$totalRows")
            Log.i("GalleryStart", "$startingRow")
            Log.i("GalleryEnd", "$rowsToLoad")

            startingRow = rowsToLoad

            if (rowsPerLoad > totalRows || rowsToLoad >= totalRows)
                rowsToLoad = totalRows
            else {
                if (totalRows - rowsToLoad <= rowsPerLoad)
                    rowsToLoad = totalRows
                else
                    rowsToLoad += rowsPerLoad
            }

            cursor.close()
            Log.i("PartialGallerySize", " ${galleryImageUrls.size}")
        }
        return galleryImageUrls
    }

    private fun getGalleryCursor(context: Context): Cursor? {
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columns = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DATE_MODIFIED)
        val orderBy = MediaStore.MediaColumns.DATE_MODIFIED //order data by modified
        return context.contentResolver
            .query(
                externalUri,
                columns,
                null,
                null,
                "$orderBy DESC"
            )//get all data in Cursor by sorting in DESC order
    }

    private fun getImageUri(path: String) = ContentUris.withAppendedId(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        path.toLong()
    )

    override fun onCleared() {
        compositeDisposable.clear()
    }
}