package se.magictechnology.pia12androidfirebase

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Shoppinglist(shopVM : ShopViewmodel, goInfo : (goshopitem : Shopitem) -> Unit) {

    val shoppinglist by shopVM.shoppinglist.collectAsState()

    var addName by remember { mutableStateOf("") }
    var addAmount by remember { mutableStateOf("") }

    var errormessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        Log.i("PIA12DEBUG", "LAUNCHED EFFECT")
        shopVM.loadshopping()
    }

    Column {
        Text("Shop list")

        if(errormessage != null) {
            Text(errormessage!!)
        }
        Row {
            TextField(value = addName, onValueChange = { addName = it }, modifier = Modifier.width(100.dp))
            TextField(value = addAmount, onValueChange = { addAmount = it }, modifier = Modifier.width(100.dp))


            Button(onClick = {
                shopVM.addShop(addName, addAmount) {
                    if(it == null) {
                        addName = ""
                        addAmount = ""
                    }
                    errormessage = it
                }
            }) {
                Text("Add")
            }
        }

        if(shoppinglist != null) {
            LazyColumn {
                items(shoppinglist!!) {
                    Row(modifier = Modifier.clickable {
                        goInfo(it)
                    }) {
                        Text(it.name!!)
                        Text(it.amount!!.toString())

                        if(it.done!!) {
                            Text("X", modifier = Modifier.clickable {
                                shopVM.switchDone(it)
                            })
                        } else {
                            Text("O", modifier = Modifier.clickable {
                                shopVM.switchDone(it)
                            })
                        }

                        Text("DELETE", modifier = Modifier.clickable {
                            shopVM.deleteitem(it)
                        })

                    }
                }
            }
        } else {
            Text("Loading...")
        }

        Button(onClick = {
            shopVM.dologout()
        }) {
            Text("Logout")
        }
    }
}

@Preview
@Composable
fun ShoppingListPreview() {
    Shoppinglist(shopVM = ShopViewmodel()) {
        
    }
}