package mucacho.apps.zapcha.store

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.databinding.FragmentStoreBinding
import mucacho.apps.zapcha.product.ProductViewModel

class StoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStoreBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_store, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ZapchaDatabase.getInstance(application).zapchaDatabaseDao

        val viewModelFactory = StoreViewModelFactory(dataSource,application)

        val storeViewModel = ViewModelProvider(this, viewModelFactory).get(
            StoreViewModel::class.java)

        binding.storeViewModel = storeViewModel

        binding.lifecycleOwner = this

        storeViewModel.navigateToProduct.observe(viewLifecycleOwner, Observer {
            product ->
            product?.let {
                this.findNavController().navigate(
                    StoreFragmentDirections.actionStoreFragmentToProductFragment(product.productId)
                )
                storeViewModel.doneNavigating()
            }
        })

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.partnersFragment -> {
                        return NavigationUI.onNavDestinationSelected(menuItem!!,view!!.findNavController())
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val adapter = ZapchaAdapter(ZapchaProductListener {
            productId -> storeViewModel.onZapchaProductClicked(productId)
        })
        binding.productList.adapter = adapter

        storeViewModel.navigateToProductDetail.observe(viewLifecycleOwner, Observer {
            product ->
            product?.let {
                this.findNavController().navigate(StoreFragmentDirections.actionStoreFragmentToProductFragment(product))
                storeViewModel.onZapchaProductNavigated()
            }
        })

        storeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root

    }

}