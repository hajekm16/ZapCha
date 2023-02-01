package mucacho.apps.zapcha.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mucacho.apps.zapcha.R
//fragment for showing latest news
//TODO implement viewmodel for news and update fragment, maybe even newsDetail
class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
            }
    }
}