/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.objectdetection

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.Text.text
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding
import java.util.Locale

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class MainActivity : AppCompatActivity() {
    var tts: TextToSpeech? = null
    var btnSpeak: Button? = null
    var btnClose: Button? = null
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        btnSpeak = findViewById(R.id.btnSpk)
        btnClose = findViewById(R.id.btnclose)
        tts = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                tts!!.setLanguage( Locale("en_GB"))
            }
            else{
                println("error")
            }
        };



        btnSpeak!!.setOnClickListener{speakOut()}
        btnClose!!.setOnClickListener{closeApp()}
        Handler().postDelayed({
            tts!!.speak("Detecting , $text , ... Click the middle portion to hear what is in front of you. And click the top portion to go back.", TextToSpeech.QUEUE_FLUSH, null, "")

        }, 1500)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
    fun speakOut() {
        println( text);
        tts!!.speak(if(text=="")"Nothing" else text, TextToSpeech.QUEUE_FLUSH, null, "")
//       Thread(Runnable {
//
//
//
//       }).start()


    }
    fun closeApp(){
        finish()
    }
    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
