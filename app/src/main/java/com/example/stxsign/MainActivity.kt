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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.material3.*

//import StackScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            STXSignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainLayout() {
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
                SignScreen(navController = navController, navBackStackEntry, navBackStackEntry?.destination?.route)
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