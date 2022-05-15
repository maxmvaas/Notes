package ru.maxmv.notes.data

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int = 0,
    val title: String,
    val text: String,
    var color: Int,
) : Parcelable