package com.example.apnabazaar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.apnabazaar.AppUtil
import com.example.apnabazaar.GlobalNavigation
import com.example.apnabazaar.model.ProductModel

@Composable
fun ProductItem(product: ProductModel,modifier: Modifier){
    val context = LocalContext.current
    Card(modifier.padding(8.dp).clickable {
        GlobalNavigation.navController.navigate("productdetails/${product.id}")  //build logic for product detuls page from product id
    }, shape =RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),elevation = CardDefaults.cardElevation(8.dp) ) {
        Column(modifier = Modifier.padding(8.dp)){
            AsyncImage(model = product.images.firstOrNull(), contentDescription = product.title,modifier= Modifier.height(150.dp).fillMaxWidth())
            Text(text = product.title, fontWeight = FontWeight.Bold, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp) )
            Row ( modifier = Modifier.fillMaxWidth().padding(8.dp),
                  verticalAlignment = Alignment.CenterVertically
                ){
                Text(text = "₹"+product.price, fontSize = 14.sp ,maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis, style = TextStyle(textDecoration = TextDecoration.LineThrough))
                Text(text = "₹"+product.actualPrice, fontSize = 16.sp,fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
               Spacer(modifier = Modifier.weight(1f)) // by this cart icon will go to extreme right
                IconButton(onClick = { AppUtil.addItemCart(product.id, context = context) }) {
                    Icon(imageVector = Icons.Default.ShoppingCart , contentDescription = "add to cart")
                }
            }
        }
    }

}