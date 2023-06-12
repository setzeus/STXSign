package com.example.stxsign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
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
        Box(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.signviewbg),
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f
            )) {
            Column( modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp)) {
                Row() {
                    Text(text = "Pending Requests", fontSize = 28.sp, fontWeight = FontWeight.Black, modifier = Modifier)
                    Row(modifier = Modifier
                        .padding(start = 8.dp)
                        .height(40.dp), verticalAlignment = Alignment.CenterVertically) {
                        Row(modifier = Modifier
                            .background(
                                color = colorResource(id = R.color.gray_text_100),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp))
                        {
                            Text(text = "2", color = Color.White, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                        }
                    }
                }
                Row(modifier = Modifier) {
                    Text("sort by", fontWeight = FontWeight.Light, fontSize = 16.sp)
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
                            painter = painterResource(id = R.drawable.chevtopiconss),
                            contentDescription = "Restfully Placeholder Coach Image",
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.Center)
                                .rotate(180f),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                ClickableCard(onRowClicked = {}, isDeposit = true)
                ClickableCard(onRowClicked = {}, isDeposit = false)
                Text(text = "Pending Requests", fontSize = 28.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 8.dp), color = colorResource(
                    id = R.color.gray_button_400
                ))
                ClickableRow(onRowClicked = { overlayActive = true }, isDeposit = true, signedAuto = true, signedVote = true)
                ClickableRow(onRowClicked = { overlayActive = true }, isDeposit = true, signedAuto = true, signedVote = false)
                ClickableRow(onRowClicked = { overlayActive = true }, isDeposit = false, signedAuto = false, signedVote = false)
                ClickableRow(onRowClicked = { overlayActive = true }, isDeposit = false, signedAuto = true, signedVote = true)
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
                            modifier = Modifier.padding(horizontal = 24.dp), fontSize = 16.sp)
                        Image(
                            painter = painterResource(id = R.drawable.sbtctobtcdiagram),
                            contentDescription = "Deposit Request Image",
                            modifier = Modifier
                                .heightIn(min = 0.dp, max = 128.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 8.dp),
                            contentScale = ContentScale.FillWidth
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.walleticonss),
                                contentDescription = "Wallet Icon",
                                modifier = Modifier
                                    .size(28.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Address",
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.weight(1f))
                            Text("2MwS....6rJo",
                                textAlign = TextAlign.Right,
                                //modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 16.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.bitcoiniconss),
                                contentDescription = "Bitcoin Icon",
                                modifier = Modifier
                                    .size(28.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Amount",
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.weight(1f))
                            Text("0.18402042",
                                textAlign = TextAlign.Right,
                                //modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 16.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.feeiconss),
                                contentDescription = "Fees Icon",
                                modifier = Modifier
                                    .size(28.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Fees",
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.weight(1f))
                            Text("0.000251",
                                textAlign = TextAlign.Right,
                                fontSize = 16.sp
                            )
                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Divider(modifier = Modifier
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 48.dp))
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.percenticonss),
                                contentDescription = "% Icon",
                                modifier = Modifier
                                    .size(28.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Confirmed",
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.weight(1f))
                            Text("42.53%",
                                textAlign = TextAlign.Right,
                                //modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 16.sp
                            )

                        }
                        //Spacer(modifier = Modifier.weight(0.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.blockiconss),
                                contentDescription = "Block Icon",
                                modifier = Modifier
                                    .size(28.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text("Expiration",
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(Modifier.weight(1f))
                            Text("56 blocks",
                                textAlign = TextAlign.Right,
                                //modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 16.sp
                            )
                        }
                        // Last of Overlays 5 Rows
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(vertical = 12.dp).padding(horizontal = 16.dp)) {
                            Row(modifier = Modifier.weight(1f)) {
                                Row(modifier = Modifier
                                    .background(
                                        color = colorResource(id = R.color.gray_button_400),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(vertical = 6.dp).fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                )
                                {
                                    Text(text = "Approve", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                                }
                            }
                            Row(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                                Row(modifier = Modifier.border(width = 1.dp, color =  colorResource(id = R.color.gray_button_400), shape = RoundedCornerShape(16.dp))
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(vertical = 6.dp).fillMaxWidth(),
                                    //.width(112.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                    //.padding(horizontal = 12.dp, vertical = 4.dp)
                                )

                                {
                                    Text(text = "Deny", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center ,color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                                }
                            }
                        }


                    }
                }
            }

        }
    }
}

//@Composable
//fun ClearButton(onClick: () -> Unit) {
//    var rotated by remember { mutableStateOf(false) }
//
//    IconButton(
//        onClick = {
//            rotated = !rotated
//            onClick()
//        },
//        modifier = Modifier.rotate(if (rotated) -90f else 0f)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.chevlefticonss),
//            contentDescription = "Restfully Placeholder Coach Image",
//            modifier = Modifier.size(20.dp),
//            contentScale = ContentScale.Fit
//        )
//    }
//}

