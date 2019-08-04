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
import android.widget.ArrayAdapter
import com.example.mydictionary.controllers.LibraryController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.EditText



class MainActivity : AppCompatActivity() {
    val library: LibraryController = LibraryController(this)
    var isToolbarShowed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonShow.setOnClickListener(handleToggleButton(true))
        buttonCancel.setOnClickListener(handleToggleButton(false))

        setContent(library.get())
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

    private fun showCreateBar() {
        if (!isToolbarShowed) {
            slideView(0, 400)
            buttonShow.hide()
            buttonCancel.show()
            buttonCreate.show()
            isToolbarShowed = true
        }
    }

    private fun hideCreateBar() {
        if (isToolbarShowed) {
            buttonCancel.hide()
            buttonCreate.hide()
            buttonShow.show()
            slideView(400, 0)
            isToolbarShowed = false
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(libraryName.windowToken, 0)
            libraryName.text.clear()
        }
    }

    private fun slideView(from: Int, to: Int) {
        var startAlpha = 0f
        var endAlpha = 1f
        var duration: Long = 500

        if (from == 400) {
            startAlpha = 1f
            endAlpha = 1f
            duration = 50
        }

        libraryName.apply {
            alpha = startAlpha
            visibility = View.VISIBLE
            animate().alpha(endAlpha).setDuration(duration).start()
        }
        val valueAnimator = ValueAnimator.ofInt(from, to)
        valueAnimator.addUpdateListener {
            val params = containerCreate.layoutParams
            params.height = it.animatedValue as Int
            containerCreate.layoutParams = params
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 250
        valueAnimator.start()
        libraryName.requestFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(libraryName, 2)
    }

    private fun setContent(data: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        library_list.adapter = adapter
    }
}
