package com.mar.project.drinkingreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.mar.project.drinkingreminder.R
import com.mar.project.drinkingreminder.databinding.FragmentUserProfileBinding

class UserProfileFragment : DialogFragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveUsername.setOnClickListener {
            val getName = binding.etUsername.text.toString()

            if(getName.isEmpty()){
                Toast.makeText(context, R.string.username_empty, Toast.LENGTH_LONG).show()
            } else {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("name", getName)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}