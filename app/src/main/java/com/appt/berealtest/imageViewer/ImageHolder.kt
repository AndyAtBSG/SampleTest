package com.appt.berealtest.imageViewer

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.appt.berealtest.BuildConfig
import com.appt.berealtest.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

private const val getImageEndpoint = "/items/%s/data"

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageViewer(imageId: String) {
    GlideImage(
        BuildConfig.SERVER_URL + String.format(getImageEndpoint, imageId),
        stringResource(R.string.contentDescriptionImage)
    )
}
