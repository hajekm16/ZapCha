package mucacho.apps.zapcha

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

//fragment with info for partners
//TODO update fragment with relevant info
class PartnersFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partners, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PartnersFragment().apply {
            }
    }
}