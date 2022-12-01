package com.appt.berealtest.fileExplorer

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appt.berealtest.R
import com.appt.berealtest.ui.theme.BeRealTestTheme

@Composable
fun FileExplorer(
    directoryId: String,
    openDirectory: (directoryId: String) -> Unit,
    openImage: (imageId: String) -> Unit,
    signOut: () -> Unit,
    viewModel: FileExplorerViewModel = viewModel(factory = FileExplorerViewModel.Factory)
) {
    LaunchedEffect(directoryId) {
        viewModel.openDirectory(directoryId, signOut)
    }

    val uiState = viewModel.uiState.value

    FileExplorerContent(
        directories = uiState.subDirectories,
        images = uiState.images,
        openDirectory = openDirectory,
        openImage = openImage
    )

}

@Composable
fun FileExplorerContent(
    directories: List<FileDirectory>,
    images: List<ImageFile>,
    openDirectory: (directoryId: String) -> Unit,
    openImage: (imageId: String) -> Unit,
) {
    Column {
        Text(stringResource(R.string.fileExplorerTitle))
        LazyColumn {
            items(
                items = directories,
                key = { it.id }
            ) {
                ItemDirectory(it, openDirectory)
            }

            items(
                items = images,
                key = { it.id }
            ) {
                ItemImage(it, openImage)
            }
        }
    }
}

@Composable
fun ItemDirectory(fileDirectory: FileDirectory, onDirectorySelected: (id: String) -> Unit) {
    Row(
        modifier = Modifier.clickable {
            onDirectorySelected(fileDirectory.id)
        }
    ) {
        Icon(
            Icons.Rounded.Send,
            stringResource(R.string.contentDescription_DirectoryItem, fileDirectory.name)
        )
        Text(fileDirectory.name)
    }
}

@Composable
fun ItemImage(image: ImageFile, onOnImageSelected: (id: String) -> Unit) {
    Row(modifier = Modifier.clickable {
        onOnImageSelected(image.id)
    }) {
        Icon(Icons.Rounded.Email, stringResource(R.string.contentDescription_ImageItem, image.name))
        Text(image.name)
    }
}

@Preview(showBackground = true)
@Composable
fun FileExplorerPreview() {
    BeRealTestTheme {
        FileExplorerContent(emptyList(), emptyList(), {}, {})
    }
}
