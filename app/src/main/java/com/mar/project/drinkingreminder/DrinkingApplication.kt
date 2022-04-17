package com.mar.project.drinkingreminder

import android.app.Application
import com.mar.project.drinkingreminder.data.ItemRoomDatabase

class DrinkingApplication: Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}