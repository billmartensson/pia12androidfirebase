package se.magictechnology.pia12androidfirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Shopinfo(shopVM : ShopViewmodel, currentshop : Shopitem) {
    Column {
        Text("Shop info")
        Text(currentshop.name!!)
    }
}