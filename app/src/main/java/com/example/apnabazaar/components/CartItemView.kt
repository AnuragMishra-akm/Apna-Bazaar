package com.example.apnabazaar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.apnabazaar.AppUtil
import com.example.apnabazaar.GlobalNavigation
import com.example.apnabazaar.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CartItemView(modifier: Modifier = Modifier,productId:String,quantity: Long){
    val context = LocalContext.current
    var product by remember{
    mutableStateOf(ProductModel())
   }
    LaunchedEffect(key1 = Unit){
        Firebase.firestore.collection("data").document("stock").collection("products")
            .document(productId)
            .get().addOnCompleteListener {
                if(it.isSuccessful){
                    val result = it.result.toObject(ProductModel::class.java)
                    if(result!=null){
                        product=result
                    }
                }
            }
    }
    // desgingin product ui in cart

    Card(modifier.fillMaxWidth().padding(8.dp).clickable {
        GlobalNavigation.navController.navigate("productdetails/${product.id}")  //build logic for product detuls page from product id
    }, shape =RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),elevation = CardDefaults.cardElevation(8.dp) ) {
        Row (modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            AsyncImage(model = product.images.firstOrNull(), contentDescription = product.title,modifier= Modifier.height(150.dp).width(100.dp))
            Column( modifier = Modifier.fillMaxWidth().padding(8.dp).weight(1f),
                verticalArrangement = Arrangement.Center
            ){
            Text(text = product.title, fontWeight = FontWeight.Bold, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp) )
              Row (verticalAlignment = Alignment.CenterVertically){
                  Text(
                    text = "₹" + quantity*product.actualPrice.toInt(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                  )
                  Text(
                      text = "₹" + product.price,
                      fontSize = 14.sp,
                      maxLines = 1,
                      overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                      style = TextStyle(textDecoration = TextDecoration.LineThrough)
                  )

              }
              Row(verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { AppUtil.addItemCart(product.id, context = context) }) {
                    Icon(imageVector = Icons.Default.Add , contentDescription = "Increment in quantity")
                }
                Text(text = quantity.toString(), fontSize = 16.sp)
                IconButton(onClick = { AppUtil.removeItemCart(product.id, context = context) }) {
                    Icon(painter = painterResource(id = com.example.apnabazaar.R.drawable.outline_remove_24) ,modifier= Modifier.size(16.dp), contentDescription = "Decrement in quantity")
                }

              }
            }
            IconButton(onClick = {AppUtil.removeItemCart(productId,quantity,context = context,removeAll = true)}) {
                Icon(imageVector = Icons.Default.Delete , contentDescription = "remove item")
            }
        }
    }
}