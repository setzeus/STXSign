import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.stxsign.R

sealed class Screen(val route:String, val title:String, val icon:Int) {
    object Stack: Screen("stack", title = "Stack", icon = R.drawable.keyiconss)
    object Sign: Screen("sign", title = "Sign", icon = R.drawable.gridiconss)
}

data class BottomNavigationItem(val route: String, val icon: Int, val iconContentDescription: String)

val bottomNavigationItems = listOf(
    BottomNavigationItem(Screen.Sign.title, R.drawable.keyiconss, Screen.Sign.title),
    BottomNavigationItem(Screen.Stack.title, R.drawable.gridiconss, Screen.Stack.title),
)

@Composable
fun STXSignBottomNavigation(navController: NavHostController, navBackStackEntry: NavBackStackEntry?, currentRoute: String?) {
    BottomNavigation(modifier = Modifier, backgroundColor = androidx.compose.material.MaterialTheme.colors.background, contentColor = colorResource( id = R.color.navClick_orange_100)) {
        //val currentRoute = navBackStackEntry?.destination?.route

        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                //unselectedContentColor = androidx.compose.material.MaterialTheme.colors.primary,
                icon = { Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = "Restfully Placeholder Coach Image",
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(180f),
                    contentScale = ContentScale.Fit
                ) },
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