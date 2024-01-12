package com.pushparaj.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.pushparaj.quiz.databinding.ActivityMainBinding
import com.pushparaj.quiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var  binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ss:String = intent.getStringExtra("score").toString()

        binding.scoreBoard.text = "Your Score : $ss"

        binding.homeButton.setOnClickListener{
            startActivity(Intent(this,FrontActivity::class.java))
        }


    }
}