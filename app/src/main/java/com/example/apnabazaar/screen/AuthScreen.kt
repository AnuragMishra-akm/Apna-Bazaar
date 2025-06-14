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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AuthScreen(navController: NavHostController){
    Column(modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = com.example.apnabazaar.R.drawable.logoicon), contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth().height(300.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Start Your Shopping Journey Now",
            style = TextStyle(fontSize = 30.sp, fontFamily = FontFamily.Cursive, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Sab Kuch Apna!!",
            style = TextStyle(fontSize = 30.sp, fontFamily = FontFamily.Cursive, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {navController.navigate("login")}, modifier = Modifier.fillMaxWidth().height(60.dp)) {
            Text(text = "Login", style = TextStyle(fontSize = 22.sp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(onClick = {navController.navigate("signup")}, modifier = Modifier.fillMaxWidth().height(60.dp)) {
            Text(text = "Sign Up", style = TextStyle(fontSize = 22.sp))
        }
    }
}