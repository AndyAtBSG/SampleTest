package com.appt.berealtest.imageViewer

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.appt.berealtest.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageViewer(imageId: String) {
    GlideImage(
        stringResource(R.string.imageUrl, imageId),
        stringResource(R.string.contentDescriptionImage)
    )
}
