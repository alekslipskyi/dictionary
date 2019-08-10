package com.example.mydictionary

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mydictionary.adapters.LibraryAdapter
import com.example.mydictionary.controllers.LibraryController
import com.example.mydictionary.models.LibraryModel
import kotlinx.android.synthetic.main.content_main.*

class WordsActivity : AppCompatActivity() {
    private val libraryController: LibraryController = LibraryController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.words)

        setLibraryList(libraryController.get())
    }

    private fun handleOpenLibrary(library: LibraryModel.Library) {
        val intent = Intent(this, WordsActivity::class.java)
        startActivity(intent)
    }

    private fun setLibraryList(data: Array<LibraryModel.Library>) {
        val adapter = LibraryAdapter(this, android.R.layout.simple_list_item_activated_1, data, { library -> handleOpenLibrary(library) })
        library_list.adapter = adapter
    }
}