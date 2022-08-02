package com.example.hpindonesia

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Hp (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nama: String = "",
    var latin: String = "",
    var keterangan: String = "",
    var url: String = "",
    var gambar: String = ""
) : Parcelable