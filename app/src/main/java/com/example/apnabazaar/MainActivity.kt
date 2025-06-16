package com.example.apnabazaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apnabazaar.ui.theme.ApnaBazaarTheme
import com.razorpay.PaymentResultListener

class MainActivity : ComponentActivity(), PaymentResultListener { //here we are inheriting the PaymentResultListener Interface provided by
    override fun onCreate(savedInstanceState: Bundle?) { //razorpay , to get methods for payment succes and failure
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApnaBazaarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        AppUtil.clearCartAndAddToOrders()
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Payment Successful")
            .setMessage("Your order has been placed successfully")
            .setPositiveButton("OK") { dialog, which ->
                val navController = GlobalNavigation.navController
                navController.navigate("home")
                navController.popBackStack()
            }
            .setCancelable(false)
            .show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        AppUtil.showToast(this,"Payment Failed")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApnaBazaarTheme {
        Greeting("Android")
    }
}