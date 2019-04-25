package uk.co.massimocarli.livedatabus.busstop.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.bus_stop_list_fragment.*
import uk.co.massimocarli.livedatabus.busstop.arch.MainViewModel
import uk.co.massimocarli.livedatabus.busstop.arch.MainViewModelFactory
import uk.co.massimocarli.livedatabus.databinding.BusStopListFragmentBinding
import uk.co.massimocarli.livedatabus.db.BusStop
import uk.co.massimocarli.livedatabus.util.OnSelectedItemListener

class BusStopListFragment : Fragment() {

  companion object {
    fun newInstance() = BusStopListFragment()
  }

  private lateinit var viewModel: MainViewModel
  private lateinit var adapter: BusStopAdapter
  private val model: MutableList<BusStop> = mutableListOf()
  private lateinit var binding: BusStopListFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    viewModel = activity?.run {
      val factory = MainViewModelFactory(this.application, this@BusStopListFragment)
      ViewModelProviders.of(this, factory)
        .get(MainViewModel::class.java)
    } ?: throw Exception("Invalid Activity")
    binding = BusStopListFragmentBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(activity)
    binding.model = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    adapter = BusStopAdapter(model, object : OnSelectedItemListener<BusStop> {
      override fun onSelected(item: BusStop, isLongClick: Boolean) {

      }
    })
    val layoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = layoutManager
    val dividerItemDecoration = DividerItemDecoration(
      context,
      layoutManager.getOrientation()
    )
    recyclerView.addItemDecoration(dividerItemDecoration)
    recyclerView.adapter = adapter
  }
}

