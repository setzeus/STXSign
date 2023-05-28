package com.example.stxsign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, currentRoute: String?) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // sbtc or btc logo
            Image(
                painter = painterResource(id = R.drawable.sbtclogo),
                contentDescription = "Restfully Placeholder Coach Image",
                modifier = Modifier
                    .size(56.dp)
                    .padding(0.dp),
                contentScale = ContentScale.Fit
            )
            // Column containing detail row & slider row
            Column {
                Row(modifier = Modifier) {
                    Text(text = "Deposit", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "vote required", fontSize = 20.sp, fontStyle = FontStyle.Italic)
                    Spacer(modifier = Modifier.weight(1f))
                    ClearButton(onClick = { /* Handle button click */ })
                }
                SigningProgressBar()
            }
        }
    }
}

@Composable
fun ClearButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "open")
    }
}

@Composable
fun SigningProgressBar() {
    var signingProgress by remember { mutableStateOf(0.0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Simulate signing progress
        coroutineScope.launch {
            for (progress in 0 until 100) {
                delay(1000) // Adjust the delay to match your actual signing process
                signingProgress = (progress + 1).toFloat()
            }
        }
    }

    Column() {
        LinearProgressIndicator(
            progress = signingProgress / 100,
            modifier = Modifier.fillMaxWidth()
        )
    }
}