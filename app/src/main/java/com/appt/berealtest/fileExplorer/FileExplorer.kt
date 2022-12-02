package com.appt.berealtest.fileExplorer

import FileDirectory
import ImageFile
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val uiState = viewModel.uiState

    FileExplorerContent(
        isLoading = uiState.isLoading,
        directories = uiState.subDirectories,
        images = uiState.images,
        openDirectory = openDirectory,
        openImage = openImage
    )
}

@Composable
fun FileExplorerContent(
    isLoading: Boolean,
    directories: List<FileDirectory>,
    images: List<ImageFile>,
    openDirectory: (directoryId: String) -> Unit,
    openImage: (imageId: String) -> Unit,
) {
    Column {
        Text(
            stringResource(R.string.fileExplorerTitle),
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(0.dp, 24.dp),
        )

        AnimatedVisibility(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.Companion
                    .align(CenterHorizontally)
                    .testTag("LoadingIndicator"),

                )
        }

        Items(directories, openDirectory, images, openImage)
    }
}

@Composable
private fun Items(
    directories: List<FileDirectory>,
    openDirectory: (directoryId: String) -> Unit,
    images: List<ImageFile>,
    openImage: (imageId: String) -> Unit
) {
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

@Composable
private fun ItemDirectory(fileDirectory: FileDirectory, onDirectorySelected: (id: String) -> Unit) {
    Row(modifier = Modifier
        .clickable {
            onDirectorySelected(fileDirectory.id)
        }
        .fillMaxSize()
        .padding(8.dp, 8.dp)
    ) {
        Icon(
            Icons.Rounded.Folder,
            stringResource(R.string.contentDescription_DirectoryItem, fileDirectory.name),
            modifier = Modifier
                .align(CenterVertically)
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        )
        Text(fileDirectory.name)
    }
}

@Composable
private fun ItemImage(image: ImageFile, onOnImageSelected: (id: String) -> Unit) {
    Row(modifier = Modifier
        .clickable {
            onOnImageSelected(image.id)
        }
        .fillMaxSize()
        .padding(8.dp, 8.dp)
    ) {
        Icon(
            tint = Color.Blue,
            imageVector = Icons.Rounded.Image,
            contentDescription = stringResource(R.string.contentDescription_ImageItem, image.name),
            modifier = Modifier
                .align(CenterVertically)
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        )
        Text(image.name)
    }
}


@Preview(showBackground = true)
@Composable
fun FileExplorerPreview() {
    BeRealTestTheme {
        FileExplorerContent(
            false,
            listOf(
                FileDirectory("123", "A Directory"),
                FileDirectory("456", "Another Directory With A Really Really Really Long Name"),
            ),
            listOf(
                ImageFile("654", "An Image"),
                ImageFile("321", "An Image With A Really Really Really Long Filename")
            ), {}, {})
    }
}
