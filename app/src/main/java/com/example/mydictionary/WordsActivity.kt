package com.example.mydictionary

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.mydictionary.adapters.LibraryAdapter
import com.example.mydictionary.controllers.LibraryController
import com.example.mydictionary.models.LibraryModel
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.words.*

class WordsActivity : AppCompatActivity() {
    private val libraryController: LibraryController = LibraryController(this)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.words)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        setLibraryList(libraryController.get())
    }

    private fun handleOpenLibrary(library: LibraryModel.Library, view: View) {
        val intent = Intent(this, WordsActivity::class.java)

        startActivity(intent)
    }

    private fun setLibraryList(data: Array<LibraryModel.Library>) {
        val adapter = LibraryAdapter(this, android.R.layout.simple_list_item_activated_1, data, { library, view -> handleOpenLibrary(library, view) })
        library_list.adapter = adapter
    }
}