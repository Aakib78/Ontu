package com.darbin.ontu.ui.fileslisting.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.darbin.ontu.R
import com.darbin.ontu.base.MainApplication
import com.darbin.ontu.data.models.GalleryImage
import com.darbin.ontu.databinding.FragmentImagesBinding
import com.darbin.ontu.ui.Router
import com.darbin.ontu.ui.fileslisting.SpaceItemDecoration
import com.darbin.ontu.ui.fileslisting.adapters.PhotosAdapter
import com.darbin.ontu.ui.fileslisting.viewmodels.ImagesViewModel
import com.darbin.ontu.utils.viewModelProvider
import kotlinx.android.synthetic.main.fragment_images.*
import javax.inject.Inject


class ImagesFragment : Fragment() {

    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var router: Router

    private val adapter by lazy {
        PhotosAdapter(pictures, 10)
    }

    private val pictures by lazy {
        ArrayList<GalleryImage>(getViewModel().getGallerySize(requireActivity()))
    }


    private lateinit var binding: FragmentImagesBinding

    private fun getViewModel(): ImagesViewModel {
        return viewModelProvider(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appComponents.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestReadStoragePermission()
    }

    private fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                requireActivity(),
                readStorage
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } else initViews()
    }


    private fun initViews() {
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        val pageSize = 20
        rv.layoutManager = layoutManager
        rv.addItemDecoration(SpaceItemDecoration(8))
        rv.adapter = adapter

        adapter.setOnClickListener { galleryPicture ->
            showToast(galleryPicture.path)
        }

        adapter.setAfterSelectionListener {
            updateToolbar(getSelectedItemsCount())
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == pictures.lastIndex) {
                    loadPictures(pageSize)
                }
            }
        })

        loadPictures(pageSize)
    }

    private fun showToast(s: String) =
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()

    private fun getSelectedItemsCount() = adapter.getSelectedItems().size

    private fun loadPictures(pageSize: Int) {
        getViewModel().getImagesFromGallery(requireActivity(), pageSize) {
            if (it.isNotEmpty()) {
                pictures.addAll(it)
                adapter.notifyItemRangeInserted(pictures.size, it.size)
            }
            Log.i("GalleryListSize", "${pictures.size}")
        }
    }

    private fun updateToolbar(selectedItems: Int) {
//        val data = if (selectedItems == 0) {
//            tvDone.visibility = View.GONE
//            getString(R.string.txt_gallery)
//        } else {
//            tvDone.visibility = View.VISIBLE
//            "$selectedItems/${adapter.getSelectionLimit()}"
//        }
//        tvTitle.text = data
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            initViews()
        else {
            showToast("Permission Required to Fetch Gallery.")
        }
    }

}