package com.droidtitan.volley.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ViewFlipper
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageLoader.ImageContainer
import com.android.volley.toolbox.ImageLoader.ImageListener
import com.droidtitan.volley.R
import com.droidtitan.volley.util.*
import com.droidtitan.volley.util.volley.toString
import javax.inject.Inject
import kotlin.properties.Delegates

public class ImageLoaderFragment : Fragment() {

    var loader: ImageLoader by Delegates.notNull()
        @Inject set

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, state: Bundle?): View? {
        setActionBarTitle(R.string.image_loader_example)
        withComponent().inject(this)

        val root = inflater!!.inflate(R.layout.fragment_image_loader, container, false) as ViewFlipper
        val map = root.findViewById(R.id.mapImageView) as ImageView

        loader.get(Api.sfMapUrl(), object : ImageListener {
            override fun onResponse(container: ImageContainer, isImmediate: Boolean) {
                container.getBitmap()?.let {
                    root.setDisplayedChild(1)
                    map.setImageBitmap(it)
                }
            }

            override fun onErrorResponse(error: VolleyError) {
                root.setDisplayedChild(2)
                showSnackbar(error.toString(getResources()))
            }
        })

        return root
    }

    companion object {
        public val TAG: String = javaClass<ImageLoaderFragment>().getName()
    }
}