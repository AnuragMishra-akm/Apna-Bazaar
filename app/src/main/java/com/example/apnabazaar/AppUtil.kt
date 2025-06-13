package com.example.apnabazaar

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

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
}