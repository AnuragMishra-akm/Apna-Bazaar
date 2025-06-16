package com.example.apnabazaar

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apnabazaar.pages.CategoryProductPage
import com.example.apnabazaar.pages.CheckoutPage
import com.example.apnabazaar.pages.ProductDetailsPage
import com.example.apnabazaar.screen.AuthScreen
import com.example.apnabazaar.screen.HomeScreen
import com.example.apnabazaar.screen.LoginScreen
import com.example.apnabazaar.screen.SignUpScreen
import com.example.apnabazaar.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun AppNavigation(modifier: Modifier){
    val navController = rememberNavController()
    GlobalNavigation.navController = navController  // we use this to simplify navigation, as before it we have to inintalize everywhere navcontroller
    val authViewModel = AuthViewModel()
    val isLoggedIn = Firebase.auth.currentUser!=null // to check if user is already logged in or not , so that every time we will not see auth and login screen
    val firstPage = if(isLoggedIn) "home" else "auth"
    NavHost(navController = navController , startDestination = firstPage) {
        composable("auth") { AuthScreen(navController) }
        composable("login"){LoginScreen(navController,authViewModel)}
        composable("signup"){ SignUpScreen(navController,authViewModel = authViewModel) }
        composable("home"){ HomeScreen(navController) }
        composable("categoryproducts/{category-id}"){ //here we passed categoryid as parameter in navigation
            val categoryId = it.arguments?.getString("category-id")
            CategoryProductPage(categoryId = categoryId?:"") }
        composable("productsdetails/{product-id}"){ //here we passed productid as parameter in navigation
            val productId = it.arguments?.getString("product-id")
            ProductDetailsPage(productId = productId?:"")
        }
        composable("checkoutpage"){ CheckoutPage() }

    }
}

object GlobalNavigation{
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
}