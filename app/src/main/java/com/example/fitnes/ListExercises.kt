package com.example.fitnes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable


class ListExercises : AppCompatActivity() {
    var exercises = ExerciseDataBase.exercises
    lateinit var excercisesLV: ListView
    var listAdapter:ListAdapter?=null
    var item:Int?=null
    lateinit var mainTB:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_exercises)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        excercisesLV = findViewById(R.id.excercisesLV)
        mainTB=findViewById(R.id.mainTB)
        setSupportActionBar(mainTB)
        title = "Тренировки по фитнесу"
        mainTB.setLogo(R.drawable.baseline_sports_gymnastics_24)
        listAdapter=ListAdapter(this@ListExercises,exercises)
        excercisesLV.adapter=listAdapter
        (listAdapter as com.example.fitnes.ListAdapter)?.notifyDataSetChanged()
        excercisesLV.onItemClickListener=
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val exercise=listAdapter!!.getItem(position)
                item=position
                val args=Bundle()
                args.putSerializable("excercise", exercise as Serializable)
                val intent=Intent(this,ActivityTwo::class.java)
                intent.putExtras(args)
                startActivity(intent)
            }
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