package com.example.stxsign

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stxsign.ui.theme.STXSignTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import bottomNavigationItems
import STXSignBottomNavigation
import Screen
import android.app.Activity
import android.view.View
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class TransactionType {
    DEPOSIT, WITHDRAWAL, HANDOFF
}

enum class TransactionStatus {
    ABSTAIN, APPROVE, REJECT, UNSIGNED
}

//data class Configuration

// Request Model
data class Request(
    val txID: String,
    val transactionType: TransactionType,
    var transactionStatus: MutableState<TransactionStatus>,
    val heightMined: UInt,
    val heightExpiring: UInt,
    val isAutosigned: Boolean,
    val transactionFees: Float,
    val transactionAmount: Float,
    val originatorAddress: String,
    val withdrawalAddress: String? = null,
    val depositAddress: String? = null,
    var currentConsensus: MutableState<Float>,
    val targetConsensus: Float
) {
    fun vote(increasesConsensusBy: Float) {
        val newConsensus = currentConsensus.value + increasesConsensusBy
        currentConsensus.value = newConsensus

        if (increasesConsensusBy > 0) {
            transactionStatus.value = TransactionStatus.APPROVE
        } else {
            transactionStatus.value = TransactionStatus.REJECT
        }

    }
}

// core viewModel
class CoreViewModel : ViewModel() {
    private val _requests = mutableStateListOf<Request>()
    val requests: List<Request> = _requests

    val currentConsensusState0 = mutableStateOf(34.54f)
    val currentConsensusState1 = mutableStateOf(22.75f)
    val currentConsensusState2 = mutableStateOf(1.13f)
    val currentConsensusState3 = mutableStateOf(63.50f)
    val currentConsensusState4 = mutableStateOf(2.75f)
    val currentConsensusState5 = mutableStateOf(70.23f)

    val transactionStatusState0 = mutableStateOf(TransactionStatus.UNSIGNED)
    val transactionStatusState1 = mutableStateOf(TransactionStatus.UNSIGNED)
    val transactionStatusState2 = mutableStateOf(TransactionStatus.APPROVE)
    val transactionStatusState3 = mutableStateOf(TransactionStatus.REJECT)
    val transactionStatusState4 = mutableStateOf(TransactionStatus.APPROVE)
    val transactionStatusState5 = mutableStateOf(TransactionStatus.REJECT)

    init {
        val randomRequests = listOf(
            Request(
                txID = "exampleTxID1",
                transactionType = TransactionType.DEPOSIT,
                transactionStatus = transactionStatusState0,
                heightMined = 100u,
                heightExpiring = 200u,
                isAutosigned = false,
                transactionFees = 0.5f,
                transactionAmount = 10.0f,
                originatorAddress = "exampleOriginatorAddress1",
                withdrawalAddress = "exampleWithdrawalAddress1",
                depositAddress = "exampleDepositAddress1",
                currentConsensus = currentConsensusState0,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID2",
                transactionType = TransactionType.WITHDRAWAL,
                transactionStatus = transactionStatusState1,
                heightMined = 150u,
                heightExpiring = 250u,
                isAutosigned = false,
                transactionFees = 0.8f,
                transactionAmount = 15.0f,
                originatorAddress = "exampleOriginatorAddress2",
                withdrawalAddress = "exampleWithdrawalAddress2",
                depositAddress = "exampleDepositAddress2",
                currentConsensus = currentConsensusState1,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID3",
                transactionType = TransactionType.DEPOSIT,
                transactionStatus = transactionStatusState2,
                heightMined = 200u,
                heightExpiring = 300u,
                isAutosigned = false,
                transactionFees = 1.2f,
                transactionAmount = 20.0f,
                originatorAddress = "exampleOriginatorAddress3",
                withdrawalAddress = "exampleWithdrawalAddress3",
                depositAddress = "exampleDepositAddress3",
                currentConsensus = currentConsensusState2,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID4",
                transactionType = TransactionType.WITHDRAWAL,
                transactionStatus = transactionStatusState3,
                heightMined = 100u,
                heightExpiring = 200u,
                isAutosigned = false,
                transactionFees = 0.5f,
                transactionAmount = 10.0f,
                originatorAddress = "exampleOriginatorAddress1",
                withdrawalAddress = "exampleWithdrawalAddress1",
                depositAddress = "exampleDepositAddress1",
                currentConsensus = currentConsensusState3,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID5",
                transactionType = TransactionType.WITHDRAWAL,
                transactionStatus = transactionStatusState4,
                heightMined = 150u,
                heightExpiring = 250u,
                isAutosigned = false,
                transactionFees = 0.8f,
                transactionAmount = 15.0f,
                originatorAddress = "exampleOriginatorAddress2",
                withdrawalAddress = "exampleWithdrawalAddress2",
                depositAddress = "exampleDepositAddress2",
                currentConsensus = currentConsensusState4,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID6",
                transactionType = TransactionType.DEPOSIT,
                transactionStatus = transactionStatusState5,
                heightMined = 200u,
                heightExpiring = 300u,
                isAutosigned = false,
                transactionFees = 1.2f,
                transactionAmount = 20.0f,
                originatorAddress = "exampleOriginatorAddress4",
                withdrawalAddress = "exampleWithdrawalAddress4",
                depositAddress = "exampleDepositAddress3",
                currentConsensus = currentConsensusState5,
                targetConsensus = 70.0f
            )
        )

        _requests.addAll(randomRequests)
    }
}


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coreViewModel = CoreViewModel()
            STXSignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainLayout(coreViewModel = coreViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainLayout(coreViewModel: CoreViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    
    
    Scaffold(
        bottomBar = { STXSignBottomNavigation(
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            currentRoute = navBackStackEntry?.destination?.route
        ) }
    ) {
        NavHost(navController = navController, startDestination = Screen.Sign.title) {
            composable(Screen.Stack.title) {
                StackScreen(navController = navController, navBackStackEntry, navBackStackEntry?.destination?.route, coreViewModel)
            }
            composable(Screen.Sign.title) {
                SignScreen(navController = navController, navBackStackEntry, navBackStackEntry?.destination?.route, coreViewModel)
            }
        }
    }
}

    

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    STXSignTheme {
//        Greeting("Android")
//    }
//}