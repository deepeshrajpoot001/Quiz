package com.pushparaj.quiz

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pushparaj.quiz.databinding.ActivityFrontBinding
import com.pushparaj.quiz.databinding.FragmentLoginBinding


class FrontActivity : AppCompatActivity() {


    lateinit var binding : ActivityFrontBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(binding.root)


        showLogInBottomSheet()

        binding.uploadQuestionBtn.setOnClickListener{
            startActivity(Intent(this,UploadQuestionActivity::class.java))
        }

        binding.startQuizBtn.setOnClickListener{
            // make condition of LogIn
            startActivity(Intent(this,MainActivity::class.java))
        }


    }


    fun showLogInBottomSheet(){
        var fragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)
        var bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)



        bottomSheetDialog.setContentView(fragmentLoginBinding.root)


        fragmentLoginBinding.buttonSignIn.setOnClickListener{
            if(fragmentLoginBinding.userName.text.toString() != ""  ) {
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.show()


    }
}