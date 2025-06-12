package com.example.apnabazaar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.apnabazaar.GlobalNavigation
import com.example.apnabazaar.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CategoryView() {
 val categoryList = remember {
     mutableStateOf<List<CategoryModel>>(emptyList())
 }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stock").collection("categories")
            .get().addOnCompleteListener(){
               if(it.isSuccessful){
                   val resultList =it.result.documents.mapNotNull { doc->    // we are mapping it to categorymodel object as there are many documents in collection categories
                       doc.toObject(CategoryModel::class.java)
                   }
                categoryList.value=resultList
               }
            }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(categoryList.value) { item->
           CategoryItem(item)
        }
    }
}

@Composable
fun CategoryItem(category: CategoryModel){
    Card ( //we can use global navigation without passing parameter of navcontroller this is because now we create object of navcontroller in global navigation in appnavigation file which is assigned by navControoller
        modifier = Modifier.size(100.dp).clickable { GlobalNavigation.navController.navigate("categoryproducts/"+category.id) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImage(model = category.imageUrl, contentDescription = category.name , modifier = Modifier.size(50.dp).clip(shape = RoundedCornerShape(5.dp)))
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = category.name, textAlign = TextAlign.Center)
        }
    }
}