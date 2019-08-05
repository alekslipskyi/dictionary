package com.example.mydictionary.controllers

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.mydictionary.core.DB
import com.example.mydictionary.models.LibraryModel.Library
import com.example.mydictionary.models.LibraryModel.Queries
import com.example.mydictionary.models.LibraryModel.Columns
import com.example.mydictionary.models.LibraryModel.NAME as TABLE

class LibraryController(context: Context): DB(context, Queries.createTable, Queries.dropTable) {
    private var list: Array<Library> = emptyArray()

    private fun synchronization() {
        val db = this.readableDatabase
        val cursor = db.rawQuery(Queries.get, null)
        Log.println(1, cursor.toString(), "hello")

        var newList: Array<Library> = emptyArray()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(Columns._id))
                val name = cursor.getString(cursor.getColumnIndex(Columns.name))
                newList += Library(name, id)
            } while (cursor.moveToNext())
        }

        db.close()
        list = newList
    }

    fun create(name: String): Array<Library> {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Columns.name, name)
        val id = db.insert(TABLE, null, contentValues)
        db.close()
        list += Library(name, id)
        return list
    }

    fun get(): Array<Library> {
        if (list.isEmpty()) synchronization()

        return list
    }
}