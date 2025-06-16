package com.example.apnabazaar.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apnabazaar.GlobalNavigation
import com.example.apnabazaar.components.CartItemView
import com.example.apnabazaar.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CartPage(modifier: Modifier) {
    // getting data (usermodel (email,name etc,)) getting cart details from firebase which are in collection(users)-> document(uid)-> field(email,name, uid,cartitem)-> cartItem
  val userModel = remember {
      mutableStateOf(UserModel())
  }
    DisposableEffect(key1 = Unit) {
       val listener = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener {it , _ ->
                if(it!=null){
                    val result = it.toObject(UserModel::class.java)
                    if(result!=null){
                        userModel.value = result
                    }
                }

            }
        onDispose {
            listener.remove()
        }
    }   //this above code has been chNGED to addsnapshotListener so that it dynamically change the quantity , and must not triggered every time thats why disposedEffect is used
    //designing ui for cart page
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Your Cart", style = TextStyle(fontSize = 24.sp , fontWeight = FontWeight.Bold))
        if(userModel.value.cartItem.isNotEmpty()){  //create logic if our cart is empty , so make sure button is not visible
        LazyColumn (modifier.weight(1f)){
            items(userModel.value.cartItem.toList(),key={it.first}){(productId,quantity)->
                CartItemView(modifier,productId,quantity)
            } // here key is passed , because when i delete any item, the other one will be gone ,and when we come back it is corrected
            // now it is fixed
        }
        Button(
            onClick = { GlobalNavigation.navController.navigate("checkoutpage")},
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Add To Cart", fontSize = 16.sp)
        }
    }else{
        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Your cart is so light", fontSize = 20.sp,fontFamily = FontFamily.Cursive ,fontWeight = FontWeight.SemiBold)
            Text(text = "Add some stuff in it and made yourself happy", fontSize = 20.sp, fontFamily = FontFamily.Cursive, fontWeight = FontWeight.SemiBold)
        }
    }
    }
}