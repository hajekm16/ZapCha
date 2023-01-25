package mucacho.apps.zapcha.store

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapChaProduct

class ZapchaAdapter : RecyclerView.Adapter<ZapchaAdapter.TextItemViewHolder>() {

    class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView){

    }

    var data = listOf<ZapChaProduct>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.productName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_product_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }
}