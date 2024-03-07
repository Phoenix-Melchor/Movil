package com.example.proyecto_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import android.widget.ImageView


class in_game : AppCompatActivity() {
    private lateinit var current_question: TextView
    private lateinit var img_topic: ImageView
    private lateinit var question: TextView
    private lateinit var answerone: Button
    private lateinit var answertwo: Button
    private lateinit var answerthree: Button
    private lateinit var answerfour: Button
    private lateinit var prev_button: Button
    private lateinit var next_button: Button
    private var questionsAnsweredCorrect = 0
    private lateinit var hint_button: Button
    private lateinit var score: TextView
    private var score_num = 0
    private lateinit var useHint: TextView
    private var numAnsLeft = 0
    private var numHintsUsed = 0

    private val gameModel: GameModel by viewModels()

    //-------------------Falta agregar lo de dificultad que viene en el main menu ------------------------------------------
    private var level = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_game)

        val intent = intent
        if (intent.hasExtra("dificultad")) {
            level = intent.getIntExtra("dificultad", 0)
        }

        savedInstanceState?.let {
            questionsAnsweredCorrect = it.getInt("questionsAnsweredCorrect", 0)
            score_num = it.getInt("score_num", 0)
            numAnsLeft = it.getInt("numAnsLeft", 0)
            numHintsUsed = it.getInt("numHintsUsed", 0)
            level = it.getInt("level", 1)

            // Puedes agregar más variables según sea necesario
        }

        current_question = findViewById(R.id.current_question)
        img_topic = findViewById(R.id.img_topic)
        question = findViewById(R.id.question_box)
        prev_button = findViewById(R.id.prev)
        next_button = findViewById(R.id.next)
        answerone = findViewById(R.id.answer_one)
        answertwo = findViewById(R.id.answer_two)
        answerthree = findViewById(R.id.answer_three)
        answerfour = findViewById(R.id.answer_four)
        score = findViewById(R.id.score)
        hint_button = findViewById(R.id.hints)
        useHint = findViewById(R.id.usehint)

        //Numero de respuestas restantes
        if (level == 0){
            numAnsLeft = 2
        }else if (level == 1){
            numAnsLeft = 3
        }else if (level == 2){
            numAnsLeft = 4
        }

        //Obtener Preguntas
        gameModel.saveRandQuestions()

        //Cambia la imagen dependiendo de la categoria
        changeImg()
        //Numero de la pregunta
        current_question.text = gameModel.currentQuestionId.toString()

        //Funcionamiento pregunta
        question.text = gameModel.currentQuestionText

        //Numero de respuestas
        updateAns()

        //Verificacion Respuesta
        answerone.setOnClickListener { _ ->
            if (answerone.isClickable){
                buttonFunction(answerone)
            }
        }

        answertwo.setOnClickListener { _ ->
            if(answertwo.isClickable){
                buttonFunction(answertwo)
            } else {
                var selectedAns = gameModel.getSelectAns(gameModel.currentQuestionNum)
                putdetails(answertwo, selectedAns)
            }
        }
        answerthree.setOnClickListener { _ ->
            if(answerthree.isClickable){
                buttonFunction(answerthree)
            }
        }
        answerfour.setOnClickListener { _ ->
            if (answerfour.isClickable){
                buttonFunction(answerfour)
            }
        }

        hint_button.setOnClickListener{_ ->
            if (gameModel.getHints > 0){
                gameModel.usehints()
                numHintsUsed += 1
                if (numAnsLeft == 2){
                    gameModel.usehint(level)
                    if (answerone.text == gameModel.currentQuestionAns()){
                        buttonFunction(answerone)
                    } else if (answertwo.text == gameModel.currentQuestionAns()){
                        buttonFunction(answertwo)
                    } else if (answerthree.text == gameModel.currentQuestionAns()){
                        buttonFunction(answerthree)
                    } else if (answerfour.text == gameModel.currentQuestionAns()){
                        buttonFunction(answerfour)
                    }
                    println("primer if hint")
                    println(gameModel.getNumUsedHints())
                    println(numAnsLeft)
                } else {
                    numAnsLeft --
                    var hintAns = gameModel.usehint(level)
                    if (answerone.text == hintAns){
                        putColor(answerone, 2)
                        answerone.isClickable = false
                    } else if (answertwo.text == hintAns){
                        putColor(answertwo, 2)
                        answertwo.isClickable = false
                    } else if (answerthree.text == hintAns){
                        putColor(answerthree, 2)
                        answerthree.isClickable = false
                    } else if (answerfour.text == hintAns){
                        putColor(answerfour, 2)
                        answerfour.isClickable = false
                    }
                    println("segundo if hint")
                    println(gameModel.getNumUsedHints())
                    println(numAnsLeft)
                }
                hint_button.text = "Hints: ${gameModel.getHints}"
                useHint.visibility = View.VISIBLE

            } else {
                //Aqui va una advertencia de que ya no hay hints------------------------------------------------------
            }

        }
        if(!gameModel.itsAswered()){
            isClickable()
            if (gameModel.getIfUsehints()){
                useHint.visibility = View.VISIBLE

            } else {
                useHint.visibility = View.INVISIBLE
            }
        } else {
            noClickable()
            val selectedAns = gameModel.getSelectAns(gameModel.currentQuestionNum)
            putdetails(answerone, selectedAns)
            putdetails(answertwo, selectedAns)
            putdetails(answerthree, selectedAns)
            putdetails(answerfour, selectedAns)
            if (gameModel.getIfUsehints()) {
                useHint.visibility = View.VISIBLE
            } else {
                useHint.visibility = View.INVISIBLE
            }
        }
        hint_button.text = "Hints: ${gameModel.getHints}"
        //Funcionamiento botones prev y next
        prev_button.setOnClickListener { _ ->
            gameModel.prevQuestion()
            changeImg()
            question.text = gameModel.currentQuestionText
            updateAns()
            current_question.text = gameModel.currentQuestionId.toString()
            returnColor()
            if (level == 1){
                if (gameModel.getNumUsedHints() == 1){
                    numAnsLeft = 2
                    println("level one primer if")
                } else if (gameModel.getNumUsedHints() == 0){
                    numAnsLeft = 3
                    println("level one segundo if")
                }
            }else if (level == 2){
                if (gameModel.getNumUsedHints() == 1){
                    numAnsLeft = 3
                }else if(gameModel.getNumUsedHints() == 2){
                    numAnsLeft = 2
                } else if(gameModel.getNumUsedHints() == 0){
                    numAnsLeft = 4
                }
            }
            if(!gameModel.itsAswered()){
                isClickable()
                if (gameModel.getIfUsehints()){
                    useHint.visibility = View.VISIBLE

                } else {
                    useHint.visibility = View.INVISIBLE
                }
            } else {
                noClickable()
                val selectedAns = gameModel.getSelectAns(gameModel.currentQuestionNum)
                putdetails(answerone, selectedAns)
                putdetails(answertwo, selectedAns)
                putdetails(answerthree, selectedAns)
                putdetails(answerfour, selectedAns)
                if (gameModel.getIfUsehints()){
                    useHint.visibility = View.VISIBLE
                } else {
                    useHint.visibility = View.INVISIBLE
                }
            }

        }
        next_button.setOnClickListener { _ ->
            gameModel.nextQuestion()
            changeImg()
            question.text = gameModel.currentQuestionText
            updateAns()
            current_question.text = gameModel.currentQuestionId.toString()
            returnColor()
            if (level == 1){
                if (gameModel.getNumUsedHints() == 1){
                    numAnsLeft = 2
                    println("level one primer if")
                } else if (gameModel.getNumUsedHints() == 0){
                    numAnsLeft = 3
                    println("level one segundo if")
                }
            }else if (level == 2){
                if (gameModel.getNumUsedHints() == 1){
                    numAnsLeft = 3
                }else if(gameModel.getNumUsedHints() == 2){
                    numAnsLeft = 2
                } else if(gameModel.getNumUsedHints() == 0){
                    numAnsLeft = 4
                }
            }
            if(!gameModel.itsAswered()){
                isClickable()
                if (gameModel.getIfUsehints()){
                    useHint.visibility = View.VISIBLE
                } else {
                    useHint.visibility = View.INVISIBLE
                }
            } else {
                noClickable()
                val selectedAns = gameModel.getSelectAns(gameModel.currentQuestionNum)
                putdetails(answerone, selectedAns)
                putdetails(answertwo, selectedAns)
                putdetails(answerthree, selectedAns)
                putdetails(answerfour, selectedAns)
                if (gameModel.getIfUsehints()){
                    useHint.visibility = View.VISIBLE
                } else {
                    useHint.visibility = View.INVISIBLE
                }
            }
            println("next hint")
            println(gameModel.getNumUsedHints())
            println(numAnsLeft)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Guardar variables importantes
        outState.putInt("questionsAnsweredCorrect", questionsAnsweredCorrect)
        outState.putInt("score_num", score_num)
        outState.putInt("numAnsLeft", numAnsLeft)
        outState.putInt("numHintsUsed", numHintsUsed)
        outState.putInt("level", level)

    }


    private fun buttonFunction(button: Button){
        if (gameModel.verifyans(button.text)){
            putColor(button, 1)
            noClickable()
            gameModel.markAsAnswered()
            gameModel.saveSelectAns(gameModel.currentQuestionNum, button.text)

            //--------------Terminar--------------------------------
            if (gameModel.getIfUsehints()){
                score_num += 50
                questionsAnsweredCorrect = 0
            } else {
                score_num += 100
                questionsAnsweredCorrect += 1
            }
            score.text = score_num.toString()
            if (questionsAnsweredCorrect == 2) {
                gameModel.getExtraHints()
                questionsAnsweredCorrect = 0
                hint_button.text = "Hints: ${gameModel.getHints}"
            }
        } else {
            putColor(button, 2)
            noClickable()
            gameModel.markAsAnswered()
            gameModel.saveSelectAns(gameModel.currentQuestionNum, button.text)
            questionsAnsweredCorrect = 0
        }
    }

    private fun putColor(button: Button, option : Int){
        if (option == 1){
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun putdetails(button: Button, ansSelected : Boolean){
        if (button.text == gameModel.getSelectAnsString(gameModel.currentQuestionNum) && ansSelected){
            putColor(button, 1)
        }
        else if (button.text == gameModel.getSelectAnsString(gameModel.currentQuestionNum) && !ansSelected){
            putColor(button, 2)
        }
    }

    private fun updateAns() {
        var ansList = gameModel.numAnswers(level)
        if (level == 0) {
            answerone.text = ansList[0]
            answertwo.text = ansList[1]
            answerthree.visibility = View.INVISIBLE
            answerfour.visibility = View.INVISIBLE
        } else if (level == 1) {
            answerone.text = ansList[0]
            answertwo.text = ansList[1]
            answerthree.text = ansList[2]
            answerfour.visibility = View.INVISIBLE
        } else {
            answerone.text = ansList[0]
            answertwo.text = ansList[1]
            answerthree.text = ansList[2]
            answerfour.text = ansList[3]
        }
    }

    private fun changeImg(){
        if (gameModel.getTopic() == "deportes"){img_topic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sports))}
        if (gameModel.getTopic() == "geografia"){img_topic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.geography))}
        if (gameModel.getTopic() == "historia"){img_topic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.history))}
        if (gameModel.getTopic() == "quimica"){img_topic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.chemistry))}
        if (gameModel.getTopic() == "entretenimiento"){img_topic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.entretenimiento))}
    }

    private fun noClickable(){
        answerone.isClickable = false
        answertwo.isClickable = false
        answerthree.isClickable = false
        answerfour.isClickable = false
        hint_button.isClickable = false
    }

    private fun isClickable(){
        answerone.isClickable = true
        answertwo.isClickable = true
        answerthree.isClickable = true
        answerfour.isClickable = true
        hint_button.isClickable = true
    }

    private fun returnColor(){
        answerone.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        answerone.setTextColor(ContextCompat.getColor(this, R.color.gray))
        answertwo.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        answertwo.setTextColor(ContextCompat.getColor(this, R.color.gray))
        answerthree.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        answerthree.setTextColor(ContextCompat.getColor(this, R.color.gray))
        answerfour.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        answerfour.setTextColor(ContextCompat.getColor(this, R.color.gray))
    }
}