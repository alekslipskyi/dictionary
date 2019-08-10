package com.example.mydictionary.controllers

import android.content.ContentValues
import android.content.Context
import com.example.mydictionary.core.DB
import com.example.mydictionary.models.WordsModel
import com.example.mydictionary.models.WordsModel.Words
import com.example.mydictionary.models.WordsModel.Queries.createTable
import com.example.mydictionary.models.WordsModel.Queries.dropTable
import com.example.mydictionary.models.WordsModel.Queries
import com.example.mydictionary.models.WordsModel.Columns

class WordsController(context: Context, val library_id: Long): DB(context, createTable, dropTable) {
    private var list: Array<Words> = emptyArray()

    private fun synchronization() {
        val db = this.readableDatabase
        val cursor = db.rawQuery(Queries.get, null)

        var newList: Array<Words> = emptyArray()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(Columns.ID))
                val libraryID = cursor.getLong(cursor.getColumnIndex(Columns.library_id))
                val name = cursor.getString(cursor.getColumnIndex(Columns.name))
                newList += Words(name, libraryID, id)
            } while (cursor.moveToNext())
        }

        db.close()
        list = newList
    }

    fun create(name: String): Array<Words> {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Columns.name, name)
        contentValues.put(Columns.library_id, library_id)
        val id = db.insert(WordsModel.NAME, null, contentValues)
        db.close()
        return list + Words(Columns.name, library_id, id)
    }

    fun get(): Array<Words> {
        if (list.isEmpty()) this.synchronization()
        return list
    }
}