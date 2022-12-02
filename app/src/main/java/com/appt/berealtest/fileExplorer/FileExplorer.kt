package com.appt.berealtest.fileExplorer

import com.appt.berealtest.models.FileDirectory
import com.appt.berealtest.models.ImageFile
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
        openImage = openImage,
        createDirectory = {
            viewModel.createDirectory(
                parentDirectoryId = directoryId,
                newDirectoryName = it,
                onSuccess = { openDirectory(directoryId) },
                onError = {
                    // TODO: Display error to the user
                }
            )
        }
    )
}

@Composable
fun FileExplorerContent(
    isLoading: Boolean,
    directories: List<FileDirectory>,
    images: List<ImageFile>,
    openDirectory: (directoryId: String) -> Unit,
    openImage: (imageId: String) -> Unit,
    createDirectory: (directoryId: String) -> Unit,
) {
    Column {

        Row(modifier = Modifier.align(CenterHorizontally)) {
            val openDialog = remember { mutableStateOf(false) }

            Text(
                stringResource(R.string.fileExplorerTitle),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(0.dp, 24.dp),
            )

            Button(
                onClick = { openDialog.value = true },
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(16.dp, 0.dp, 8.dp, 0.dp)
            ) {
                Icon(
                    Icons.Rounded.Add,
                    stringResource(R.string.contentDescription_DirectoryItem)
                )
            }

            AnimatedVisibility(openDialog.value) {
                DirectoryDialog(openDialog, createDirectory)
            }
        }

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

private val directoryNameValidation = Regex("^[a-zA-Z0-9]+$")

@Composable
private fun DirectoryDialog(
    openDialog: MutableState<Boolean>,
    createDirectory: (directoryId: String) -> Unit
) {
    var filename by remember { mutableStateOf(TextFieldValue()) }
    var filenameError by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { openDialog.value = false },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp, 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Text(
                stringResource(R.string.createDirectoryTitle),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(CenterHorizontally)
            )

            Text(
                stringResource(R.string.filenameInfo),
            )

            TextField(
                value = filename,
                onValueChange = { filename = it },
                label = { Text(stringResource(R.string.directoryName)) },
                isError = filenameError,
                modifier = Modifier
                    .fillMaxWidth()
            )


            Button(onClick = {
                val directoryId = filename.text
                if (directoryNameValidation.matches(directoryId)) {
                    createDirectory(directoryId)
                } else {
                    filenameError = true
                }

            }
            ) {
                Icon(
                    Icons.Rounded.Done,
                    stringResource(R.string.contentDescription_Confirm)
                )
            }
        }
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


@Preview(showBackground = true, showSystemUi = true)
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
            ),
            {}, {}, {}
        )
    }
}
