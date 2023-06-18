package com.example.stxsign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp

@Composable
fun StackScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, currentRoute: String?, coreViewModel: CoreViewModel) {

    var privateHashedKey by remember { mutableStateOf("Hello") }
    var publicSigningKey by remember { mutableStateOf("Hello") }
    var currentNetwork by remember { mutableStateOf("Hello") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        //color = MaterialTheme.colors.background
    ) {
        Box(modifier = Modifier.fillMaxSize().paint(
            painterResource(id = R.drawable.signviewbg),
            contentScale = ContentScale.FillBounds,
            alpha = 0.5f
        )) {
            Column(modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                Text(text = "Basics", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier)
                Text(text = "This first batch of settings are related to the signer & connected btc/stx node.", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
                OutlinedTextField(
                    value = privateHashedKey,
                    onValueChange = { privateHashedKey = it },
                    label = { Text("Private Hashed Key") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                OutlinedTextField(
                    value = publicSigningKey,
                    onValueChange = { publicSigningKey = it },
                    label = { Text("Public Signing Key") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = currentNetwork,
                    onValueChange = { currentNetwork = it },
                    label = { Text("Current Network") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
                Text(text = "Approval Settings", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier)
                Text(text = "This second batch of settings configure what transactions (if any), to autosign.", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
                // Default for approve all
                // Below will be a ranged slider for min and/or max
                OutlinedTextField(
                    value = currentNetwork,
                    onValueChange = { currentNetwork = it },
                    label = { Text("Transaction Sizes") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
                Text(text = "Demo Settings", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier)
                // On / off
                // Randomize every 5 seconds
                // button to save any changes made
            }
        }
    }
}