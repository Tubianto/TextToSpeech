package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etTts: EditText
    private lateinit var btnSpeak: Button
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTts = findViewById(R.id.et_tts)
        btnSpeak = findViewById(R.id.btn_speak)
        tts = TextToSpeech(this, object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts.setLanguage(Locale.ENGLISH)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language specified is not supported!")
                    }
                } else {
                    Log.e("TTS", "Initilization Failed!")
                }
            }
        })

        btnSpeak.setOnClickListener{
            if (etTts.text.toString().trim() == "") {
                etTts.error = "Bidang ini wajib diisi"
                etTts.requestFocus()
            } else {
                textToSpeech()
            }
        }
    }

    private fun textToSpeech() {
        val text = etTts.text.toString()
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}