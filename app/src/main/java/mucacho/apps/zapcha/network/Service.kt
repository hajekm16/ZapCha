package mucacho.apps.zapcha.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import mucacho.apps.zapcha.domain.Product

class Service {

    val CHILD_PRODUCTS = "products"

    private lateinit var databaseReference: DatabaseReference

    fun initializeDbRef() {
        // [START initialize_database_ref]
        databaseReference = Firebase.database("https://zapcha-7156a-default-rtdb.europe-west1.firebasedatabase.app/").reference
        // [END initialize_database_ref]
    }

    fun writeProduct(id: Long, name: String, description: String, stock: Int, price: Long): Boolean{
        val zapchaFirebase = ZapchaFirebase(id,name,description,stock,price)
        databaseReference.child(CHILD_PRODUCTS).child(id.toString()).setValue(zapchaFirebase)
        return true
    }

    fun deleteProduct(id: Long): Boolean{
        databaseReference.child(CHILD_PRODUCTS).child(id.toString()).removeValue()
        return true
    }

    fun getProducts(products: MutableLiveData<List<Product>>){
        // Read from the database
//        val myUserId = uid
        val allProducts = databaseReference.child(CHILD_PRODUCTS)
        allProducts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val _products : List<ZapchaFirebase> = dataSnapshot.children.map { localDataSnapshot ->
                    localDataSnapshot.getValue<ZapchaFirebase>()!!
                }
                products.postValue(ZapchaFirebaseList(_products).asDomainModel().toList())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}