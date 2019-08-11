package com.example.mydictionary.models

import android.provider.BaseColumns

object WordsModel : BaseColumns {
    const val NAME = "library"

    object Columns {
        const val name = "library_name"
        const val library_id = "library_id"
        const val ID = "_id"
    }

    object Queries {
        const val get = "SELECT * FROM $NAME WHERE library_id="
        const val createTable =
            "CREATE TABLE $NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "FOREIGN KEY(${WordsModel.Columns.library_id}) REFERENCES ${LibraryModel.NAME}(${LibraryModel.Columns.ID})" +
                    "${WordsModel.Columns.name} TEXT)"
        const val dropTable = "DROP TABLE IF EXISTS $NAME"
    }

    class Words(val name: String, val libraryID: Long, val ID: Long) {}
}