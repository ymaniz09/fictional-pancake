package com.github.ymaniz09.fictionalpancake.filter.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.github.ymaniz09.fictionalpancake.R
import com.github.ymaniz09.fictionalpancake.filter.Filter
import com.github.ymaniz09.fictionalpancake.utils.gone
import com.github.ymaniz09.fictionalpancake.utils.visible
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class FilterFragment : Fragment() {

    private val IMAGE_URL =
        "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fetchImageButton.setOnClickListener {
            imageView.gone()
            progressBar.visible()
            fetchAndDisplayImage()
        }

        applyFilterButton.setOnClickListener {
            imageView.gone()
            progressBar.visible()

            coroutineScope.launch {
                val filteredDeferred =
                    coroutineScope.async(Dispatchers.Default) { applyFilter(imageView.drawable.toBitmap()) }

                loadImage(filteredDeferred.await())
            }
        }

        fetchAndDisplayImage()

    }



    private fun fetchAndDisplayImage() {
        coroutineScope.launch {
            val originalDeferred = coroutineScope.async(Dispatchers.IO) { getOriginalBitmap() }

            loadImage(originalDeferred.await())
        }
    }

    private fun applyFilter(originalBitmap: Bitmap) =
        Filter.apply(originalBitmap)

    private fun loadImage(bitmap: Bitmap) {
        progressBar.gone()
        imageView.setImageBitmap(bitmap)
        imageView.visible()
    }

    private fun getOriginalBitmap() =
        URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }
}
