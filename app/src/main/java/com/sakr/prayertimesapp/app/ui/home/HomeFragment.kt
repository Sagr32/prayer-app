package com.sakr.prayertimesapp.app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.sakr.prayertimesapp.R
import com.sakr.prayertimesapp.app.viewmodel.MainViewModel
import com.sakr.prayertimesapp.databinding.FragmentHomeBinding
import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewPager: ViewPager2

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        viewPager = binding.viewPager
        viewModel.getPrayerTimesFromLocal()
        viewModel.getAddress()
        observePrayerTimes()
        observeViewPager()
        observeNextPrayer()
        observeNextPrayerTime()
        observeAddress()
        return binding.root
    }

    private fun observeAddress() {
        lifecycleScope.launch {
            viewModel.address.collect {
                it?.let {
                    Log.e("TAG", "observeAddress: $it", )
                    binding.tvCity.text = it
                }
            }
        }
    }

    private fun observeNextPrayer() {
        lifecycleScope.launch {
            viewModel.nextPrayerName.collect {
                it?.let {
                    Log.e("TAG", "observeNextPrayer: $it")
                    viewModel.startCountDownTimer()
                    binding.tvNextPrayerName.text = it
                }
            }
        }
    }

    private fun observeNextPrayerTime() {
        lifecycleScope.launch {
            viewModel.nextPrayerTime.collect {
                it?.let {
                    binding.tvNextPrayerTime.text = it.toString()
                }
            }
        }
    }

    private fun observeViewPager() {
        lifecycleScope.launch {
            viewModel.viewPagerCounter.collect {
                viewPager.currentItem = it
            }
        }

    }

    private fun observePrayerTimes() {
        lifecycleScope.launch {
            viewModel.prayerTimesFromLocal.collect {
                if (it?.isNotEmpty() == true) {
                    viewModel.calculateNextPrayerTimeAndName(it!![0])
                    viewPager.adapter = PrayerTimeViewPagerAdapter(
                        it,
                        {
                            viewModel.setViewPagerCounter(
                                viewPager.currentItem++
                            )
                        },
                        {
                            viewModel.setViewPagerCounter(
                                viewPager.currentItem--
                            )
                        }
                    )


                }
            }
        }
    }
}