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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.stxsign.RequestOverlay
import androidx.compose.runtime.getValue

@Composable
fun ClickableCard(onCardClicked: (Request) -> Unit, request: Request) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClicked(request) }
            .shadow(elevation = 4.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            if (request.transactionType == TransactionType.DEPOSIT) {
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
                                Text(text = "sBTC", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier, color = colorResource(
                                    id = R.color.gray_button_400
                                )
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.pendingiconss),
                                    contentDescription = "red cross",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(0.dp),
                                    contentScale = ContentScale.Fit
                                )
                                Text(text = "Unsigned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.gray_button_400), textAlign = TextAlign.Right)
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
                                Text(text = String.format("%.2f%% confirmed", request.currentConsensus.value), modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar(request = request)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .padding(vertical = 12.dp)
                        .padding(horizontal = 16.dp)) {
                        Row(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                            {
                                Text(text = "Approve", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)) {
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
                                Text(text = "Deny", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center ,color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(text = "Dismiss", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = colorResource(id = R.color.gray_button_400), fontSize = 20.sp, style = MaterialTheme.typography.body2)
                    }
                }
            } else if (request.transactionType == TransactionType.WITHDRAWAL) {
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
                                Text(text = "Bitcoin", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier, color = colorResource(
                                    id = R.color.gray_button_400
                                )
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.pendingiconss),
                                    contentDescription = "red cross",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(0.dp),
                                    contentScale = ContentScale.Fit
                                )
                                Text(text = "Unsigned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.gray_button_400), textAlign = TextAlign.Right)
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
                                Text(    text = String.format("%.2f%% confirmed", request.currentConsensus.value), modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar(request = request)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .padding(vertical = 12.dp)
                        .padding(horizontal = 16.dp)) {
                        Row(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                                //.width(112.dp),
                                verticalAlignment = Alignment.CenterVertically
                                //.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                            {
                                Text(text = "Approve", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.White, fontSize = 20.sp, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)) {
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