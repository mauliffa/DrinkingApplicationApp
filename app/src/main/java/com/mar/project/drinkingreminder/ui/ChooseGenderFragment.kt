package com.mar.project.drinkingreminder.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.mar.project.drinkingreminder.R
import com.mar.project.drinkingreminder.databinding.FragmentChooseGenderBinding

class ChooseGenderFragment : DialogFragment() {
    private var _binding: FragmentChooseGenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentChooseGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val gender: String? = sharedPreferences.getString("gender", " ")
        binding.tvUserGender.text = gender

        binding.btnSaveGender.setOnClickListener {
            val intSelectButton: Int = binding.radioGroup.checkedRadioButtonId
            if(intSelectButton != -1){
                val selectedRadioButton: RadioButton = view.findViewById(intSelectButton)
                val setGender = getString(R.string.user_gender, selectedRadioButton.text.toString())

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("gender", setGender)
                editor.apply()

                dismiss()
            } else {
                Toast.makeText(context, R.string.gender_empty, Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnCancel.setOnClickListener { dismiss() }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}