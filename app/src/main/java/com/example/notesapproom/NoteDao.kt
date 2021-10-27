package com.example.notesapproom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note  ORDER BY id ASC")
    fun getAllUserInfo(): List<Note>

    @Insert
    fun insertStudent(note: Note)


}