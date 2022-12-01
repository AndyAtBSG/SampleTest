package com.appt.berealtest.imageViewer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.appt.berealtest.BuildConfig
import com.appt.berealtest.R
import com.appt.berealtest.services.NetworkBeRealImageService.Companion.BASE64_AUTH
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

private const val getImageEndpoint = "/items/%s/data"

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ImageViewer(imageId: String) {
    GlideImage(
        model = BuildConfig.SERVER_URL + String.format(getImageEndpoint, imageId),
        contentDescription = stringResource(R.string.contentDescriptionImage),
        modifier = Modifier.fillMaxSize()
    ) {
        val url = GlideUrl(
            BuildConfig.SERVER_URL + String.format(getImageEndpoint, imageId),
            LazyHeaders.Builder()
                .addHeader("Authorization", "Basic $BASE64_AUTH")
                .build()
        )

        it.load(url)
    }
}
