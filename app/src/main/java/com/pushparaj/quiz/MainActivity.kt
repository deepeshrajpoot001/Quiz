package com.pushparaj.quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pushparaj.quiz.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity(),AnswerListener {

    lateinit var  binding : ActivityMainBinding
    private lateinit var recyclerQuestionAdapter : RecyclerQuestionAdapter
     var questionArrayList = ArrayList<QuestionModel>()
    var choosedAnswerList = ArrayList<ChooseAnswerModel>()
    var clickedCond = ArrayList<Boolean>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickedCond.add(false)
        clickedCond.add(false)
        clickedCond.add(false)
        clickedCond.add(false)
        clickedCond.add(false)


        choosedAnswerList.add(ChooseAnswerModel(binding.temp,"-1"))
        choosedAnswerList.add( ChooseAnswerModel(binding.temp,"-1"))
        choosedAnswerList.add( ChooseAnswerModel(binding.temp,"-1"))
        choosedAnswerList.add(ChooseAnswerModel(binding.temp,"-1"))
        choosedAnswerList.add(ChooseAnswerModel(binding.temp,"-1"))

        val db = Firebase.firestore

        db.collection("Questions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   questionArrayList.add(QuestionModel(document.get("Question").toString(),
                       document.get("Question").toString(),
                       document.get("Option1").toString(),
                       document.get("Option2").toString(),
                       document.get("Option3").toString(),
                       document.get("Option4").toString(),
                       document.get("RightAnswer").toString()))
                }
                setUpViewPager()
                setListener()
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { exception ->

            }
    }

    override fun onOptionClick(questionModel: QuestionModel, view: View) {
        if(clickedCond.get(binding.viewPager.currentItem)) {
            choosedAnswerList.get(binding.viewPager.currentItem).view.setBackgroundColor(getColor(R.color.option_background))
            choosedAnswerList[binding.viewPager.currentItem] = ChooseAnswerModel(view, view.tag.toString())
            Log.d("hideepesh", choosedAnswerList.toString())
            view.setBackgroundColor(getColor(R.color.choose_option_background))

        }else{
            choosedAnswerList.set(binding.viewPager.currentItem,ChooseAnswerModel(view, view.tag.toString()))
            Log.d("hideepesh", choosedAnswerList.toString())
            view.setBackgroundColor(getColor(R.color.choose_option_background))
            clickedCond.set(binding.viewPager.currentItem,true)
        }
    }


    private fun setListener(){
        binding.nextBtn.setOnClickListener{

            if(binding.nextBtn.text=="SUBMIT"){
                var score: Int = 0

                for(k in 0..4){
                    if(clickedCond[k]){
                        if(questionArrayList[k].answer == choosedAnswerList[k].chooseAnswer){
                            score ++
                        }
                    }
                }


                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("score",score.toString())
                startActivity(intent)
                finish()

            }else{
                binding.viewPager.setCurrentItem(++binding.viewPager.currentItem,true)
            }

            if(binding.viewPager.currentItem>=1) {
                binding.prevBtn.visibility = View.VISIBLE
            }
            if(binding.viewPager.currentItem==4){
                binding.nextBtn.text = "SUBMIT"
            }


        }
        binding.prevBtn.setOnClickListener{
            binding.viewPager.setCurrentItem(--binding.viewPager.currentItem,true)
            if(binding.viewPager.currentItem<=0) {
                binding.prevBtn.visibility = View.GONE
            }
            if(binding.viewPager.currentItem<=4){
                binding.nextBtn.text = "NEXT"
            }
        }
        var i =0
        val countDownTimer  = object: CountDownTimer(150000, 200){
            override fun onTick(millisUntilFinished: Long) {

                binding.timer.setProgress(i*100/735,true)
                i++
            }

            override fun onFinish() {

            }

        }.start()
    }
    private fun setUpViewPager() {
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.viewPager.setPageTransformer(compositePageTransformer)
        recyclerQuestionAdapter =  RecyclerQuestionAdapter(this,questionArrayList)
        binding.viewPager.setAdapter(recyclerQuestionAdapter)

        binding.viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position<=0){
                    binding.prevBtn.visibility = View.GONE
                }else{
                    binding.prevBtn.visibility = View.VISIBLE
                }
                if(position==4){
                    binding.nextBtn.text = "SUBMIT"
                }else{
                    //one
                    binding.nextBtn.text = "NEXT"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }



}


