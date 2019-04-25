package uk.co.massimocarli.livedatabus.busstop.list

import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.livedatabus.databinding.BusStopItemLayoutBinding
import uk.co.massimocarli.livedatabus.db.BusStop
import uk.co.massimocarli.livedatabus.util.OnSelectedItemListener

/**
 * This is the ViewHolder for the BusStop entity
 */
class BusStopViewHolder(
  val binding: BusStopItemLayoutBinding,
  listener: OnSelectedItemListener<BusStop>
) :
  RecyclerView.ViewHolder(binding.root) {

  lateinit var model: BusStop

  init {
    binding.busStopName.setOnClickListener {
      listener.onSelected(model)
    }
    binding.busStopDirection.setOnLongClickListener {
      listener.onSelected(model, true)
      true
    }
  }

  fun bindModel(newModel: BusStop) {
    model = newModel
    binding.busStop = newModel
  }
}