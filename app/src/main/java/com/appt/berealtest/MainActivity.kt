package com.appt.berealtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appt.berealtest.ui.theme.BeRealTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeRealTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainActivityContent()
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(viewModel: FileExplorerViewModel = viewModel(factory = FileExplorerViewModel.Factory)) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn/{error}") {
            SignIn(
                error = uiState.error,
                signIn = { username, password -> viewModel.signIn(username, password) }
            )
        }
    }
    val uiState = viewModel.uiState.value

    if (uiState.selectedImage != null) {
        ImageHolder(uiState.selectedImage)
    } else if (uiState.signedIn) {
        FileExplorer(
            uiState.directories,
            uiState.images,
            viewModel::openDirectory,
            viewModel::selectImage
        )
    } else {
        SignIn(
            error = uiState.error,
            signIn = { username, password -> viewModel.signIn(username, password) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BeRealTestTheme {
        SignIn(error = false, signIn = { _, _ -> })
    }
}
