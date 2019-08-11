package com.example.mydictionary

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import com.example.mydictionary.controllers.LibraryController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.EditText
import com.example.mydictionary.adapters.LibraryAdapter
import com.example.mydictionary.models.LibraryModel.Library
import android.content.Intent
import java.util.logging.Logger

class MainActivity: AppCompatActivity() {
    private val library: LibraryController = LibraryController(this)
    private val logger = Logger.getLogger("Asd")
    private var isToolbarShowed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonShow.setOnClickListener(handleToggleButton(true))
        buttonCancel.setOnClickListener(handleToggleButton(false))
        buttonCreate.setOnClickListener(handleCreate())
        setLibraryList(library.get())
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText && isToolbarShowed) {
                val inputRect = Rect()
                val buttonCloseRect = Rect()
                val buttonCreateRect = Rect()

                libraryName.getGlobalVisibleRect(inputRect)
                buttonCreate.getGlobalVisibleRect(buttonCreateRect)
                buttonCreate.getGlobalVisibleRect(buttonCloseRect)

                if (isOutsideClicked(arrayOf(buttonCloseRect, buttonCreateRect, inputRect), event)) {
                    hideCreateBar()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun isOutsideClicked(rects: Array<Rect>, event: MotionEvent): Boolean {
        rects.map { if (it.contains(event.rawX.toInt(), event.rawY.toInt())) return false }
        return true
    }

    private fun handleToggleButton(isShow: Boolean): View.OnClickListener {
        return View.OnClickListener {
            if (isShow) showCreateBar()
            else hideCreateBar()
        }
    }

    private fun handleCreate(): View.OnClickListener {
        return View.OnClickListener {
            val newData = library.create(libraryName.text.toString())
            hideCreateBar()
            setLibraryList(newData)
        }
    }

    private fun showCreateBar() {
        if (!isToolbarShowed) {
            isToolbarShowed = true
            showCreatingLib(0, 200)
            buttonShow.hide()
            buttonCancel.show()
            buttonCreate.show()
        }
    }

    private fun hideCreateBar() {
        if (isToolbarShowed) {
            isToolbarShowed = false
            buttonCancel.hide()
            buttonCreate.hide()
            buttonShow.show()
            libraryName.text.clear()
            showCreatingLib(0, 200)
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(libraryName.windowToken, 0)
        }
    }

    private fun handleOpenLibrary(library: Library) {
        val intent = Intent(this@MainActivity, WordsActivity::class.java)
        intent.putExtra("library_id", library.ID)
        intent.putExtra("library_title", library.name)
        startActivity(intent)
    }

    private fun showCreatingLib(from: Int, to: Int) {
        showLibrary(isToolbarShowed)
        val valueAnimator = ValueAnimator.ofInt(from, to)
        val heightOfContainer = appBar.height
        valueAnimator.apply {
            addUpdateListener {
                val params = appBar.layoutParams
                if (!isToolbarShowed) params.height = heightOfContainer - it.animatedValue as Int
                else params.height = heightOfContainer + it.animatedValue as Int
                appBar.layoutParams = params
            }
            interpolator = LinearInterpolator()
            duration = 200
            start()
        }
        libraryName.requestFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(libraryName, 2)
    }

    private fun showLibrary(show: Boolean) {
        if (show) {
            libraryName.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).setDuration(300).start()
            }
        } else {
            libraryName.apply {
                alpha = 1f
                visibility = View.VISIBLE
                animate().alpha(0f).setDuration(300).start()
            }
        }
    }

    private fun setLibraryList(data: Array<Library>) {
        val adapter = LibraryAdapter(this, R.layout.item_library, data, { library -> handleOpenLibrary(library) })
        library_list.adapter = adapter
    }
}
