package com.mar.project.drinkingreminder.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.mar.project.drinkingreminder.DrinkingApplication
import com.mar.project.drinkingreminder.DrinkingViewModel
import com.mar.project.drinkingreminder.DrinkingViewModelFactory
import com.mar.project.drinkingreminder.R
import com.mar.project.drinkingreminder.databinding.FragmentSettingsBinding
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkingViewModel by activityViewModels {
        DrinkingViewModelFactory(
            (activity?.application as DrinkingApplication).database
                .itemDao()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGender.setOnClickListener {
            val popUpGender = ChooseGenderFragment()
            popUpGender.show((activity as AppCompatActivity).supportFragmentManager, "popUpGender")
        }

        binding.btnUsername.setOnClickListener {
            val popUpUsername = UserProfileFragment()
            popUpUsername.show((activity as AppCompatActivity).supportFragmentManager, "popUpUsername")
        }

        binding.btnGoals.setOnClickListener {
            val popUpGoal = SetGoalFragment()
            popUpGoal.show((activity as AppCompatActivity).supportFragmentManager, "popUpGoal")
        }

        binding.btnReminderSchedule.setOnClickListener {
            Toast.makeText(context, R.string.available_soon, Toast.LENGTH_LONG).show()
        }

        binding.btnReminderSound.setOnClickListener {
            Toast.makeText(context, R.string.available_soon, Toast.LENGTH_LONG).show()
        }

        binding.btnLanguage.setOnClickListener {
            val changeLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(changeLanguage)
        }

        binding.btnReset.setOnClickListener { alertDialog() }
    }

    private fun alertDialog(){
        AlertDialog.Builder(context)
            .setTitle(R.string.reset)
            .setMessage(R.string.reset_subtitle)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.resetData()
                Toast.makeText(context, R.string.reset_true, Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                Toast.makeText(context, R.string.reset_false, Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}