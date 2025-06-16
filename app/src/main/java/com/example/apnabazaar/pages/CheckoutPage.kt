package com.example.apnabazaar.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apnabazaar.AppUtil
import com.example.apnabazaar.GlobalNavigation
import com.example.apnabazaar.model.ProductModel
import com.example.apnabazaar.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun CheckoutPage(){
    val userModel = remember {
        mutableStateOf(UserModel())
    }
    val productList = remember {
        mutableStateListOf(ProductModel())
    }
    val subTotal = remember {
        mutableStateOf(0f)
    }
    val discount = remember {
        mutableStateOf(0f)
    }
    val tax = remember {
        mutableStateOf(0f)
    }
    val total = remember {
        mutableStateOf(0f)
    }
    fun calculateAndAssign(){
        productList.forEach {
            if(it.actualPrice.isNotEmpty()){
                val qty = userModel.value.cartItem[it.id]?:0
                subTotal.value += it.actualPrice.toFloat()*qty
            }
        }
        discount.value = subTotal.value*(AppUtil.getDiscountPercentage())/100
        tax.value = subTotal.value*(AppUtil.getTaxPercentage())/100
        total.value = "%.2f".format(subTotal.value - discount.value + tax.value).toFloat()
    }
    LaunchedEffect(key1=Unit) {
        Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful){
                    val result = it.result.toObject(UserModel::class.java)
                    if(result!=null){
                        userModel.value=result
// we have provided below all product id list to wherein query to get all products, this will return the list of products which are in cart
                        Firebase.firestore.collection("data").document("stock").collection("products")
                            .whereIn("id",userModel.value.cartItem.keys.toList())
                            .get().addOnCompleteListener {task->
                                if(task.isSuccessful){
                                    val resultProducts = task.result.toObjects(ProductModel::class.java)
                                    productList.addAll(resultProducts)
                                }
                            }
                    }
                }
            }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("CheckOut",fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Delivery Address", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text(text = userModel.value.name, fontSize = 16.sp)
        Text(text = userModel.value.address, fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(16.dp))
        RowCheckoutItems("Subtotal",subTotal.value.toString())
        Spacer(modifier = Modifier.padding(8.dp))
        RowCheckoutItems("Discount (-)",discount.value.toString())
        Spacer(modifier = Modifier.padding(8.dp))
        RowCheckoutItems("Tax (+)",tax.value.toString())
        Spacer(modifier = Modifier.padding(8.dp))
        RowCheckoutItems("Total",total.value.toString())
        Spacer(modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(16.dp))
        Text("To Pay", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text("₹"+total.value.toString(), modifier = Modifier.fillMaxWidth(), fontSize = 30.sp, fontWeight = FontWeight.Bold ,textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { AppUtil.startPayment(total.value)},
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Pay Now", fontSize = 16.sp)
        }
    }
}

@Composable
fun RowCheckoutItems(title:String,value:String){
    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 20.sp , fontWeight = FontWeight.SemiBold)
        Text("₹${value}", fontSize = 18.sp)
    }
}