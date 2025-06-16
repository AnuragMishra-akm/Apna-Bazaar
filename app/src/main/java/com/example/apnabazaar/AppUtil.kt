package com.example.apnabazaar

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.apnabazaar.model.OrderModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import org.json.JSONObject
import java.util.UUID

object AppUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    fun addItemCart(productId: String, quantity: Long =1 ,context: Context){
        val userDocRef = Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
        // we are using userDocRef of type document reference , so we can perform operations on it and change it
        //if we want to just get and display data , we would have convert it into userModel
        userDocRef.get().addOnCompleteListener(){
            if(it.isSuccessful){
                val currentCart = it.result.get("cartItem") as? Map<String,Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity + quantity
                val updatedCart = mapOf("cartItem.$productId" to updatedQuantity) // it will store in map , productId is key, and updatedQuantity is value
                userDocRef.update(updatedCart).addOnCompleteListener(){
                    if(it.isSuccessful){
                        showToast(context,"Item added to cart")
                    }
                    else{
                        showToast(context,"Failed to add item to cart")
                    }
                }

            }
        }
    }
    fun removeItemCart(productId: String, quantity: Long =1 ,context: Context,removeAll:Boolean=false){
        val userDocRef = Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
        // we are using userDocRef of type document reference , so we can perform operations on it and change it
        //if we want to just get and display data , we would have convert it into userModel
        userDocRef.get().addOnCompleteListener(){
            if(it.isSuccessful){
                val currentCart = it.result.get("cartItem") as? Map<String,Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity - quantity
                val updatedCart = if(updatedQuantity<=0 || removeAll) {mapOf("cartItem.$productId" to FieldValue.delete())} // it will store in map , productId is key, and updatedQuantity is value")
                                  else{mapOf("cartItem.$productId" to updatedQuantity)} // it will store in map , productId is key, and updatedQuantity is value
                userDocRef.update(updatedCart).addOnCompleteListener(){
                    if(it.isSuccessful){
                        showToast(context,"Item removed from cart")
                    }
                    else{
                        showToast(context,"Failed to remove item to cart")
                    }
                }

            }
        }
    }

    fun clearCartAndAddToOrders(){
        val userDocRef = Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
        // we are using userDocRef of type document reference , so we can perform operations on it and change it
        userDocRef.get().addOnCompleteListener(){
            if(it.isSuccessful){
                val currentCart = it.result.get("cartItem") as? Map<String,Long> ?: emptyMap() //current cart of current user
                val order = OrderModel(
                    id = "ORD_"+UUID.randomUUID().toString().replace("-","").take(10).uppercase(),
                    userId = FirebaseAuth.getInstance().currentUser?.uid!!,
                    date = Timestamp.now(),
                    items = currentCart,
                    status = "ORDERED",
                    address = it.result.get("address") as String
                ) //now order objet is ready , now we will push it to the firestore orders collection alongside, users,data collection
                Firebase.firestore.collection("orders") //it will create orders collection for first time if there not exists orders collection in firestore
                    .document(order.id).set(order) //document will be order id  and object will be order
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            userDocRef.update("cartItems", FieldValue.delete())  //it will delete the cart items from the user object
                        } //we will imolement on the payment success method in main activity
                    }

            }
        }
    }

    fun getDiscountPercentage(): Float{
        return 10.0f
    }
    fun getTaxPercentage(): Float{
        return 13.0f
    }
    fun razorPayApiKey():String{
       return "rzp_test_S2kAPDZ7PVMOMb"
    }
    fun startPayment(amount:Float){
        val checkout = Checkout()
        checkout.setKeyID(razorPayApiKey())

        val options = JSONObject()   //now we ahve to pass some details to razorpay as jsom object
        options.put("name","Apna Bazaar")
        options.put("description","Your order")
        options.put("theme.color","#3399cc")
        options.put("currency","INR")
        options.put("amount",amount*100)

        checkout.open(GlobalNavigation.navController.context as Activity,options)
    }
}