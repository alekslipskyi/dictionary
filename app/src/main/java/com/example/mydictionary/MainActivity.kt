package com.example.mydictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.example.mydictionary.controllers.LibraryController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_add_library.*


class MainActivity : AppCompatActivity() {
    val library: LibraryController = LibraryController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        buttonCreate.setOnClickListener(handleShow())

        setContent(library.get())
    }

    fun handleShow(): View.OnClickListener {
        return View.OnClickListener {
            library_list.adapter = ArrayAdapter(this, LayoutInflater.from(this).inflate(row_add, parent, false), arrayOf("1"))
        }
    }

    private fun setContent(data: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        library_list.adapter = adapter
    }
}
