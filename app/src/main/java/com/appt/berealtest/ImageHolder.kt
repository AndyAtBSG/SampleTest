package com.appt.berealtest;

import ImageFile
import androidx.compose.runtime.Composable;
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageHolder(image: ImageFile) {
    GlideImage(
        stringResource(R.string.imageUrl, image.id),
        image.name
    )
}
