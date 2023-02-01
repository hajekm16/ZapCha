package mucacho.apps.zapcha.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.databinding.ListProductZapchaBinding

//listAdapter for products

class ProductAdapter(private val clickListener:ZapchaProductListener) : ListAdapter<ZapChaDatabaseProduct, ProductAdapter.ViewHolder>(ZapchaProductDiffCallback()) {

    class ViewHolder private constructor(private val binding: ListProductZapchaBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ZapChaDatabaseProduct, clickListener: ZapchaProductListener) {
            binding.product = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) :ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListProductZapchaBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

/*porovnani polozek a vyhodnoceni nejnizsi pocet nutnych zmen*/
class ZapchaProductDiffCallback :DiffUtil.ItemCallback<ZapChaDatabaseProduct>() {
    override fun areItemsTheSame(oldItem: ZapChaDatabaseProduct, newItem: ZapChaDatabaseProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: ZapChaDatabaseProduct, newItem: ZapChaDatabaseProduct): Boolean {
        return oldItem == newItem
    }
}

class ZapchaProductListener(val clickListener: (productId: Long) -> Unit){
    fun onClick(product: ZapChaDatabaseProduct) = clickListener(product.productId)
}