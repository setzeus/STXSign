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
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.Composable
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

// Request Model
data class Request(
    val txID: String,
    val transactionType: TransactionType,
    var transactionStatus: TransactionStatus,
    val heightMined: UInt,
    val heightExpiring: UInt,
    val isAutosigned: Boolean,
    val transactionFees: Float,
    val transactionAmount: Float,
    val originatorAddress: String,
    val withdrawalAddress: String? = null,
    val depositAddress: String? = null,
    var currentConsensus: Float,
    val targetConsensus: Float
) {
    fun vote(increasesConsensusBy: Float): Float {
        val newConsensus = currentConsensus + increasesConsensusBy
        currentConsensus = newConsensus

        if (newConsensus >= targetConsensus) {
            transactionStatus = if (newConsensus > targetConsensus) {
                TransactionStatus.APPROVE
            } else {
                TransactionStatus.REJECT
            }
        }

        return currentConsensus
    }
}

// core viewModel
class CoreViewModel : ViewModel() {
    private val _requests = MutableLiveData<List<Request>>()
    val requests: LiveData<List<Request>> = _requests

    init {
        val randomRequests = listOf(
            Request(
                txID = "exampleTxID1",
                transactionType = TransactionType.DEPOSIT,
                transactionStatus = TransactionStatus.UNSIGNED,
                heightMined = 100u,
                heightExpiring = 200u,
                isAutosigned = false,
                transactionFees = 0.5f,
                transactionAmount = 10.0f,
                originatorAddress = "exampleOriginatorAddress1",
                withdrawalAddress = "exampleWithdrawalAddress1",
                depositAddress = "exampleDepositAddress1",
                currentConsensus = 34.54f,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID2",
                transactionType = TransactionType.WITHDRAWAL,
                transactionStatus = TransactionStatus.UNSIGNED,
                heightMined = 150u,
                heightExpiring = 250u,
                isAutosigned = false,
                transactionFees = 0.8f,
                transactionAmount = 15.0f,
                originatorAddress = "exampleOriginatorAddress2",
                withdrawalAddress = "exampleWithdrawalAddress2",
                depositAddress = "exampleDepositAddress2",
                currentConsensus = 22.75f,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID3",
                transactionType = TransactionType.DEPOSIT,
                transactionStatus = TransactionStatus.APPROVE,
                heightMined = 200u,
                heightExpiring = 300u,
                isAutosigned = false,
                transactionFees = 1.2f,
                transactionAmount = 20.0f,
                originatorAddress = "exampleOriginatorAddress3",
                withdrawalAddress = "exampleWithdrawalAddress3",
                depositAddress = "exampleDepositAddress3",
                currentConsensus = 1.13f,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID4",
                transactionType = TransactionType.WITHDRAWAL,
                transactionStatus = TransactionStatus.APPROVE,
                heightMined = 100u,
                heightExpiring = 200u,
                isAutosigned = false,
                transactionFees = 0.5f,
                transactionAmount = 10.0f,
                originatorAddress = "exampleOriginatorAddress1",
                withdrawalAddress = "exampleWithdrawalAddress1",
                depositAddress = "exampleDepositAddress1",
                currentConsensus = 63.50f,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID5",
                transactionType = TransactionType.WITHDRAWAL,
                transactionStatus = TransactionStatus.REJECT,
                heightMined = 150u,
                heightExpiring = 250u,
                isAutosigned = false,
                transactionFees = 0.8f,
                transactionAmount = 15.0f,
                originatorAddress = "exampleOriginatorAddress2",
                withdrawalAddress = "exampleWithdrawalAddress2",
                depositAddress = "exampleDepositAddress2",
                currentConsensus = 2.75f,
                targetConsensus = 70.0f
            ),
            Request(
                txID = "exampleTxID6",
                transactionType = TransactionType.DEPOSIT,
                transactionStatus = TransactionStatus.APPROVE,
                heightMined = 200u,
                heightExpiring = 300u,
                isAutosigned = false,
                transactionFees = 1.2f,
                transactionAmount = 20.0f,
                originatorAddress = "exampleOriginatorAddress4",
                withdrawalAddress = "exampleWithdrawalAddress4",
                depositAddress = "exampleDepositAddress3",
                currentConsensus = 70.23f,
                targetConsensus = 70.0f
            )
        )

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
    
    
    Scaffold(
        bottomBar = { STXSignBottomNavigation(
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            currentRoute = navBackStackEntry?.destination?.route
        ) }
    ) {
        NavHost(navController = navController, startDestination = Screen.Sign.title) {
            composable(Screen.Stack.title) {
                StackScreen()
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