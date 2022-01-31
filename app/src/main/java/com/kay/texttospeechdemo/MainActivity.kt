package com.kay.texttospeechdemo

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kay.texttospeechdemo.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Need to create a text to speech object
    private var tts: TextToSpeech? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)
        binding.btnSpeak.setOnClickListener {
            if (binding.etEnteredText.text.isEmpty()) {
                Toast.makeText(this, "Enter a text to speak.", Toast.LENGTH_SHORT).show()
            } else {
                // todo speak the text please
                speakOut(binding.etEnteredText.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed!!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
    }
}
