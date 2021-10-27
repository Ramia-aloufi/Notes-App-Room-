package com.example.notesapproom

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var btn:Button
    lateinit var et1:EditText
    var note = ""
    lateinit var sp:SharedPreferences
    lateinit var rv:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button)
        et1 = findViewById(R.id.editTextTextPersonName)
        rv =  findViewById(R.id.rv)
        not()
        btn.setOnClickListener {
            note = et1.text.toString()
            if(note.isNotEmpty()) {
                it.hideKeyboard()
                save()
                not()
            }else{
                Toast.makeText(this,"Write a note",Toast.LENGTH_SHORT).show()
            }

}
    }

    fun save(){
         note = et1.text.toString()
        val s = Note(null,note)
        Log.d("ss","$s")
        CoroutineScope(Dispatchers.IO).launch {
            NoteDatabase.getInstance(applicationContext).StudentDao().insertNote(s)}
        Toast.makeText(applicationContext, "data saved successfully! ", Toast.LENGTH_SHORT)
            .show();
        not()
        et1.text.clear()
    }

    fun retrive(): MutableList<Note> {
        var notelist = mutableListOf<Note>()
        var f = NoteDatabase.getInstance(applicationContext).StudentDao()
            .getAllUserInfo()
        for(i in 0 until f.size){
            notelist .add(f.get(i))
        }
        Log.d("notelist","$notelist")
        return notelist
    }


    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun not(){
        rv.adapter = MyAdapter(retrive(),this)
        rv.layoutManager = LinearLayoutManager(this)
    }
    fun preUpdate(item:Note) {
        var txtt = EditText(this)
        txtt.setText(item.name)
        AlertDialog.Builder(this)
            .setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
                updatde(txtt.text.toString(), item)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
            .setTitle("Update Note")
            .setView(txtt)
            .create()
            .show()
    }
    fun updatde(item:String,note:Note){
        var rr = Note(note.id,item)
//        CoroutineScope(Dispatchers.IO).launch {
            NoteDatabase.getInstance(applicationContext).StudentDao().UpdateNote(rr)
        not()
        Toast.makeText(applicationContext, "data Updated successfully! ", Toast.LENGTH_SHORT)
            .show();
    }
    fun preDelete(item:Note){
        AlertDialog.Builder(this)
            .setPositiveButton("delete", DialogInterface.OnClickListener{
                    _,_ -> delete(item)

            })
            .setNegativeButton("No", DialogInterface.OnClickListener{
                    dialog,_ -> dialog.cancel()
            })
            .setTitle("Delete Note?")
            .create()
            .show()
    }
    fun delete(note: Note){
//        CoroutineScope(Dispatchers.IO).launch {
            NoteDatabase.getInstance(applicationContext).StudentDao().DeleteNote(note)
        Toast.makeText(applicationContext, "data deleted successfully! ", Toast.LENGTH_SHORT)
            .show()
        not()

    }

}







