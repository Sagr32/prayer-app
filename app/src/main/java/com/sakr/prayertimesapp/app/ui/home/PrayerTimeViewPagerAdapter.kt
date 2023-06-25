package com.sakr.prayertimesapp.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakr.prayertimesapp.databinding.PagerPrayerItemBinding
import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity
import com.sakr.prayertimesapp.utils.formatDate
import com.sakr.prayertimesapp.utils.removeEESTExtension
import java.util.*

class PrayerTimeViewPagerAdapter(
    private val prayerTimeList: List<PrayerTimesEntity>,
    private val nextPage: () -> Unit,
    private val previousPage: () -> Unit
) :
    RecyclerView.Adapter<PrayerTimeViewPagerAdapter.ViewPagerViewHolder>() {


    class ViewPagerViewHolder(private val binding: PagerPrayerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PrayerTimesEntity, nextPage: () -> Unit, previousPage: () -> Unit) {

            binding.tvCurrentDate.text =
                "${item.dayOfWeek} ,${item.date.toLocaleString().split("00")[0]}"
            binding.tvCurrentHijriDate.text =
                "${item.hijriDate}"
            binding.tvFajr.text = "Fajr"
            binding.tvFajrTime.text = "${item.fajr.removeEESTExtension()}"
            binding.tvDhuhr.text = "Dhuhr"
            binding.tvDhuhrTime.text = "${item.dhuhr.removeEESTExtension()}"
            binding.tvAsr.text = "Asr"
            binding.tvAsrTime.text = "${item.asr.removeEESTExtension()}"
            binding.tvMaghrib.text = "Maghrib"
            binding.tvMaghribTime.text = "${item.maghrib.removeEESTExtension()}"
            binding.tvIsha.text = "Isha"
            binding.tvIshaTime.text = "${item.isha.removeEESTExtension()}"
            binding.ivArrowNext.setOnClickListener {
                nextPage()
            }
            binding.ivArrowBack.setOnClickListener {
                previousPage()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewPagerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = PagerPrayerItemBinding.inflate(layoutInflater, parent, false)

                return ViewPagerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
//        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.))
//
//        return ViewPagerViewHolder(view)
        return ViewPagerViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = prayerTimeList[position]
        holder.bind(item, nextPage, previousPage)
    }

    override fun getItemCount(): Int {
        return prayerTimeList.size
    }
}