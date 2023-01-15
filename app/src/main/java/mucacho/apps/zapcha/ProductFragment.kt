package mucacho.apps.zapcha

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                val args = ProductFragmentArgs.fromBundle(requireArguments())
                return ShareCompat.IntentBuilder(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle("Jak sdílet: ")
                    .setText(getString(R.string.share_product_text, args.productId))
                    .createChooserIntent()
            }

            private fun shareSuccess() {
                startActivity(getShareIntent())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        // TODO: Use the ViewModel
        var args = ProductFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context,"ProductId: ${args.productId}",Toast.LENGTH_LONG).show()
    }

}