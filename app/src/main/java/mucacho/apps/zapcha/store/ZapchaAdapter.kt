package mucacho.apps.zapcha.store

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapChaProduct

class ZapchaAdapter : RecyclerView.Adapter<ZapchaAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productStock: TextView = itemView.findViewById(R.id.product_stock)
        val productImage: ImageView = itemView.findViewById(R.id.product_image)

        fun bind(item : ZapChaProduct) {
            productName.text = item.productName
            productStock.text = item.productStock.toString()
            productImage.setImageResource(R.drawable.zapcha)
        }

        companion object {
            fun from(parent: ViewGroup) :ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_product_zapcha, parent, false)

                return ViewHolder(view)
            }
        }
    }

    var data = listOf<ZapChaProduct>()
        set(value) {
            field = value
//            prekresli veskere polozky
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}