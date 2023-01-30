package mucacho.apps.zapcha.store

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapChaProduct

@BindingAdapter("productStockString")
fun TextView.setProductStockString(item: ZapChaProduct?){
    item?.let {
        text = "Skladem: " +item.productStock
    }
}

@BindingAdapter("productName")
fun TextView.setProductName(item: ZapChaProduct?){
    item?.let {
        text = item.productName
    }
}

@BindingAdapter("zapchaImage")
fun ImageView.setZapchaImage(item: ZapChaProduct?){
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