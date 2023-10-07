package com.perspectivev.jetpackintro.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perspectivev.jetpackintro.ui.theme.JetPackIntroTheme


@Composable
@ExperimentalMaterial3Api
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = "Choose An Option")

        Button(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ }) {
            Text(text = "Goto First")
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(), onClick = { /*TODO*/ }) {
            Text(text = "Goto Second")
        }
    }
}


@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
@ExperimentalMaterial3Api
fun HomeScreenPreview() {
    JetPackIntroTheme {
        Surface {
            HomeScreen()
        }
    }
}