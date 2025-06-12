package com.example.apnabazaar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    val authViewModel = AuthViewModel()
    val isLoggedIn = Firebase.auth.currentUser!=null // to check if user is already logged in or not , so that every time we will not see auth and login screen
    val firstPage = if(isLoggedIn) "home" else "auth"
    NavHost(navController = navController , startDestination = "auth") {
        composable("auth") { AuthScreen(navController) }
        composable("login"){LoginScreen(navController,authViewModel)}
        composable("signup"){ SignUpScreen(navController,authViewModel = authViewModel) }
        composable("home"){ HomeScreen() }
    }
}