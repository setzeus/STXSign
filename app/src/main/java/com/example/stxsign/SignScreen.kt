package com.example.stxsign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
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
fun SignScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, currentRoute: String?, coreViewModel: CoreViewModel) {

    val requests = coreViewModel.requests ?: emptyList()
    var unsignedRequests = requests.filter { it.transactionStatus.value == TransactionStatus.UNSIGNED }
    var signedRequests = requests.filter { it.transactionStatus.value != TransactionStatus.UNSIGNED}

    val requestsText = AnnotatedString.Builder().apply {
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline)
        ) {
            append("2")
        }
    }.toAnnotatedString()

    var overlayActive by remember { mutableStateOf(false) }

    var selectedOverlayRequest: Request? by remember { mutableStateOf(null) }

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
                .padding(top = 16.dp)
                ) {
                if (unsignedRequests.size != 0) {
                    Row() {
                        Text(text = "Pending Requests", fontSize = 28.sp, fontWeight = FontWeight.Black, modifier = Modifier, color = colorResource(
                            id = R.color.gray_button_400
                        ))
                        Row(modifier = Modifier
                            .padding(start = 8.dp)
                            .height(40.dp), verticalAlignment = Alignment.CenterVertically) {
                            Row(modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.gray_button_400),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp))
                            {
                                Text(text = unsignedRequests.size.toString(), color = Color.White, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                    Row(modifier = Modifier) {
                        Text("sort by", fontWeight = FontWeight.Light, fontSize = 16.sp, color = colorResource(
                            id = R.color.gray_button_400
                        ))
                        Text(" time to expire", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = colorResource(
                            id = R.color.gray_button_400
                        ))
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

                    Box(modifier = Modifier.padding(bottom = 20.dp)) {
                        LazyColumn(
                            modifier = Modifier,

                            ) {
                            itemsIndexed(unsignedRequests) { index, request ->
                                ClickableCard(
                                    request = request,
                                    onCardClicked = { selectedRequest ->
                                        // Show the selected request in the RequestOverlay
                                        overlayActive = true
                                        selectedOverlayRequest = selectedRequest
                                    }
                                )
                            }
                        }
                    }
                }

                Text(text = "Signed Requests", fontSize = 28.sp, fontWeight = FontWeight.Medium, modifier = Modifier, color = colorResource(id = R.color.gray_button_400))

                LazyColumn(modifier = Modifier.padding(bottom = 24.dp)) {
                    itemsIndexed(signedRequests) { index, request ->
                        ClickableRow(
                            request = request,
                            onRowClicked = { selectedRequest ->
                                // Show the selected request in the RequestOverlay
                                overlayActive = true
                                selectedOverlayRequest = selectedRequest
                            }
                        )
                    }
                }
            }
            if (overlayActive) {
                selectedOverlayRequest?.let {
                    RequestOverlay(
                        request = it,
                        onDismiss = { overlayActive = false }
                    )
                }
            }

        }
    }
}

@Composable
fun ClickableRow(onRowClicked: (Request) -> Unit, request: Request) {

    //val currentConsensusState = remember { mutableStateOf(request.currentConsensus) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onRowClicked(request) }
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
                                Text(text = "sBTC", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                                Spacer(Modifier.weight(1f))
                                if (request.transactionStatus.value == TransactionStatus.APPROVE) {
                                    Image(
                                        painter = painterResource(id = R.drawable.greencheckss),
                                        contentDescription = "green check",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else if (request.transactionStatus.value == TransactionStatus.REJECT) {
                                    Image(
                                        painter = painterResource(id = R.drawable.redcrossss),
                                        contentDescription = "red cross",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                if (request.transactionStatus.value == TransactionStatus.APPROVE) {
                                    Text(text = "Approved", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (request.transactionStatus.value == TransactionStatus.REJECT)  {
                                    Text(text = "Rejected", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                } else if (request.transactionStatus.value == TransactionStatus.ABSTAIN) {
                                    Text(text = "Abstained", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.gray_button_400), textAlign = TextAlign.Right)
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
                                Text(text = String.format("%.2f%% confirmed", request.currentConsensus.value), modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar(request = request)
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
                                Text(text = "Bitcoin", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier)
                                Spacer(Modifier.weight(1f))
                                if (request.transactionStatus.value == TransactionStatus.APPROVE) {
                                    Image(
                                        painter = painterResource(id = R.drawable.greencheckss),
                                        contentDescription = "green check",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else if (request.transactionStatus.value == TransactionStatus.REJECT) {
                                    Image(
                                        painter = painterResource(id = R.drawable.redcrossss),
                                        contentDescription = "red cross",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                if (request.transactionStatus.value == TransactionStatus.APPROVE) {
                                    Text(text = "Approved", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (request.transactionStatus.value == TransactionStatus.REJECT)  {
                                    Text(text = "Rejected", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                } else if (request.transactionStatus.value == TransactionStatus.ABSTAIN) {
                                    Text(text = "Abstained", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.gray_button_400), textAlign = TextAlign.Right)
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
                                Text(text = String.format("%.2f%% confirmed", request.currentConsensus.value), modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar(request = request)
                }
            } else if (request.transactionType == TransactionType.HANDOFF) {
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
                                if (request.transactionStatus.value == TransactionStatus.APPROVE) {
                                    Image(
                                        painter = painterResource(id = R.drawable.greencheckss),
                                        contentDescription = "green check",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else if (request.transactionStatus.value == TransactionStatus.REJECT) {
                                    Image(
                                        painter = painterResource(id = R.drawable.redcrossss),
                                        contentDescription = "red cross",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(0.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                if (request.transactionStatus.value == TransactionStatus.APPROVE) {
                                    Text(text = "Approved", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.green_approve_500), textAlign = TextAlign.Right)
                                } else if (request.transactionStatus.value == TransactionStatus.REJECT)  {
                                    Text(text = "Rejected", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.red_approve_500), textAlign = TextAlign.Right)
                                } else if (request.transactionStatus.value == TransactionStatus.ABSTAIN) {
                                    Text(text = "Abstained", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp), color = colorResource(id = R.color.gray_button_400), textAlign = TextAlign.Right)
                                }
                            }
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.padding(top = 0.dp)) {
                                    Row(modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.handoff_orange_200).copy(
                                                alpha = .5f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp))
                                    {
                                        Text(text = "Handoff", color = colorResource(id = R.color.handoff_orange_800), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Black)
                                    }
                                }
                                Spacer(Modifier.weight(1f))
                                Text(text = String.format("%.2f%% confirmed", request.currentConsensus.value), modifier = Modifier, style = MaterialTheme.typography.body2, textAlign = TextAlign.Right)
                            }
                        }
                    }
                    SigningProgressBar(request = request)
                }
            }

        }
    }

}

@Composable
fun SigningProgressBar(request: Request) {
    var signingProgress by remember { mutableStateOf(request.currentConsensus) }

    Column() {
        LinearProgressIndicator(
            progress = signingProgress.value/100,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(id = R.color.sbtc_yellow_500)
        )
    }
}

@Composable
fun SigningProgressBar2(request: MutableState<Request>) {

    Column {
        LinearProgressIndicator(
            progress = request.value.currentConsensus.value / 100,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(id = R.color.sbtc_yellow_500)
        )
    }
}