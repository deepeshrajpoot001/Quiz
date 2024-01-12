package com.pushparaj.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.pushparaj.quiz.databinding.ItemContainingQuestionsBinding


class RecyclerQuestionAdapter(val answerListener: AnswerListener, val questionModels: ArrayList<QuestionModel>) : RecyclerView.Adapter<RecyclerQuestionAdapter.ViewHolder>() {

    class ViewHolder(itemContainingQuestionsBinding: ItemContainingQuestionsBinding) : RecyclerView.ViewHolder(itemContainingQuestionsBinding.root){

        var binding = itemContainingQuestionsBinding

      fun setData(questionModel: QuestionModel, answerListener: AnswerListener){
          binding.questionNo.text = questionModel.quesNo
          binding.question.text = questionModel.question
          binding.option1.text = questionModel.option1
          binding.option2.text = questionModel.option2
          binding.option3.text = questionModel.option3
          binding.option4.text = questionModel.option4

          binding.option1.setOnClickListener{
              answerListener.onOptionClick(questionModel,it)
          }
          binding.option2.setOnClickListener {
              answerListener.onOptionClick(questionModel,it)
          }
          binding.option3.setOnClickListener {
              answerListener.onOptionClick(questionModel,it)
          }
          binding.option4.setOnClickListener {
              answerListener.onOptionClick(questionModel,it)
          }
      }

    }


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val itemContainingQuestionsBinding : ItemContainingQuestionsBinding =
        ItemContainingQuestionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemContainingQuestionsBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(questionModels[position],answerListener)

    }

    override fun getItemCount(): Int {
        return questionModels.size

    }


}