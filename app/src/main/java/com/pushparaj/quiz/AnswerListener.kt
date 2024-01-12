package com.pushparaj.quiz

import android.view.View

interface AnswerListener {

    fun onOptionClick(questionModel: QuestionModel,view: View)
}