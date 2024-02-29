package se.magictechnology.pia12androidfirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Login(shopVM : ShopViewmodel, goShop : () -> Unit) {

    var loginemail by remember { mutableStateOf("") }
    var loginpassword by remember { mutableStateOf("") }

    Column {
        Text("Login")

        TextField(value = loginemail, onValueChange = { loginemail = it })
        TextField(value = loginpassword, onValueChange = { loginpassword = it })

        Button(onClick = {
            shopVM.dologin(loginemail, loginpassword)
        }) {
            Text("Login")
        }
        Button(onClick = {
            shopVM.doregister(loginemail, loginpassword)
        }) {
            Text("Register")
        }
    }
}