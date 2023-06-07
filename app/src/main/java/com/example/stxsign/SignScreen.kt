package com.example.stxsign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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

    val requestsText = AnnotatedString.Builder().apply {
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline)
        ) {
            append("2")
        }
    }.toAnnotatedString()

    var overlayActive by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column( modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)) {
                Row() {
                    Text(text = requestsText, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                    Text(text = " Pending Reqs", fontSize = 36.sp, fontWeight = FontWeight.Light, modifier = Modifier)
                }
                Row(modifier = Modifier) {
                    Text("sort by", fontWeight = FontWeight.ExtraLight, fontSize = 16.sp)
                    Text(" time to expire", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = colorResource(id = R.color.offwhite_100),
                                shape = CircleShape
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.filtericonss2),
                            contentDescription = "Restfully Placeholder Coach Image",
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.listiconss),
                        contentDescription = "Restfully Placeholder Coach Image",
                        modifier = Modifier.size(18.dp),
                        contentScale = ContentScale.Fit
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gridiconss),
                        contentDescription = "Restfully Placeholder Coach Image",
                        modifier = Modifier.size(18.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                ClickableRow(onRowClicked = { overlayActive = true })
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)) {
                    // sbtc or btc logo
                    Image(
                        painter = painterResource(id = R.drawable.btclogo),
                        contentDescription = "Restfully Placeholder Coach Image",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(0.dp),
                        contentScale = ContentScale.Fit
                    )
                    // Column containing detail row & slider row
                    Column(modifier = Modifier) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "Withdraw", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                            Spacer(Modifier.weight(1f))
                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Row(modifier = Modifier
                                    .background(
                                        color = colorResource(id = R.color.green_tag_500).copy(alpha = .5f),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = "autosigned", color = colorResource(id = R.color.green_text_900), style = MaterialTheme.typography.body2)
                                }
                            }
                            Spacer(Modifier.weight(10f))
                            ClearButton(onClick = { /* Handle button click */ })
                        }
                        SigningProgressBar()
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)) {
                    // sbtc or btc logo
                    Image(
                        painter = painterResource(id = R.drawable.sbtclogo),
                        contentDescription = "Restfully Placeholder Coach Image",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(0.dp),
                        contentScale = ContentScale.Fit
                    )
                    // Column containing detail row & slider row
                    Column(modifier = Modifier) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "Deposit", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                            Spacer(Modifier.weight(1f))
                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Row(modifier = Modifier
                                    .background(
                                        color = colorResource(id = R.color.green_tag_500).copy(alpha = .5f),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = "autosigned", color = colorResource(id = R.color.green_text_900), style = MaterialTheme.typography.body2)
                                }
                            }
                            Spacer(Modifier.weight(10f))
                            ClearButton(onClick = { /* Handle button click */ })
                        }
                        SigningProgressBar()
                    }
                }
            }
            if (overlayActive) {
                Box() {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA424242))
                        .clickable { overlayActive = false }) {

                    }
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                        .padding(bottom = 80.dp)
                        .align(Alignment.Center)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Deposit Request", fontSize = 36.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 32.dp))
                        Text("Your signature vote is manually requested for this deposit request.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp))
                        Image(
                            painter = painterResource(id = R.drawable.depositrequestimage),
                            contentDescription = "Deposit Request Image",
                            modifier = Modifier
                                .size(256.dp)
                                .padding(0.dp),
                            contentScale = ContentScale.Fit
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth().height(48.dp)
                            .padding(horizontal = 48.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.walleticonss),
                                contentDescription = "Restfully Placeholder Coach Image",
                                modifier = Modifier
                                    .size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(Modifier.weight(1f))
                            Text("2MwSNR......j36rJo",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 20.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth().height(48.dp)
                            .padding(horizontal = 48.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.bitcoiniconss),
                                contentDescription = "Restfully Placeholder Coach Image",
                                modifier = Modifier
                                    .size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(Modifier.weight(1f))
                            Text("0.184028000 total",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 20.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth().height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.feeiconss),
                                contentDescription = "Restfully Placeholder Coach Image",
                                modifier = Modifier
                                    .size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(Modifier.weight(1f))
                            Text("0.0000531 fees",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 20.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Divider(modifier = Modifier
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 48.dp))
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth().height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.percenticonss),
                                contentDescription = "Restfully Placeholder Coach Image",
                                modifier = Modifier
                                    .size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.weight(0.5f))
                            Text("34.64% confirmed",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 20.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth().height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.blockiconss),
                                contentDescription = "Restfully Placeholder Coach Image",
                                modifier = Modifier
                                    .size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(Modifier.weight(1f))
                            Text("54 blocks to expire",
                                textAlign = TextAlign.Right,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ClickableRow(onRowClicked: () -> Unit) {

//    onClick = {
//        onClick()
//    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .clickable { onRowClicked() }
    ) {
        // sbtc or btc logo
        Image(
            painter = painterResource(id = R.drawable.sbtclogo),
            contentDescription = "Restfully Placeholder Coach Image",
            modifier = Modifier
                .size(48.dp)
                .padding(0.dp),
            contentScale = ContentScale.Fit
        )
        // Column containing detail row & slider row
        Column(modifier = Modifier) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(text = "Deposit", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                Spacer(Modifier.weight(1f))
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Row(modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.green_tag_500).copy(alpha = .5f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = "autosigned", color = colorResource(id = R.color.green_text_900), style = MaterialTheme.typography.body2)
                    }
                }
                Spacer(Modifier.weight(10f))
                ClearButton(onClick = { /* Handle button click */ })
            }
            SigningProgressBar()
        }
    }
}

@Composable
fun ClearButton(onClick: () -> Unit) {
    var rotated by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            rotated = !rotated
            onClick()
        },
        modifier = Modifier.rotate(if (rotated) -90f else 0f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.chevlefticonss),
            contentDescription = "Restfully Placeholder Coach Image",
            modifier = Modifier.size(20.dp),
            contentScale = ContentScale.Fit
        )
//        Icon(
//            Icons.Default.KeyboardArrowLeft,
//            contentDescription = "open",
//            modifier = Modifier.size(32.dp),
//        )
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
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(id = R.color.sbtc_yellow_500)
        )
    }
}