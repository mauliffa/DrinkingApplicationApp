package com.mar.project.drinkingreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.mar.project.drinkingreminder.R
import com.mar.project.drinkingreminder.databinding.FragmentSetGoalBinding

class SetGoalFragment : DialogFragment() {
    private var _binding: FragmentSetGoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding =  FragmentSetGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSetGoal.setOnClickListener {
            val getSetGoal = binding.etSetGoal.text.toString()

            if(getSetGoal.isEmpty()){
                Toast.makeText(context, R.string.setGoal_empty, Toast.LENGTH_LONG).show()
            } else {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("goal", getSetGoal)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}