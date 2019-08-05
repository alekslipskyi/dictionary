package com.example.mydictionary.models

import android.provider.BaseColumns

object LibraryModel : BaseColumns {
    const val NAME = "library"

    object Columns {
        const val name = "library_name"
        const val _id = "_id"
    }

    object Queries {
        const val get = "SELECT * FROM $NAME"
        const val createTable =
            "CREATE TABLE $NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${LibraryModel.Columns.name} TEXT)"
        const val dropTable = "DROP TABLE IF EXISTS $NAME"
    }

    class Library(val name: String, val ID: Long) {}
}