package com.example.mydictionary.controllers

import android.content.Context
import com.example.mydictionary.helpers.DB

class LibraryController(context: Context): DB(context) {
    private val list: Array<String> = emptyArray()

    fun create(name: String): Array<String> {
        val db = this.writableDatabase
        return list
    }

    fun get(): Array<String> {
        return list
    }
}