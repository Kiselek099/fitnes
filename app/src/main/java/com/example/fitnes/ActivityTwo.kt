package com.example.fitnes

import android.content.Intent
import android.content.IntentSender.OnFinished
import android.media.audiofx.AudioEffect.Descriptor
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityTwo : AppCompatActivity() {
    lateinit var titleTV: TextView
    lateinit var descriptionTV: TextView
    lateinit var startBTN: Button
    lateinit var nextBTN: Button
    lateinit var timerTV: TextView
    lateinit var gifIV: ImageView
    lateinit var mainTB:Toolbar
    lateinit var currentExercise: Exercise
    lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_two)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        startBTN.setOnClickListener {
            startWorkout()
        }
        nextBTN.setOnClickListener {
            val intent=Intent(this,ListExercises::class.java)
            startActivity(intent)
        }
        currentExercise=intent.getSerializableExtra("excercise") as Exercise
    }

    private fun init() {
        titleTV = findViewById(R.id.titleTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        startBTN = findViewById(R.id.startBTN)
        nextBTN = findViewById(R.id.nextBTN)
        timerTV = findViewById(R.id.timerTV)
        gifIV = findViewById(R.id.gifIV)
        mainTB = findViewById(R.id.mainTB)
        setSupportActionBar(mainTB)
        title = "Тренировки по фитнесу"
        mainTB.setLogo(R.drawable.baseline_sports_gymnastics_24)
    }

    private fun startWorkout() {
        titleTV.text = currentExercise.name
        startBTN.isEnabled = false
        startBTN.text = "Процесс тренировки"
        startExercise()
    }
    private fun startExercise() {
            descriptionTV.text = currentExercise.description
            gifIV.setImageResource(currentExercise.gifImage)
            timerTV.text = formatTime(currentExercise.durationInSeconds)
            timer =object :CountDownTimer(
                currentExercise.durationInSeconds*1000L,
                1000
            ){
                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text=formatTime((millisUntilFinished/1000).toInt())
                }
                override fun onFinish() {
                    timerTV.text=""
                    startBTN.text="Тренировка завершена"
                    gifIV.visibility= View.VISIBLE
                    nextBTN.isEnabled=true
                    gifIV.setImageResource(R.drawable.finishgif)
                }
            }.start()
    }
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitApp -> {
                finishAffinity()
                Toast.makeText(this,"Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}