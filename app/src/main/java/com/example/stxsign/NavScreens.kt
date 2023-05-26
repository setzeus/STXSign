import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry

sealed class Screen(val route:String, val title:String, val icon:ImageVector) {
    object Stack: Screen("stack", title = "Stack", icon = Icons.Default.Person)
    object Sign: Screen("sign", title = "Sign", icon = Icons.Default.Refresh)
}

data class BottomNavigationItem(val route: String, val icon: ImageVector, val iconContentDescription: String)

val bottomNavigationItems = listOf(
    BottomNavigationItem(Screen.Sign.title, Icons.Default.Add, Screen.Sign.title),
    BottomNavigationItem(Screen.Stack.title, Icons.Default.Person, Screen.Stack.title),
)

@Composable
fun STXSignBottomNavigation(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, currentRoute: String?) {
    BottomNavigation(modifier = Modifier, backgroundColor = androidx.compose.material.MaterialTheme.colors.background, contentColor = androidx.compose.material.MaterialTheme.colors.secondary) {
        //val currentRoute = navBackStackEntry?.destination?.route

        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                unselectedContentColor = androidx.compose.material.MaterialTheme.colors.secondaryVariant,
                icon = { Icon(imageVector = item.icon, contentDescription = item.iconContentDescription) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.id)
                        launchSingleTop = true
                    }
                })
        }

    }
}