package com.anasmohammed.qiblatalmuslim.f02Home.presentation.adapters

import com.anasmohammed.qiblatalmuslim.databinding.ItemPrayerTimeBinding
import com.anasmohammed.qiblatalmuslim.f00Core.extensions.loadImage
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model.PrayerTime
import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseAdapter

class PrayerTimesAdapter : BaseAdapter<PrayerTime, ItemPrayerTimeBinding>(
    areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
) {
    override fun onBindItemViewHolder(
        holder: BaseViewHolder<ItemPrayerTimeBinding>,
        position: Int
    ) {
        val prayerTime = getItem(position)

        holder.view.nameTv.text = prayerTime.name
        holder.view.timeTv.text = prayerTime.time
        holder.view.iconIv.loadImage(prayerTime.iconRes)
    }
}