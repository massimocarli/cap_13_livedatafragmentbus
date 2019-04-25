package uk.co.massimocarli.livedatabus.busstop.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.livedatabus.databinding.BusStopItemLayoutBinding
import uk.co.massimocarli.livedatabus.db.BusStop
import uk.co.massimocarli.livedatabus.util.OnSelectedItemListener

/**
 * The Adapter fro the BusStop RecyclerView
 */
class BusStopAdapter(
  val model: List<BusStop>,
  val listener: OnSelectedItemListener<BusStop>
) : RecyclerView.Adapter<BusStopViewHolder>() {

  lateinit var binding: BusStopItemLayoutBinding

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BusStopViewHolder {
    binding = BusStopItemLayoutBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return BusStopViewHolder(binding, listener)
  }

  override fun getItemCount(): Int = model.size

  override fun onBindViewHolder(
    holder: BusStopViewHolder,
    position: Int
  ) = holder.bindModel(model[position])
}