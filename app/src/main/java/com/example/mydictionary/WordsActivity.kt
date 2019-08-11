package com.example.mydictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.words.*

class WordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.words)
        val bundle = intent.extras
        appTitle.text = bundle!!.getString("library_title")
    }
}