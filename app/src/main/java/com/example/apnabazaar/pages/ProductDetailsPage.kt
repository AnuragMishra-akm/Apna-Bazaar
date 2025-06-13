package com.example.apnabazaar.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.apnabazaar.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailsPage(productId: String){
    var product by remember{
        mutableStateOf(ProductModel())
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stock").collection("products")
            //now we getting all products of firestore regardless of their cotegory
            .document(productId)  // it will only get products which matches with category id , means when we click on fashion it will only show fashion products
            .get().addOnCompleteListener(){
                if(it.isSuccessful){
                   val result = it.result.toObject(ProductModel::class.java)
                    if(result!=null){
                        product=result
                    }
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Text(
            text = product.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier) {
            val pagerState = rememberPagerState(0) {
                product.images.size
            }
            HorizontalPager(state = pagerState, pageSpacing = 24.dp) {
                AsyncImage(
                    model = product.images.get(it),
                    contentDescription = "product image",
                    modifier = Modifier.height(220.dp).fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
            }
            DotsIndicator(
                dotCount = product.images.size,
                type = ShiftIndicatorType(
                    dotsGraphic = DotGraphic(
                        color = MaterialTheme.colorScheme.primary,
                        size = 6.dp
                    )
                ),
                pagerState = pagerState
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "₹" + product.price,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                style = TextStyle(textDecoration = TextDecoration.LineThrough)
            )
            Text(
                text = "₹" + product.actualPrice,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f)) // by this cart icon will go to extreme right
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "add to whishlist"
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Add To Cart", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = product.description, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(16.dp))
        if (product.otherDetails.isNotEmpty()) {
            Text(text = "Other Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            product.otherDetails.forEach { (key, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text(key + " : ", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Text(value, fontSize = 16.sp)
                }
            }
        }
    }
}
