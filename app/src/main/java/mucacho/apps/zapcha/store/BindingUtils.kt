package mucacho.apps.zapcha.store

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.domain.Product

//util for data in listAdapter

@BindingAdapter("productStockString")
fun TextView.setProductStockString(item: Product?){
    item?.let {
        text = "Skladem: " +item.stock
    }
}

@BindingAdapter("productName")
fun TextView.setProductName(item: Product?){
    item?.let {
        text = item.name
    }
}

@BindingAdapter("productImage")
fun ImageView.setProductImage(item: Product?){
    item?.let {
        when (item.name) {
            "zazvor" -> {
                setImageResource(R.drawable.zazvor)
            }
            "ananas" -> {
                setImageResource(R.drawable.ananas)
            }
            else -> {
                setImageResource(R.drawable.zapcha)
            }
        }
    }
}