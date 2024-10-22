package com.anasmohammed.qiblatalmuslim.f02Home.presentation.adapters

import com.anasmohammed.qiblatalmuslim.R
import com.anasmohammed.qiblatalmuslim.databinding.ItemDayBinding
import com.anasmohammed.qiblatalmuslim.f00Core.manager.model.CalenderManagerDay
import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseAdapter
import com.anasmohammed.qiblatalmuslim.f00Core.extensions.getResourceColor

class DaysAdapter : BaseAdapter<CalenderManagerDay, ItemDayBinding>(
    areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
) {

    private var index = 0

    override fun onBindItemViewHolder(holder: BaseViewHolder<ItemDayBinding>, position: Int) {
        val day = getItem(position)

        holder.view.dayNumberTv.text = day.dayNumber
        holder.view.dayNameTv.text = day.dayName.take(2)
        holder.setupSelectedItem(day)
    }

    override fun actionOnItemClick(item: CalenderManagerDay, position: Int) {
        super.actionOnItemClick(item, position)

        if (index != -1) {
            currentList[index].isSelected = false
            notifyItemChanged(index)
        }

        index = position

        currentList[index].isSelected = true
        notifyItemChanged(index)
    }

    private fun BaseViewHolder<ItemDayBinding>.setupSelectedItem(day: CalenderManagerDay) {
        if (day.isSelected) {
            view.container.setCardBackgroundColor(view.container.context.getResourceColor(R.color.primaryColor))
        } else {
            view.container.setCardBackgroundColor(view.container.context.getResourceColor(R.color.white))
        }
    }

    fun resetIndex() {
        index = 0
    }
}