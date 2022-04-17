package com.mar.project.drinkingreminder.ui

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mar.project.drinkingreminder.*
import com.mar.project.drinkingreminder.data.Item
import com.mar.project.drinkingreminder.databinding.FragmentHomeBinding
import java.util.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var item: Item
    private var name: String? = ""
    private var goal: String? = "0"

    private val viewModel: DrinkingViewModel by activityViewModels {
        DrinkingViewModelFactory(
            (activity?.application as DrinkingApplication).database
                .itemDao()
        )
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.i("Alarm Bell", "Alarm just fired from home fragment")

            viewModel.resetData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(receiver, IntentFilter("thisIsForMyFragment"))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getName()
        getGoal()

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val savedName: String? = sharedPreferences.getString("name", name)
        val savedGoal: String? = sharedPreferences.getString("goal", goal)
        editor.putString("name", ", $name")
        editor.putString("goal", "$goal ml")
        editor.apply()

        binding.tvGreeting.text = getString(R.string.greeting, savedName)
        binding.tvGoalValue.text = savedGoal
        binding.btnDrink.setOnClickListener { chooseSize() }

        setTimeToResetData()

        val adapter = ItemListAdapter {}
        binding.rvRecap.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let { adapter.submitList(it) }
        }
        binding.rvRecap.layoutManager = LinearLayoutManager(this.context)

        adapter.setOnItemClickCallback(object: ItemListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Item){
                deleteAlertDialog(data)
            }
        })
    }

    private fun getName(){
        val currentFragment = findNavController().getBackStackEntry(R.id.navigation_home)
        val dialogObserver = LifecycleEventObserver{_, event ->

            if (event == Lifecycle.Event.ON_RESUME && currentFragment.savedStateHandle.contains("name")) {
                name = currentFragment.savedStateHandle.get("name")
            }
        }

        val dialogLifecycle = currentFragment.lifecycle
        dialogLifecycle.addObserver(dialogObserver)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver{_,event ->
            if(event == Lifecycle.Event.ON_DESTROY){
                dialogLifecycle.removeObserver(dialogObserver)
            }
        })
    }

    private fun getGoal(){
        binding.tvGoalValue.text = getString(R.string.size)

        val currentFragment = findNavController().getBackStackEntry(R.id.navigation_home)
        val dialogObserver = LifecycleEventObserver{_, event ->

            if (event == Lifecycle.Event.ON_RESUME && currentFragment.savedStateHandle.contains("goal")) {
                goal = currentFragment.savedStateHandle.get("goal")
            }
        }

        val dialogLifecycle = currentFragment.lifecycle
        dialogLifecycle.addObserver(dialogObserver)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver{_,event ->
            if(event == Lifecycle.Event.ON_DESTROY){
                dialogLifecycle.removeObserver(dialogObserver)
            }
        })
    }

    private fun chooseSize(){
        val popUpSize = ChooseSizeFragment()
        popUpSize.show((activity as AppCompatActivity).supportFragmentManager, "popUpSize")
    }

    private fun deleteAlertDialog(item: Item){
        val getDeleteSubtitle = getString(R.string.delete_subtitle)
        val getTime = getString(R.string.time)
        val getSize = getString(R.string.size)
        val subtitle = "$getDeleteSubtitle " +
                "\n $getTime = ${item.time} " +
                "\n $getSize = ${item.size} ml"

        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(subtitle)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteItem(item)
                Toast.makeText(context, R.string.delete_true, Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                Toast.makeText(context, R.string.delete_false, Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun setTimeToResetData() {
        val calendar: Calendar = Calendar.getInstance()
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0
            )

        val interval = AlarmManager.INTERVAL_DAY
        setAlarm(calendar.timeInMillis, interval)
    }

    private fun setAlarm(timeInMillis: Long, interval: Long) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            interval,
            pendingIntent
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        context?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(receiver) }
    }

}
