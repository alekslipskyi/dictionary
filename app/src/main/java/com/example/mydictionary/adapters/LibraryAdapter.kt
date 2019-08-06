package com.example.mydictionary.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mydictionary.models.LibraryModel.Library
import java.util.logging.Logger

class LibraryAdapter(
    context: Context,
    @LayoutRes private val layoutRes: Int,
    private val libraries: Array<Library>,
    private val clickListener: (library: Library, view: View) -> Unit
): ArrayAdapter<Library>(context, layoutRes, libraries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View{
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutRes, parent, false) as TextView
        view.text = libraries[position].name
        view.setOnClickListener { clickListener.invoke(libraries[position], it) }
        return view
    }
}