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

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productStock: TextView = itemView.findViewById(R.id.product_stock)
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
    }

    var data = listOf<ZapChaProduct>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.productName.text = item.productName
        holder.productStock.text = item.productStock.toString()
        holder.productImage.setImageResource(R.drawable.zapcha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_product_zapcha, parent, false)

        return ViewHolder(view)
    }
}