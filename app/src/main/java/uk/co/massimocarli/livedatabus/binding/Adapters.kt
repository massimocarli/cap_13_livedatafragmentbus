package uk.co.massimocarli.livedatabus.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.livedatabus.busstop.arch.MainViewModelResponse
import uk.co.massimocarli.livedatabus.busstop.arch.RepositoryResponse
import uk.co.massimocarli.livedatabus.busstop.list.BusStopAdapter
import uk.co.massimocarli.livedatabus.busstop.list.BusStopDiff
import uk.co.massimocarli.livedatabus.db.BusStop
import uk.co.massimocarli.livedatabus.db.FindBusStopByLocationResult

@BindingAdapter("android:model")
fun RecyclerView.setBusStopModel(data: MainViewModelResponse?) {
  if (data is RepositoryResponse && data.repositoryEvent is FindBusStopByLocationResult) {
    val busAdapter = adapter as BusStopAdapter
    val oldModel = busAdapter.model as MutableList<BusStop>
    val newModel = data.repositoryEvent.busStopList
    val diffCallback = BusStopDiff(oldModel, newModel)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    oldModel.clear()
    oldModel.addAll(newModel)
    diffResult.dispatchUpdatesTo(busAdapter)
    scrollToPosition(0)
  }
}
