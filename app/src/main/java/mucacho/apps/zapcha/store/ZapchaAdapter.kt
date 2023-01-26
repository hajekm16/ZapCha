package mucacho.apps.zapcha.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mucacho.apps.zapcha.database.ZapChaProduct
import mucacho.apps.zapcha.databinding.ListProductZapchaBinding

class ZapchaAdapter(val clickListener:ZapchaProductListener) : ListAdapter<ZapChaProduct, ZapchaAdapter.ViewHolder>(ZapchaProductDiffCallback()) {

    class ViewHolder private constructor(val binding: ListProductZapchaBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ZapChaProduct, clickListener: ZapchaProductListener) {
            binding.zapcha = item
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
class ZapchaProductDiffCallback :DiffUtil.ItemCallback<ZapChaProduct>() {
    override fun areItemsTheSame(oldItem: ZapChaProduct, newItem: ZapChaProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: ZapChaProduct, newItem: ZapChaProduct): Boolean {
        return oldItem == newItem
    }
}

class ZapchaProductListener(val clickListener: (productId: Long) -> Unit){
    fun onClick(product: ZapChaProduct) = clickListener(product.productId)
}