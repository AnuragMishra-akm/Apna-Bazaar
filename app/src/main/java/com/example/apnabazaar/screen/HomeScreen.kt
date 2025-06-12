package com.example.apnabazaar.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.apnabazaar.pages.CartPage
import com.example.apnabazaar.pages.FavouritePage
import com.example.apnabazaar.pages.HomePage
import com.example.apnabazaar.pages.ProfilePage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(navController: NavHostController) {
    val navItemList = listOf(
        BottomNavItem("Home", "home", Icons.Default.Home),
        BottomNavItem("Favourite", "favourite", Icons.Default.Favorite),
        BottomNavItem("Cart", "cart", Icons.Default.ShoppingCart),
        BottomNavItem("Profile", "profile", Icons.Default.Person)

    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {selectedIndex = index},
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.label
                            )
                        },
                        label = { Text(text = navItem.label) })
                }
            }
        }
    ) {
        ContentScreen(modifier = Modifier.padding(it),selectedIndex = selectedIndex)

    }
}
@Composable
fun ContentScreen(modifier: Modifier, selectedIndex: Int){
    when(selectedIndex){
        0 -> HomePage(modifier)
        1 -> FavouritePage(modifier)
        2 -> CartPage(modifier)
        3 -> ProfilePage(modifier)
    }

}

data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)

@Composable
fun LogOutButton(navController: NavHostController){
    Button(
        onClick = { Firebase.auth.signOut()
            navController.navigate("auth"){
                popUpTo("home"){inclusive = true}
            }
        }
    ) {
        Text("logout")
    }
}