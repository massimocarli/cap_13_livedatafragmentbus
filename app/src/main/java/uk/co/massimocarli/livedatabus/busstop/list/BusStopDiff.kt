package uk.co.massimocarli.livedatabus.busstop.list

import androidx.recyclerview.widget.DiffUtil
import uk.co.massimocarli.livedatabus.db.BusStop
import java.util.*

class BusStopDiff(val before: List<BusStop>, val after: List<BusStop>) : DiffUtil.Callback() {

    // They are the same if they have the same Id, name and description
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val before = before[oldItemPosition]
        val after = after[newItemPosition]
        return Objects.equals(before, after)
    }

    override fun getOldListSize(): Int = before.size

    override fun getNewListSize(): Int = after.size

    // They are the same if they have the same Id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemsTheSame(oldItemPosition, newItemPosition)

}