package com.mar.project.drinkingreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mar.project.drinkingreminder.DrinkingApplication
import com.mar.project.drinkingreminder.DrinkingViewModel
import com.mar.project.drinkingreminder.DrinkingViewModelFactory
import com.mar.project.drinkingreminder.R
import com.mar.project.drinkingreminder.databinding.FragmentChooseSizeBinding
import java.text.SimpleDateFormat
import java.util.*

class ChooseSizeFragment : DialogFragment() {
    private var _binding: FragmentChooseSizeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkingViewModel by activityViewModels {
        DrinkingViewModelFactory(
            (activity?.application as DrinkingApplication).database
                .itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentChooseSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd100ml.setOnClickListener {
            addNewItem(100)
            Toast.makeText(context, R.string.add_100, Toast.LENGTH_SHORT).show()
        }
        binding.btnAdd200ml.setOnClickListener {
            addNewItem(200)
            Toast.makeText(context, R.string.add_200, Toast.LENGTH_SHORT).show()
        }
        binding.btnAdd300ml.setOnClickListener {
            addNewItem(300)
            Toast.makeText(context, R.string.add_300, Toast.LENGTH_SHORT).show()
        }
        binding.btnAdd400ml.setOnClickListener {
            addNewItem(400)
            Toast.makeText(context, R.string.add_400, Toast.LENGTH_SHORT).show()
        }
        binding.btnAddCustomizeSize.setOnClickListener {
            val getCustomizeSize = binding.etCustomize.text.toString()

            if(getCustomizeSize.isEmpty()){
                Toast.makeText(context, R.string.customize_empty, Toast.LENGTH_SHORT).show()
            } else {
                val customizeSize = getCustomizeSize.toInt()
                addNewItem(customizeSize)
                Toast.makeText(context, R.string.add_customize, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addNewItem(size: Int){
        val currentTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        viewModel.addNewItem(currentTime, size)

        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}