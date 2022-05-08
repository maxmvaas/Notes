package ru.maxmv.notes.data.db

import android.os.Parcelable

import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val text: String
) : Parcelable