package com.appt.berealtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appt.berealtest.fileExplorer.FileExplorer
import com.appt.berealtest.imageViewer.ImageViewer
import com.appt.berealtest.signIn.SignIn
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
                    val navController = rememberNavController()
                    NavigationComponent(navController)
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "signIn"
    ) {
        val openDirectory = { directoryId: String ->
            navController.navigate("fileExplorer/${directoryId}")
        }

        val openImage = { imageId: String ->
            navController.navigate("imageHolder/${imageId}")
        }

        val signOut = {
            navController.navigate("signIn") {
                popUpTo("signIn") { inclusive = true }
            }
        }

        composable("signIn") {
            SignIn(openDirectory)
        }

        composable("fileExplorer/{directoryId}") {
            val directoryId = it.arguments?.getString("directoryId")!!

            FileExplorer(directoryId, openDirectory, openImage, signOut)
        }

        composable("imageHolder/{imageId}") {
            val imageId = it.arguments?.getString("imageId")!!

            ImageViewer(imageId)
        }
    }
}
