package com.example.hpindonesia

import androidx.room.*

@Dao
interface HpDao {
    @Insert
    fun insert(hp: Hp)

    @Update
    fun update(hp: Hp)

    @Delete
    fun delete(hp: Hp)

    @Query("SELECT * FROM hp")
    fun selectAll() : List<Hp>

    @Query("SELECT * FROM hp WHERE id=:id")
    fun select(id: Int) : Hp
}