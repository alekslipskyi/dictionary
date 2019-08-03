package com.example.mydictionary.helpers

import android.provider.BaseColumns
import com.example.mydictionary.models.LibraryModel

object QUERIES {
    // Table contents are grouped together in an anonymous object.

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${LibraryModel.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${LibraryModel.LIBRARY_NAME} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${LibraryModel.TABLE_NAME}"
}