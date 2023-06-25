package com.sakr.prayertimesapp.app.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sakr.prayertimesapp.R
import com.sakr.prayertimesapp.app.viewmodel.MainViewModel
import com.sakr.prayertimesapp.data.local.PreferencesHelper
import com.sakr.prayertimesapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var preferencesHelper: PreferencesHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)

        lifecycleScope.launch {
            viewModel.getPrayerTimesCalendar()
            delay(3000)
            val isFirstTime = preferencesHelper.getIsUserFirstTime()
            if (isFirstTime) {
                this@SplashFragment.findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
            } else {
                viewModel.getPrayerTimesCalendar()
                this@SplashFragment.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }


        // Inflate the layout for this fragment
        return binding.root
    }

}