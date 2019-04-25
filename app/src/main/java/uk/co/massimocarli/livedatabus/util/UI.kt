package uk.co.massimocarli.livedatabus.util

interface OnSelectedItemListener<Item> {
    fun onSelected(item: Item, isLongClick: Boolean = false)
}
