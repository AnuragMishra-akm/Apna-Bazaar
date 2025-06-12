package com.example.apnabazaar.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apnabazaar.components.ProductItem
import com.example.apnabazaar.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CategoryProductPage(categoryId: String){

    val productList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stock").collection("products")
            //now we getting all products of firestore regardless of their cotegory
            .whereEqualTo("category",categoryId)   // it will only get products which matches with category id , means when we click on fashion it will only show fashion products
            .get().addOnCompleteListener(){
                if(it.isSuccessful){
                    val resultList =it.result.documents.mapNotNull { doc->    // we are mapping it to categorymodel object as there are many documents in collection categories
                        doc.toObject(ProductModel::class.java)
                    }
                    productList.value=resultList.plus(resultList).plus(resultList).plus(resultList) // this is for testing purpose , it will add same products again and again, so that it will show realistic
                }
            }
    }
    LazyColumn (
       modifier= Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(productList.value.chunked(2)) { rowitem->
            Row{
                rowitem.forEach { item->
                    ProductItem(product = item, modifier = Modifier.weight(1f))
                }
                if (rowitem.size==1){
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

