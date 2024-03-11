package dev.shrishri1108.notesroomapp.dataLayer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getOrderByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY dateAdded DESC")
    fun getOrderByDateAddedAndDecended(): Flow<List<Note>>
}