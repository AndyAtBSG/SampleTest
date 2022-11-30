package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.appt.berealtest.ui.theme.BeRealTestTheme


@Composable
fun FileExplorer(
    directories: List<FileDirectory>,
    images: List<ImageFile>,
    onDirectorySelected: (id: String) -> Unit
) {
    Column {
        Text(text = "Explorer")
        LazyColumn {

            items(
                items = directories,
                key = { it.id }
            ) {
                ItemDirectory(it, onDirectorySelected)
            }

            items(
                items = images,
                key = { it.id }
            ) {
                ItemImage(it)
            }
        }
    }
}

@Composable
fun ItemDirectory(fileDirectory: FileDirectory, onDirectorySelected: (id: String) -> Unit) {
    Row(
        modifier = Modifier.clickable {
            println("ANDROB01 - Opening Directory ${fileDirectory.id}")
            onDirectorySelected(fileDirectory.id)
        }
    ) {
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
        FileExplorer(emptyList(), emptyList()) {}
    }
}
