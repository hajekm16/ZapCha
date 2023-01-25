package mucacho.apps.zapcha.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapChaProduct
import mucacho.apps.zapcha.databinding.ListProductZapchaBinding

class ZapchaAdapter : ListAdapter<ZapChaProduct, ZapchaAdapter.ViewHolder>(ZapchaProductDiffCallback()) {

    class ViewHolder private constructor(val binding: ListProductZapchaBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ZapChaProduct) {
            binding.zapcha = item
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
        val item = getItem(position)

        holder.bind(item)
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