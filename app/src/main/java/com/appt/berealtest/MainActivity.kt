package com.appt.berealtest

import FileExplorerViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appt.berealtest.ui.theme.BeRealTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeRealTestTheme {
                // A surface container using the 'background' color from the theme
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
fun MainActivityContent(viewModel: FileExplorerViewModel = viewModel()) {
    if (viewModel.signedIn.value) {
        SignIn(error = false, signIn = { _, _ -> })
    } else {
        FileExplorer()
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BeRealTestTheme {
        FileExplorer()
    }
}
