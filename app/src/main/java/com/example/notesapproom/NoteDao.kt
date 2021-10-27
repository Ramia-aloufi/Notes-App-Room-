package com.example.notesapproom

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note  ORDER BY id ASC")
    fun getAllUserInfo(): List<Note>

    @Insert
    fun insertNote(note: Note)

    @Delete
    fun DeleteNote(note: Note)

    @Update
    fun UpdateNote(note: Note)


}