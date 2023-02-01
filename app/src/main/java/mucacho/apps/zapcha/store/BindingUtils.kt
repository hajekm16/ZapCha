package mucacho.apps.zapcha.store

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct

//util for data in listAdapter

@BindingAdapter("productStockString")
fun TextView.setProductStockString(item: ZapChaDatabaseProduct?){
    item?.let {
        text = "Skladem: " +item.productStock
    }
}

@BindingAdapter("productName")
fun TextView.setProductName(item: ZapChaDatabaseProduct?){
    item?.let {
        text = item.productName
    }
}

@BindingAdapter("productImage")
fun ImageView.setProductImage(item: ZapChaDatabaseProduct?){
    item?.let {
        when (item.productName) {
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