package mucacho.apps.zapcha.product

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.*
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import androidx.core.content.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.databinding.FragmentProductBinding
import mucacho.apps.zapcha.product.ui.theme.ZapChaTheme

//fragment to display and edit single product

class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false)
//        reference to the application
        val application = requireNotNull(this.activity).application

        val arguments = ProductFragmentArgs.fromBundle(requireArguments())
//        reference to the datasource
        val dataSource = ZapchaDatabase.getInstance(application)

        val viewModelFactory = ProductViewModelFactory(arguments.productId,dataSource,application)

        val productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        binding.productViewModel = productViewModel

        productViewModel.navigateToStore.observe(viewLifecycleOwner, Observer {
            if (it==true){
                this.findNavController().navigate(ProductFragmentDirections.actionProductFragmentToStoreFragment())
                productViewModel.doneNavigating()
            }
        })

        productViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.sell_product_text),
                    Snackbar.LENGTH_SHORT
                ).show()
                productViewModel.doneShowingSnackbar()
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
                        productViewModel.onDeleteProduct()
                        buzz()
                        true}
                    else -> false
                }
            }

            private fun getShareIntent() : Intent {
                return ShareCompat.IntentBuilder(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle("Jak sdílet: ")
                    .setText(getString(R.string.share_product_text,
                        productViewModel.selectedProduct.value?.name ?: ""
                    ))
                    .createChooserIntent()
            }

            private fun shareSuccess() {
                startActivity(getShareIntent())
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.composeView.apply{

            setContent {
                ZapChaTheme {
                    ProductScreen(productViewModel)
                }
            }
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