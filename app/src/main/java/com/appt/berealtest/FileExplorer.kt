package com.appt.berealtest

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.appt.berealtest.ui.theme.BeRealTestTheme


@Composable
fun FileExplorer() {
    Text(text = "Explorer")
}

@Preview(showBackground = true)
@Composable
fun FileExplorerPreview() {
    BeRealTestTheme {
        FileExplorer()
    }
}
