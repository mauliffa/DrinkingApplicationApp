package com.mar.project.drinkingreminder

import androidx.lifecycle.*
import com.mar.project.drinkingreminder.data.Item
import com.mar.project.drinkingreminder.data.ItemDao
import kotlinx.coroutines.launch

class DrinkingViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    //mengambil data dari aktivitas user
    private fun getNewItemEntry(itemTime: String, itemSize: Int): Item {
        return Item(
            time = itemTime,
            size = itemSize
        )
    }

    //meneruskan data untuk lanjut ke dalam metode insertItem
    fun addNewItem(itemTime: String, itemSize: Int) {
        val newItem = getNewItemEntry(itemTime, itemSize)
        insertItem(newItem)
    }

    //memasukkan data ke database
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    //menghapus semua data
    fun resetData(){
        viewModelScope.launch {
            itemDao.reset()
        }
    }

    //menghapus selectedItem
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
}

class DrinkingViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkingViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}