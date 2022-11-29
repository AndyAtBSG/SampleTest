package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.appt.berealtest.ui.theme.BeRealTestTheme


@Composable
fun FileExplorer(directories: List<FileDirectory>, images: List<ImageFile>) {
    Column {
        Text(text = "Explorer")
        directories.forEach {
            ItemDirectory(it)
        }
        images.forEach {
            ItemImage(it)
        }
    }
}

@Composable
fun ItemDirectory(fileDirectory: FileDirectory) {
    Row {
        Icon(
            Icons.Rounded.Send,
            stringResource(R.string.contentDescription_Directory, fileDirectory.name)
        )
        Text(fileDirectory.name)
    }
}

@Composable
fun ItemImage(image: ImageFile) {
    Row {
        Icon(Icons.Rounded.Email, stringResource(R.string.contentDescription_Image, image.name))
        Text(image.name)
    }
}

@Preview(showBackground = true)
@Composable
fun FileExplorerPreview() {
    BeRealTestTheme {
        FileExplorer(emptyList(), emptyList())
    }
}
