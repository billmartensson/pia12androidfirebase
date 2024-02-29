package se.magictechnology.pia12androidfirebase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import se.magictechnology.pia12androidfirebase.ui.theme.Pia12androidfirebaseTheme

// Skriva kod. Mera kod.
// Annan person

// Dev dev testa kod. Nu funkar det bra

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pia12androidfirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Startscreen()
                }
            }
        }
    }

}

@Composable
fun Startscreen(shopVM : ShopViewmodel = viewModel()) {

    val loggedin by shopVM.loggedin.collectAsState()

    LaunchedEffect(true) {
        shopVM.checklogin()
    }

    if(loggedin) {
        AppNavHost(shopVM = shopVM)
    } else {
        Login(shopVM = shopVM) {

        }
    }
}


enum class NavScreen() {
    Login,
    Shoplist,
    Shopinfo
}

@Composable
fun AppNavHost(
    navController : NavHostController = rememberNavController(),
    shopVM : ShopViewmodel = viewModel()
) {
    NavHost(navController = navController, startDestination = NavScreen.Shoplist.name) {
        composable(NavScreen.Login.name) {
            Login(
                shopVM,
                goShop = {
                    navController.navigate(NavScreen.Shoplist.name)
                }
            )
        }
        composable(NavScreen.Shoplist.name) {
            Shoppinglist(shopVM, goInfo = {
                navController.navigate(NavScreen.Shopinfo.name + "/${it.fbid!!}")
            })
        }
        composable(NavScreen.Shopinfo.name + "/{shopid}") {
            val shopid = it.arguments?.getString("shopid")
            Shopinfo(shopVM, shopVM.getShopitemForId(shopid!!))
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pia12androidfirebaseTheme {
        AppNavHost()
    }
}


data class Fruit(
    val fruitname : String? = null,
    val fruitcolor : String? = null,
    val score : Int? = null
)


/*
        val database = Firebase.database
        val myRef = database.getReference("androidthings")
        //myRef.push().setValue("Tjena")

        val examplefruit = Fruit("Äpple", "Grön", 8)
        val fruitRef = database.getReference("androidfruitbowl")
        //fruitRef.push().setValue(examplefruit)

        fruitRef.get().addOnSuccessListener {
            //val getfruit = it.getValue<Fruit>()
            //Log.i("PIA12DEBUG", getfruit!!.fruitname!!)
            for(fruitsnap in it.children) {
                val thefruit = fruitsnap.getValue<Fruit>()
                Log.i("PIA12DEBUG", thefruit!!.fruitname!!)
            }

        }.addOnFailureListener {

        }
        */

/*
var auth: FirebaseAuth
auth = Firebase.auth

if(auth.currentUser == null) {
    Log.i("PIA12DEBUG", "INTE INLOGGAD")
} else {
    Log.i("PIA12DEBUG", "ÄR INLOGGAD")
    Log.i("PIA12DEBUG", auth.currentUser!!.uid)
}
*/

/*
auth.createUserWithEmailAndPassword("android@magictechnology.se", "hemligt").addOnCompleteListener { task ->
    if(task.isSuccessful) {
        Log.i("PIA12DEBUG", "REGISTRERING OK")
    } else {
        Log.i("PIA12DEBUG", "REGISTRERING FAIL")
    }
}
*/

//auth.signOut()

/*
auth.signInWithEmailAndPassword("android@magictechnology.se", "hemligt").addOnCompleteListener { task ->
    if(task.isSuccessful) {
        Log.i("PIA12DEBUG", "LOGIN OK")
    } else {
        Log.i("PIA12DEBUG", "LOGIN FAIL")
    }
}
 */