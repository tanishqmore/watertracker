package com.afl.waterReminderDrinkAlarmMonitor.ui.drinks

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.afl.waterReminderDrinkAlarmMonitor.R
import com.afl.waterReminderDrinkAlarmMonitor.databinding.DrinksFragmentBinding
import com.afl.waterReminderDrinkAlarmMonitor.ui.dashboard.DashboardViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DrinksFragment : Fragment() {

    companion object {
        fun newInstance() =
            DrinksFragment()
    }

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: DrinksFragmentBinding
    private var drinkButtonCheckStatus = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.drinks_fragment, container, false)

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.dashboardViewModel = dashboardViewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = this

        //admob setup
        // dummy ad banner id ca-app-pub-3940256099942544/6300978111
        // real ad banner id ca-app-pub-7954399632679605/9743680462
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


        binding.drinkButton.setOnClickListener {
            // oncelikle secili bir icecek var mi diye kontrol ediyor
            if (!drinkButtonCheckStatus) {
                Snackbar.make(binding.root, getString(R.string.select_drink), Snackbar.LENGTH_SHORT)
                    .show()
            } else {

                lifecycleScope.launch {
                    dashboardViewModel.drink()
                    it.findNavController().navigate(R.id.action_drinksFragment_to_navigation_home)
                }

            }

        }

        buttonListeners()

        return binding.root
    }

    // onchecked lister to manage only one selected toggle button exist at a time
    private var toggleButtonHandler =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (buttonView != binding.waterButton) binding.waterButton.isChecked = false
                if (buttonView != binding.coffeeButton) binding.coffeeButton.isChecked = false
                if (buttonView != binding.teaButton) binding.teaButton.isChecked = false
                if (buttonView != binding.juiceButton) binding.juiceButton.isChecked = false
                if (buttonView != binding.sodaButton) binding.sodaButton.isChecked = false
                if (buttonView != binding.beerButton) binding.beerButton.isChecked = false
                if (buttonView != binding.wineButton) binding.wineButton.isChecked = false
                if (buttonView != binding.milkButton) binding.milkButton.isChecked = false
                if (buttonView != binding.yogurtButton) binding.yogurtButton.isChecked = false
                if (buttonView != binding.milkshakeButton) binding.milkshakeButton.isChecked = false
                if (buttonView != binding.energyButton) binding.energyButton.isChecked = false
                if (buttonView != binding.lemonadeButton) binding.lemonadeButton.isChecked = false

                drinkButtonCheckStatus = true

                dashboardViewModel.drinkTypeHandler(buttonView.tag.toString())

            }
        }

    private fun buttonListeners() {
        binding.waterButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.coffeeButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.teaButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.juiceButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.sodaButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.beerButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.wineButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.milkButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.yogurtButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.milkshakeButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.energyButton.setOnCheckedChangeListener(toggleButtonHandler)
        binding.lemonadeButton.setOnCheckedChangeListener(toggleButtonHandler)
    }

}
