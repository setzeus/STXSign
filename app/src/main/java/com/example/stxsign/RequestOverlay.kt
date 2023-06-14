package com.example.stxsign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RequestOverlay(request: Request, onDismiss: () -> Unit){
    Box() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xAA424242))
            .clickable { onDismiss() }) {
        }

        if(request.transactionType == TransactionType.DEPOSIT) {
            Column(modifier = Modifier
                //.fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
                .padding(bottom = 128.dp)
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
                    request.depositAddress?.let {
                        Text(
                            it,
                            textAlign = TextAlign.Right,
                            fontSize = 16.sp
                        )
                    }
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
                    .padding(horizontal = 48.dp)
                    .padding(bottom = 12.dp),
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
                SigningProgressBar()
                // Bottom CTAs
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
                Text(text = "Dismiss", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Medium)
            }
        } else {
            Column(modifier = Modifier
                //.fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
                .padding(bottom = 128.dp)
                .align(Alignment.Center)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White)
                .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Withdraw Request", fontSize = 36.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 32.dp))
                Text("Your signature vote is manually requested for this withdraw request.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp), fontSize = 16.sp)
                Image(
                    painter = painterResource(id = R.drawable.btctosbtcdiagram),
                    contentDescription = "Withdraw Request Image",
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
                    request.depositAddress?.let {
                        Text(
                            it,
                            textAlign = TextAlign.Right,
                            fontSize = 16.sp
                        )
                    }
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
                    .padding(horizontal = 48.dp)
                    .padding(bottom = 12.dp),
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
                SigningProgressBar()
                // Bottom CTAs
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
                Text(text = "Dismiss", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Medium)
            }
        }

    }
}