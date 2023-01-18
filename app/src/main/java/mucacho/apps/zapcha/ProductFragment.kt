package mucacho.apps.zapcha

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import mucacho.apps.zapcha.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        binding.productViewModel = viewModel
        binding.setLifecycleOwner(this)
        var args = ProductFragmentArgs.fromBundle(requireArguments())
        if (viewModel.name == "") {
            viewModel.loadProduct(args.productId)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.product_menu, menu)
                if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
                    // hide the menu item if it doesn't resolve
                    menu.findItem(R.id.share)?.isVisible = false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.share -> {
                        shareSuccess()
                        true}
                    else -> false
                }
            }

            private fun getShareIntent() : Intent {
//                var args = ProductFragmentArgs.fromBundle(requireArguments())
                return ShareCompat.IntentBuilder(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle("Jak sdílet: ")
//                    .setText(getString(R.string.share_product_text, args.productId))
                    .setText(getString(R.string.share_product_text, viewModel.name))
                    .createChooserIntent()
            }

            private fun shareSuccess() {
                startActivity(getShareIntent())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.newStock.setOnClickListener{
            viewModel.newStockQty(binding.editTextQty.text.toString().toInt())}
        return binding.root
//        return inflater.inflate(R.layout.fragment_product, container, false)
    }
}