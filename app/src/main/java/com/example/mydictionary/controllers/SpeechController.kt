package com.example.mydictionary.controllers
import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import java.util.*

class SpeechController constructor(private val context: Context) : OnInitListener {
    private var myTTS: TextToSpeech = TextToSpeech(context, this)

    fun speak(text: String) {
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(initStatus: Int) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.language = Locale.US
        }

        if (initStatus == TextToSpeech.SUCCESS) {
            val result = myTTS.setLanguage(Locale.US)
            myTTS.setSpeechRate(0.9f)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "TTS missing or not supported ($result)")
                // Language data is missing or the language is not supported.
                // showError(R.string.tts_lang_not_available);

            } else {
                // Initialization failed.
                Log.e(TAG, "Error occured")
            }

        }
    }

    fun stopTTS() {
        myTTS.shutdown()
        myTTS.stop()
    }

    companion object {
        private val TAG = "TextToSpeechController"
    }
}