@Composable
fun ClickableRow(onRowClicked: () -> Unit, isDeposit: Boolean, signedAuto: Boolean, signedVote: Boolean) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onRowClicked() }
            .shadow(elevation = 4.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            if (isDeposit) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Row(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.sbtclogo2),
                            contentDescription = "sbtc icon",
                            modifier = Modifier
                                .size(56.dp)
                                .padding(0.dp),
                            contentScale = ContentScale.Fit
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "sBTC", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                                Spacer(Modifier.weight(1f))
                                if (signedVote) {
                                    Image(
                                        painter = painterResource(id = R.drawable.greencheckss),
                                        contentDescription = "green check",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.redcrossss),
                                        contentDescription = "red cross",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                if (signedAuto && signedVote) {
                                    Text(text = "Autosigned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (!signedAuto && !signedVote)  {
                                    Text(text = "Signed", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                } else if (!signedAuto && signedVote) {
                                    Text(text = "Signed", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (signedAuto && !signedVote) {
                                    Text(text = "Autosigned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                }
                            }
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.padding(top = 0.dp)) {
                                    Row(modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.blue_tag_200).copy(
                                                alpha = .5f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp))
                                    {
                                        Text(text = "Deposit", color = colorResource(id = R.color.blue_text_800), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                                    }
                                }
                                Spacer(Modifier.weight(1f))
                                Text(text = "53% confirmed", modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar()
                }
            } else {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Row(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.btclogo2),
                            contentDescription = "btc icon",
                            modifier = Modifier
                                .size(56.dp)
                                .padding(0.dp),
                            contentScale = ContentScale.Fit
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Bitcoin", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                                Spacer(Modifier.weight(1f))
                                if (signedVote) {
                                    Image(
                                        painter = painterResource(id = R.drawable.greencheckss),
                                        contentDescription = "green check",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.redcrossss),
                                        contentDescription = "red cross",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                if (signedAuto && signedVote) {
                                    Text(text = "Autosigned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (!signedAuto && !signedVote)  {
                                    Text(text = "Signed", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                } else if (!signedAuto && signedVote) {
                                    Text(text = "Signed", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (signedAuto && !signedVote) {
                                    Text(text = "Autosigned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                }
                            }
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.padding(top = 0.dp)) {
                                    Row(modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.yellow_tag_200).copy(
                                                alpha = .5f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp))
                                    {
                                        Text(text = "Withdraw", color = colorResource(id = R.color.yellow_text_800), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                                    }
                                }
                                Spacer(Modifier.weight(1f))
                                Text(text = "53% confirmed", modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar()
                }
            }

        }
    }

}

@Composable
fun ClickableCard(onRowClicked: () -> Unit, isDeposit: Boolean) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onRowClicked() }
            .shadow(elevation = 4.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            if (isDeposit) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Row(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.sbtclogo2),
                            contentDescription = "sbtc icon",
                            modifier = Modifier
                                .size(56.dp)
                                .padding(0.dp),
                            contentScale = ContentScale.Fit
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "sBTC", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.chevrighticonss),
                                    contentDescription = "right arrow",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(0.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.padding(top = 0.dp)) {
                                    Row(modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.blue_tag_200).copy(
                                                alpha = .5f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp))
                                    {
                                        Text(text = "Deposit", color = colorResource(id = R.color.blue_text_800), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                                    }
                                }
                                Spacer(Modifier.weight(1f))
                                Text(text = "53% confirmed", modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar()
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(vertical = 12.dp).padding(horizontal = 16.dp)) {
                        Row(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp).fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                            {
                                Text(text = "Approve", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                            Row(modifier = Modifier.border(width = 1.dp, color =  colorResource(id = R.color.gray_button_400), shape = RoundedCornerShape(16.dp))
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp).fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )

                            {
                                Text(text = "Deny", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center ,color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(text = "Dismiss", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2)
                    }
                }
            } else {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Row(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.btclogo2),
                            contentDescription = "btc icon",
                            modifier = Modifier
                                .size(56.dp)
                                .padding(0.dp),
                            contentScale = ContentScale.Fit
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Bitcoin", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.chevrighticonss),
                                    contentDescription = "right arrow",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(0.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.padding(top = 0.dp)) {
                                    Row(modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.yellow_tag_200).copy(
                                                alpha = .5f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp))
                                    {
                                        Text(text = "Withdraw", color = colorResource(id = R.color.yellow_text_800), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                                    }
                                }
                                Spacer(Modifier.weight(1f))
                                Text(text = "53% confirmed", modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar()
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(vertical = 12.dp).padding(horizontal = 16.dp)) {
                        Row(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp).fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                            {
                                Text(text = "Approve", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                            Row(modifier = Modifier.border(width = 1.dp, color =  colorResource(id = R.color.gray_button_400), shape = RoundedCornerShape(16.dp))
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp).fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )

                            {
                                Text(text = "Deny", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center ,color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(text = "Dismiss", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2)
                    }
                }
            }

        }
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