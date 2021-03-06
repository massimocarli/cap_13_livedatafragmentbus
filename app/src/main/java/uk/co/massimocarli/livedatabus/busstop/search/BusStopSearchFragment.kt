package uk.co.massimocarli.livedatabus.busstop.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import uk.co.massimocarli.livedatabus.LocationData
import uk.co.massimocarli.livedatabus.R
import uk.co.massimocarli.livedatabus.location.LocationViewModel

class BusStopSearchFragment : Fragment() {

    companion object {
        fun newInstance() = BusStopSearchFragment()
    }

    private lateinit var viewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bus_stop_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application))
                .get(LocationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.getLocationLiveData().observe(this, Observer {
            if (it is LocationData) {
                //locationOutput.setText("Location: ${it.location}")
            }
        })
    }
}
