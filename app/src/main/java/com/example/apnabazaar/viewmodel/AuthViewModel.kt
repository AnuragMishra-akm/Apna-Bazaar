package com.example.apnabazaar.viewmodel

import androidx.lifecycle.ViewModel
import com.example.apnabazaar.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore
    fun login(email:String, password:String , onResult : (Boolean , String)-> Unit){
auth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener {
            if(it.isSuccessful){
                onResult(true , "")
            }else{
                onResult(false , it.exception?.localizedMessage?: "Error In Login")
            }
        }
    }

    fun signup(email:String, password:String , name:String ,onResult : (Boolean , String)-> Unit){
     auth.createUserWithEmailAndPassword(email,password)
         .addOnCompleteListener {
             if(it.isSuccessful){
                 var userId = it.result?.user?.uid
               val userModel = UserModel(name, email , userId!!)
                 //now we add this data in firebase database
                 fireStore.collection("users").document(userId).set(userModel)
                     .addOnCompleteListener { dbTask->
                         if(dbTask.isSuccessful){
                             onResult(true , "")
                         }else{
                             onResult(false , dbTask.exception?.message ?: "Error In Signing")
                         }
                     }

             }else{
                 onResult(false , it.exception?.localizedMessage?: "Error In Signing")
             }
         }
    }
}