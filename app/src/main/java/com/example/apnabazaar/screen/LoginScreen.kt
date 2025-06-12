package com.example.apnabazaar.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apnabazaar.AppUtil
import com.example.apnabazaar.viewmodel.AuthViewModel


@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var isLoading by remember { mutableStateOf(false) }

    val localContext = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login To Continue", modifier = Modifier.fillMaxWidth(), style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,

                )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Welcome Back", modifier = Modifier.fillMaxWidth(), style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
            )
        )

        Image(
            painter = painterResource(com.example.apnabazaar.R.drawable.logoicon),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                isLoading = true
                authViewModel.login(email, password) { success, errormessage ->
                    if (success) {
                        isLoading = false
                        navController.navigate("home") {
                            popUpTo("auth") {
                                inclusive = true
                            }
// this will clear the backstack of auth screen , so on reaching home screen ,if we press back button , it will not go to login or auth screen
                            //it directly close the app

                        }
                    } else {
                        isLoading = false
                        AppUtil.showToast(localContext, errormessage)
                    }
                }
            },
            enabled = !isLoading, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = if (isLoading) "Logging In..." else "Log In",
                style = TextStyle(fontSize = 22.sp)
            )
        }
    }

}