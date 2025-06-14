package com.example.apnabazaar.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apnabazaar.components.BannerView
import com.example.apnabazaar.components.CategoryView
import com.example.apnabazaar.components.HeaderView

@Composable
fun HomePage(modifier: Modifier) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        HeaderView(modifier)
        Spacer(modifier = Modifier.height(20.dp))
        BannerView(modifier= Modifier.height(200.dp))
        Text("Categories", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            )
        Spacer(modifier = Modifier.height(10.dp))
        CategoryView()
    }
}