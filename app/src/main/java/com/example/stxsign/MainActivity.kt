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
import android.util.Log
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
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random
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
        } else if (increasesConsensusBy == 0f) {
            transactionStatus.value = TransactionStatus.ABSTAIN
        } else if (increasesConsensusBy < 0) {
            transactionStatus.value = TransactionStatus.REJECT
        }

    }
}

// core viewModel
class CoreViewModel : ViewModel() {
    val _requests = mutableStateOf<List<Request>>(emptyList())
    val requests: List<Request> get() = _requests.value

    init {
        generateRandomRequests()
    }

    private fun generateRandomRequests() {
        val randomRequestCount = (4..10).random()
        val randomRequests = List(randomRequestCount) {
            val currentConsensus = mutableStateOf((0..100).random().toFloat())
            val transactionStatus = mutableStateOf(TransactionStatus.values().random())
            val transactionType = TransactionType.values().random()
            val isAutosigned = Random.nextBoolean()
            val request = Request(
                txID = "exampleTxID$it",
                transactionType = transactionType,
                transactionStatus = transactionStatus,
                heightMined = 100u,
                heightExpiring = 200u,
                isAutosigned = isAutosigned,
                transactionFees = 0.5f,
                transactionAmount = 10.0f,
                originatorAddress = "exampleOriginatorAddress$it",
                withdrawalAddress = "exampleWithdrawalAddress$it",
                depositAddress = "exampleDepositAddress$it",
                currentConsensus = currentConsensus,
                targetConsensus = 70.0f
            )
            request
        }

        _requests.value = randomRequests
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

    // Keep track of requests
    var requests = coreViewModel.requests.toMutableList()
    val triggerRecomposition = remember { mutableStateOf(Unit) }

    // Randomize co-routine
//    LaunchedEffect(triggerRecomposition.value) {
//        while (true) {
//            delay(3000) // Wait for 3 seconds
//
//            Log.d("loop","while loop is printing")
//
//            // Generate a random request
//            val randomRequest = generateRandomRequest()
//
//            Log.d("tag","requests size: " + requests.size.toString())
//
//            // Update the list of requests with the new request
//            Log.d("randomRequest", randomRequest.transactionStatus.toString())
//            requests.add(randomRequest)
//        }
//    }


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