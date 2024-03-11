package dev.shrishri1108.notesroomapp.dataLayer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val disp: String,
    val dateAdded: Long
)