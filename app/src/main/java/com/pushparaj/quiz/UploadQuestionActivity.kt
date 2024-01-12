package com.pushparaj.quiz

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pushparaj.quiz.databinding.ActivityUploadQuestionBinding

class UploadQuestionActivity : AppCompatActivity() {

    lateinit var  binding : ActivityUploadQuestionBinding
    var rightOption : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val optionsIntList =arrayOf(1,2,3,4)
        val optionsStrList = ArrayList(listOf(optionsIntList))
        val adapterOptions: ArrayAdapter<Int> = ArrayAdapter<Int>(this,R.layout.simple_spinner_item,optionsIntList)
        binding.rightAnswer.adapter = adapterOptions


        binding.rightAnswer.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                rightOption = position+1
                Toast.makeText(this@UploadQuestionActivity,"right option is : $rightOption",Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })







        binding.uploadBtn.setOnClickListener{
            if(binding.question.text.toString()!=""&&
                binding.option1.text.toString()!=""&&
                binding.option2.text.toString()!=""&&
                binding.option3.text.toString()!=""&&
                binding.option4.text.toString()!=""){

                binding.progressBar.visibility= View.VISIBLE



                val db = Firebase.firestore

                val user = hashMapOf(
                    "Question" to binding.question.text.toString(),
                    "Option1" to binding.option1.text.toString(),
                    "Option2" to binding.option2.text.toString(),
                    "Option3" to binding.option3.text.toString(),
                    "Option4" to binding.option4.text.toString(),
                    "RightAnswer" to binding.rightAnswer.selectedItem
                )

                db.collection("Questions")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this@UploadQuestionActivity,"Upload Done",Toast.LENGTH_SHORT).show()
                        Log.d("hiDeepesh", "DocumentSnapshot added with ID: ${documentReference.id}")
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    .addOnFailureListener { e ->
                        Log.w("hiDeepesh", "Error adding document", e)
                        Toast.makeText(this@UploadQuestionActivity,"Upload Failed",Toast.LENGTH_SHORT).show()

                        binding.progressBar.visibility = View.INVISIBLE
                    }




            }

        }

    }

}


