package mucacho.apps.zapcha.product

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import androidx.core.content.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false)
//        reference to the application
        val application = requireNotNull(this.activity).application

        val arguments = ProductFragmentArgs.fromBundle(requireArguments())
//        reference to the datasource
        val dataSource = ZapchaDatabase.getInstance(application).zapchaDatabaseDao

        val viewModelFactory = ProductViewModelFactory(arguments.productId,dataSource,application)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        binding.productViewModel = viewModel
        viewModel.navigateToStore.observe(viewLifecycleOwner, Observer {
            if (it==true){
                this.findNavController().navigate(ProductFragmentDirections.actionProductFragmentToStoreFragment())
                viewModel.doneNavigating()
            }
        })
        viewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.sell_product_text),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackbar()
            }
        })
        binding.lifecycleOwner = this

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
                    R.id.Delete -> {
                        viewModel.onDeleteProduct()
                        buzz()
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

        binding.editTextName.doAfterTextChanged {
            viewModel.newProductName(binding.editTextName.text.toString())
        }
        binding.editTextDescr.doAfterTextChanged {
            viewModel.newProductDescr(binding.editTextDescr.text.toString())
        }
        binding.editTextStock.doAfterTextChanged {
            viewModel.newStockQty(binding.editTextStock.text.toString())
        }
        binding.editTextPrice.doAfterTextChanged {
            viewModel.newPrice(binding.editTextPrice.text.toString())
        }
        return binding.root
    }

    private fun buzz() {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                buzzer.vibrate(200)
            }
        }
    }
}