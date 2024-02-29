package se.magictechnology.pia12androidfirebase

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShopViewmodel : ViewModel() {

    val database = Firebase.database

    private val _shoppinglist = MutableStateFlow<List<Shopitem>?>(null)
    val shoppinglist : StateFlow<List<Shopitem>?> get() = _shoppinglist

    private val _loggedin = MutableStateFlow<Boolean>(false)
    val loggedin : StateFlow<Boolean> get() = _loggedin

    fun checklogin() {
        if(Firebase.auth.currentUser == null) {
            _loggedin.value = false
        } else {
            _loggedin.value = true
        }
    }

    fun dologin(email : String, password : String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful == false) {
                // VISA FEL
            }
            checklogin()
        }
    }
    fun doregister(email : String, password : String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            checklogin()
        }
    }

    fun dologout() {
        Firebase.auth.signOut()
        checklogin()
    }

    fun addShop(addname : String, addamount : String, addresult : (String?) -> Unit) {

        if(addname == "") {
            addresult("Name is empty")
            return
        }

        if(addamount.toIntOrNull() == null) {
            addresult("Amount not a number")
            return
        }

        val newshop = Shopitem(null, addname, addamount.toInt(), done = false)

        database.getReference("androidshop").child(Firebase.auth.currentUser!!.uid).push()
            .setValue(newshop)
            .addOnSuccessListener {
                addresult(null)
                loadshopping()
            }
    }

    fun loadshopping() {
        database.getReference("androidshop").child(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener { snap ->
                var tempshoplist = mutableListOf<Shopitem>()
                for(childsnap in snap.children) {
                    var tempshop = childsnap.getValue<Shopitem>()
                    tempshop!!.fbid = childsnap.key
                    Log.i("PIA12DEBUG", tempshop!!.name!!)
                    tempshoplist.add(tempshop)
                }

                _shoppinglist.value = tempshoplist
            }
            .addOnFailureListener {  }
    }

    fun switchDone(switchitem : Shopitem) {
        database.getReference("androidshop")
            .child(Firebase.auth.currentUser!!.uid)
            .child(switchitem.fbid!!)
            .child("done")
            .setValue(!switchitem.done!!)
            .addOnSuccessListener {
                loadshopping()
            }
    }

    fun deleteitem(delitem : Shopitem) {
        database.getReference("androidshop")
            .child(Firebase.auth.currentUser!!.uid)
            .child(delitem.fbid!!)
            .removeValue().addOnSuccessListener {
                loadshopping()
            }
    }

    fun getShopitemForId(shopid : String) : Shopitem {
        val findshop = _shoppinglist.value!!.filter { it.fbid == shopid }

        return findshop.first()
    }

}