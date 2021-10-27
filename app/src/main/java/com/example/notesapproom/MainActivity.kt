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
//    var i = 14
    lateinit var sp:SharedPreferences
    lateinit var rv:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button)
        et1 = findViewById(R.id.editTextTextPersonName)
        rv =  findViewById(R.id.rv)
//        sp = this.getSharedPreferences("mynotee", MODE_PRIVATE)
//          var sp1 =  sp.edit()
//        sp1 .putInt("idcounter",i)
//        sp1   .apply()
//        i = sp.getInt("idcounter",5)
//        Log.d("ii","$i")

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
        CoroutineScope(Dispatchers.IO).launch {
            NoteDatabase.getInstance(applicationContext).StudentDao().insertStudent(s)
        }
        Toast.makeText(applicationContext, "data saved successfully! ", Toast.LENGTH_SHORT)
            .show();
        not()
        et1.text.clear()
    }

    fun retrive(): MutableList<String> {
        var notelist = mutableListOf<String>()
        var f = NoteDatabase.getInstance(applicationContext).StudentDao()
            .getAllUserInfo()
        for(i in 0 until f.size){
            notelist .add(f.get(i).name)
        }
        Log.d("notelist","$notelist")
        return notelist
    }


    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun not(){
        rv.adapter?.notifyDataSetChanged()
        rv.adapter = MyAdapter(retrive(),this)
        rv.layoutManager = LinearLayoutManager(this)
    }




}


