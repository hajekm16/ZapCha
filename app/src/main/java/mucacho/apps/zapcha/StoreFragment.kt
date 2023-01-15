package mucacho.apps.zapcha

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import mucacho.apps.zapcha.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {
    private val ProductId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStoreBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_store, container, false)
        binding.changeButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(StoreFragmentDirections.actionStoreFragmentToProductFragment(ProductId))
        )
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
        return binding.root

    }

}