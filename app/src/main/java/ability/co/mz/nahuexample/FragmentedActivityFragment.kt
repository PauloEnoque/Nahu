package ability.co.mz.nahuexample

import ability.co.mz.nahu.requestNahuPermissions
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A placeholder fragment containing a simple view.
 */
class FragmentedActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.requestNahuPermissions {

        }


        return inflater.inflate(R.layout.fragment_fragmented, container, false)
    }
}
