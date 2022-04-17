package com.mar.project.drinkingreminder.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    //mengambil satu data
    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    //mengambil semua data untuk ditampilkan di recyclerview
    @Query("SELECT * from item ORDER BY time DESC")
    fun getItems(): Flow<List<Item>>

    //menghapus semua data
    @Query("DELETE FROM item")
    suspend fun reset()
}