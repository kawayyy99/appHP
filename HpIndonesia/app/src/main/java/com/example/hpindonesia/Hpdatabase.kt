package com.example.hpindonesia

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Hp::class], version = 1)
abstract class Hpdatabase : RoomDatabase(){
    abstract fun hpDao() : HpDao

    companion object{
        private var instance: Hpdatabase? = null
        private val callback = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
        private  fun populate(db: Hpdatabase){
            val hpDao = db.hpDao()
        }
    }
}