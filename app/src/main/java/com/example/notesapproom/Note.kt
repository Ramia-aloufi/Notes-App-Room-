package com.example.notesapproom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Note (
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")  var id : Int? = null, // this is how can include id if needed
    @ColumnInfo(name = "MyNote") val name: String
)