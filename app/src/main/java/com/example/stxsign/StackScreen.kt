package com.example.stxsign
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun StackScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, currentRoute: String?, coreViewModel: CoreViewModel) {

    var privateHashedKey by remember { mutableStateOf("************************************************************") }
    var publicSigningKey by remember { mutableStateOf("0377c011d562bb0203bf5adbd1c9325bbf19d7085fe80b23d30bf9f390b0fc32d8") }
    var currentNetwork by remember { mutableStateOf("Mainnet") }
    var newDenyAddress by remember { mutableStateOf("03........") }

    var hasChanges by remember { mutableStateOf(false) }

    var showAutoDenyAdd by remember { mutableStateOf(false) }

    var sliderValues by remember {
        mutableStateOf(.000000001f..100f)
    }

    var autoDenyAddresses = remember { mutableStateListOf<String>() }

    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        //color = MaterialTheme.colors.background
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.signviewbg),
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f
            )) {
            Column(modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 48.dp)
                .verticalScroll(state = ScrollState(initial = 0)),
                verticalArrangement = Arrangement.Top,
            ) {

                if (hasChanges) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .padding(bottom = 12.dp)
                        .padding(top = 24.dp)) {
                        Row(modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)) {
                            Row(modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )

                            {
                                Text(text = "Undo", modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { }, textAlign = TextAlign.Center, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Text(text = "Save", modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { }, textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                if (hasChanges) {
                    Text(text = "Basics", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 4.dp))
                } else {
                    Text(text = "Basics", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 20.dp))
                }

                Text(text = "This first batch of settings are related to the signer & connected btc/stx node.", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp))
                OutlinedTextField(
                    value = privateHashedKey,
                    onValueChange = {
                        privateHashedKey = it
                        hasChanges = true
                                    },
                    label = { Text("Private Hashed Key") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
                OutlinedTextField(
                    value = publicSigningKey,
                    onValueChange = { publicSigningKey = it
                        hasChanges = true},
                    label = { Text("Public Signing Key") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = currentNetwork,
                    onValueChange = { currentNetwork = it
                        hasChanges = true},
                    label = { Text("Current Network") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
                Text(text = "Approval Settings", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier)
                Text(text = "This second batch of settings configure what transactions (if any), to autosign.", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp))
                // Default for approve all
                // Below will be a ranged slider for min and/or max
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Transaction Limits", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier)
                    Text(text = "(btc)", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 4.dp))
                }
                RangeSlider(values = sliderValues, onValueChange = { sliderValues_ ->
                    sliderValues = sliderValues_
                },
                    valueRange = .000000001f..100f,
                    onValueChangeFinished = {
                        // this is called when the user completed selecting the value
                        Log.d(
                            "MainActivity",
                            "Start: ${sliderValues.start}, End: ${sliderValues.endInclusive}"
                        )
                    },
                colors = SliderDefaults.colors(
                    activeTrackColor = colorResource(id = R.color.handoff_orange_200),
                    thumbColor = colorResource(id = R.color.gray_button_400)
                )
                    )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)) {
                    Text(text = "%.9f".format(sliderValues.start))
                    Spacer(Modifier.weight(1f))
                    Text(text = "%.9f".format(sliderValues.endInclusive))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Auto-Deny List", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier)
                    Text(text = "(addresses)", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(start = 4.dp))
                    Spacer(Modifier.weight(1f))
                    if (showAutoDenyAdd) {
                        Box(modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                autoDenyAddresses.add(newDenyAddress)
                            }
                            .background(colorResource(id = R.color.gray_button_400), shape = CircleShape), contentAlignment = Alignment.Center) {
                            Text("+", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    } else {
                        Box(modifier = Modifier
                            .size(32.dp)
                            .background(colorResource(id = R.color.gray_button_400).copy(alpha = 0.25f), shape = CircleShape), contentAlignment = Alignment.Center) {
                            Text("+", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
                autoDenyAddresses.forEach { address ->
                    Text(text = address)
                }
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
                    OutlinedTextField(
                        value = newDenyAddress,
                        onValueChange = { newDenyAddress = it
                            hasChanges = true
                                        showAutoDenyAdd = true
                                        },
                        label = { Text("New reject address") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Text(text = "Demo Settings", fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier)
                // On / off
                // Randomize every 5 seconds
                // button to save any changes made
            }
        }
    }

}

