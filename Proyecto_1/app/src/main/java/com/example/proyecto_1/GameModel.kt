package com.example.proyecto_1

import android.text.BoringLayout
import androidx.lifecycle.ViewModel
import java.lang.Error
import kotlin.random.Random

class GameModel : ViewModel() {
    private var currentQuestion = 0
    private var hints = 5
    private val questions = listOf<Question>(
        // Deportes
        Question(1, "¿En qué deporte se utiliza una raqueta?", "Tenis", "Fútbol", "Golf", "Baloncesto", "deportes", false, false, 0),
        Question(2, "¿En qué año se celebraron los primeros Juegos Olímpicos modernos?", "1896", "1900", "1920", "1980", "deportes", false, false, 0),
        Question(3, "¿Cuál es el equipo de fútbol más exitoso de la historia?", "Real Madrid", "FC Barcelona", "Manchester United", "Bayern Munich", "deportes", false, false, 0),
        Question(4, "¿En qué deporte se utiliza un bate?", "Béisbol", "Hockey", "Críquet", "Golf", "deportes", false, false, 0),
        Question(5, "¿Cuántos jugadores conforman un equipo de balonmano en la cancha?", "7", "5", "6", "8", "deportes", false, false, 0),
        // Historia
        Question(6, "¿Quién fue el primer presidente de los Estados Unidos?", "George Washington", "Abraham Lincoln", "Thomas Jefferson", "John F. Kennedy", "historia", false, false, 0),
        Question(7, "¿En qué año comenzó la Primera Guerra Mundial?", "1914", "1939", "1945", "1918", "historia", false, false, 0),
        Question(8, "¿Cuál fue la civilización antigua que construyó las pirámides de Giza?", "Egipto", "Grecia", "Roma", "Mesopotamia", "historia", false, false, 0),
        Question(9, "¿Cuál fue la guerra de independencia de Estados Unidos?", "Guerra de Independencia", "Guerra Civil", "Primera Guerra Mundial", "Segunda Guerra Mundial", "historia", false, false, 0),
        Question(10, "¿En qué año se firmó la Declaración de Independencia de los Estados Unidos?", "1776", "1789", "1800", "1825", "historia", false, false, 0),

        // Geografía
        Question(11, "¿Cuál es el río más largo del mundo?", "Amazonas", "Nilo", "Misisipi", "Yangtsé", "geografia", false, false, 0),
        Question(12, "¿Cuál es el país más grande por área?", "Rusia", "Canadá", "Estados Unidos", "China", "geografia", false, false, 0),
        Question(13, "¿Cuál es la capital de Australia?", "Canberra", "Sídney", "Melbourne", "Brisbane", "geografia", false, false, 0),
        Question(14, "¿Cuál es el país más poblado del mundo?", "China", "India", "Estados Unidos", "Brasil", "geografia", false, false, 0),
        Question(15, "¿En qué continente se encuentra el Desierto del Sahara?", "África", "Asia", "América", "Europa", "geografia", false, false, 0),

        // Química
        Question(16, "¿Cuál es el símbolo químico del oro?", "Au", "Ag", "Fe", "Cu", "quimica", false, false, 0),
        Question(17, "¿Cuál es el elemento más abundante en la corteza terrestre?", "Oxígeno", "Silicio", "Aluminio", "Hierro", "quimica", false, false, 0),
        Question(18, "¿Quién descubrió el radio?", "Marie Curie", "Albert Einstein", "Isaac Newton", "Niels Bohr", "quimica", false, false, 0),
        Question(19, "¿Cuál es el gas más abundante en la atmósfera terrestre?", "Nitrógeno", "Oxígeno", "Hidrógeno", "Dióxido de carbono", "quimica", false, false, 0),
        Question(20, "¿Cuál es el pH neutro?", "7", "5", "10", "14", "quimica", false, false, 0),

        // Entretenimiento
        Question(21, "¿Cuál es el nombre del actor que interpreta a Iron Man en las películas de Marvel?", "Robert Downey Jr.", "Chris Hemsworth", "Chris Evans", "Mark Ruffalo", "entretenimiento", false, false, 0),
        Question(22, "En la serie 'Friends', ¿cuál es el trabajo de Chandler Bing?", "Analista de estadísticas y procesamiento de datos", "Abogado", "Actor", "Chef", "entretenimiento", false, false, 0),
        Question(23, "¿Quién es el director de la película 'Inception'?", "Christopher Nolan", "Quentin Tarantino", "Steven Spielberg", "Martin Scorsese", "entretenimiento", false, false, 0),
        Question(24, "¿Cuál es el nombre del personaje principal en la serie 'Breaking Bad'?", "Walter White", "Jesse Pinkman", "Saul Goodman", "Skyler White", "entretenimiento", false, false, 0),
        Question(35, "¿Qué banda de rock lanzó el álbum 'The Dark Side of the Moon'?", "Pink Floyd", "The Beatles", "Led Zeppelin", "Queen", "entretenimiento", false, false, 0)
    )

    //Listas que almacenan las respuestas aleatorias para cuando se regrese a la pregunta
    private var saveAnswersLOne : MutableList<String?> = MutableList(20){ null }
    private var saveAnswersLTwo : MutableList<String?> = MutableList(30){ null }
    private var saveAnswersLThree : MutableList<String?> = MutableList(40){ null }

    //Lista que guarda las preguntas seleccionadas aleatoriamente
    private var saveQuestions : MutableList<String?> = MutableList(10){ null }

    //Lista que almacena las repsuestas seleccionadas
    private var saveanswers : MutableList<String?> = MutableList(10){ null }

    //Lista que almacena las respuestas quitadas
    private var deletedAnsOne : MutableList<String?> = MutableList(20){ null }
    private var deletedAnsTwo : MutableList<String?> = MutableList(30){ null }
    private var deletedAnsThree : MutableList<String?> = MutableList(40){ null }

    //Retornar Questions
    val returnQuestionsMain
        get() = questions

    //Guardar respuestas generadas automaticamente
    fun saveAns(list: List<String>, level: Int){
        if (level == 0){
            var indiceInicio = (currentQuestion) * 2
            for (element in list){
                saveAnswersLOne[indiceInicio] = element
                indiceInicio++
            }
        } else if(level == 1){
            var indiceInicio = (currentQuestion) * 3
            for (element in list){
                saveAnswersLTwo[indiceInicio] = element
                indiceInicio++
            }
        } else {
            var indiceInicio = (currentQuestion) * 4
            for (element in list){
                saveAnswersLThree[indiceInicio] = element
                indiceInicio++
            }
        }

    }

    //Almacena la respuesta seleccionada por el usuario
    fun saveSelectAns(currentQ : Int, ans: CharSequence){
        saveanswers[currentQ] = ans.toString()
    }

    //Obtiene si la respuesta seleccionada fue correcta
    fun getSelectAns(currentQ : Int) : Boolean{
        return verifyans(saveanswers[currentQ].toString())
    }

    //Obtiene la respuesta string seleccionada
    fun getSelectAnsString(currentQ : Int) : String?{
        return saveanswers[currentQ]
    }

    //Obtiene las respuestas ya generadas
    fun getAns(level: Number) : List<String?>{
        var genAns = mutableListOf<String?>()
        if (level == 0){
            val indiceInicio = (currentQuestion) * 2
            val indiceFin = indiceInicio + 1
            for (i in indiceInicio..indiceFin){
                genAns.add(saveAnswersLOne[i])
            }
            return genAns
        }else if(level == 1){
            val indiceInicio = (currentQuestion) * 3
            val indiceFin = indiceInicio + 2
            for (i in indiceInicio..indiceFin){
                genAns.add(saveAnswersLTwo[i])
            }
            return genAns
        } else {
            val indiceInicio = (currentQuestion) * 4
            val indiceFin = indiceInicio + 3
            for (i in indiceInicio..indiceFin){
                genAns.add(saveAnswersLThree[i])
            }
            return genAns
        }

    }

    //Preguntas
    val currentQuestionText: String?
        get() = saveQuestions[currentQuestion]

    val currentQuestionId: Number
        get() = currentQuestion + 1

    val currentQuestionNum: Int
        get() = currentQuestion

    fun currentQuestionAns(): String{
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                return questions[i].answer
            }
        }
        return "a"
    }


    //-------------------Pistas------------------------

    //Obtener Hints
    fun getExtraHints(){
        hints += 1
    }

    //Obtener Numero de Hints
    val getHints: Int
        get() = hints

    //Obtiene si uso pistas
    fun getIfUsehints(): Boolean{
        var quest:Int = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        if(questions[quest].usehints > 0){return true} else {return false}
    }

    fun getNumUsedHints() : Int{
        var quest:Int = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        return questions[quest].usehints
    }

    //Pone que uso pistas
    fun usehints(){
        var quest = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        questions[quest].usehints ++
    }

    //Usar Pista
    fun usehint(level: Number) : String?{
        hints -= 1
        var answers = getAns(level)
        var list: MutableList<String?> = mutableListOf()
        var quest = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        for (i in 0 until answers.size){
            if (answers[i] != questions[quest].answer){
                list.add(answers[i])
            }
        }
        if (level == 0){
            val indiceInicio = (currentQuestion) * 2
            val indiceFinal = currentQuestion + 1
            for (i in indiceInicio until indiceFinal) {
                val answerToRemove = deletedAnsOne[i]
                list = list.filterNot { it == answerToRemove }.toMutableList()
            }
        } else if (level == 1){
            val indiceInicio = (currentQuestion) * 3
            val indiceFinal = currentQuestion + 2
            for (i in indiceInicio until indiceFinal) {
                val answerToRemove = deletedAnsTwo[i]
                list = list.filterNot { it == answerToRemove }.toMutableList()
            }
        } else {
            val indiceInicio = (currentQuestion) * 4
            val indiceFinal = currentQuestion + 3
            for (i in indiceInicio until indiceFinal) {
                val answerToRemove = deletedAnsThree[i]
                list = list.filterNot { it == answerToRemove }.toMutableList()
            }
        }
        list.shuffle()
        return list[0]
    }

    //-------------------------------------------------

    //Botones prev y next
    fun nextQuestion(){
        currentQuestion = (currentQuestion + 1) % saveQuestions.size
    }
    fun prevQuestion(){
        currentQuestion = (currentQuestion - 1 + saveQuestions.size) % saveQuestions.size
    }

    //Generacion aleatoria

    //Seleccionar 10 preguntas al Azar
    private fun generateRanQuestions() : List<Int> {
        val idList : List<Int> = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)
        return idList.shuffled().take(10)
    }

    //Save Random Questions
    fun saveRandQuestions(){
        if (saveQuestions.all { it == null }) {
            val idList = generateRanQuestions()
            var x = 0
            for (i in idList){
                saveQuestions[x] = questions[i - 1].question
                x += 1
            }
        }
    }



    //Obtener las preguntas almacenadas
    val getRandQuestions
        get() = saveQuestions

    //Numero de respuestas base dificultad
    fun numAnswers(level: Int) : List<String?>{
        val answers: List <String>
        var quest = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        if (questions[quest].haveAns){
            return getAns(level)
        }else{
            if(level == 0){
                answers = createList(2)
            } else if(level == 1){
                answers = createList(3)
            } else {
                answers = createList(4)
            }
            saveAns(answers, level)
            questions[quest].haveAns = true
            return answers
        }

    }

    //Respuestas aleatorias
    private fun createList(numans: Int) : List<String> {
        val notAns = getfakeans(numans - 1)
        val ansList = mutableListOf<String>()
        var currentQuest: String = ""
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                currentQuest = questions[i].answer
            }
        }
        ansList.add(currentQuest)
        for (i in 0 until notAns.size){
            ansList.add(notAns[i])
        }
        ansList.shuffle()
        return ansList
    }

    //Obtiene las respuestas erroneas de forma aleatoria
    private fun getfakeans(numfakes: Int): List<String>{
        var quest: Int = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id
            }
        }
        val fansList = mutableListOf<String>()
        fansList.add(questions[quest - 1].answerwone)
        fansList.add(questions[quest - 1].answerwtwo)
        fansList.add(questions[quest - 1].answerwthree)
        fansList.shuffle()
        return fansList.take(numfakes)
    }

    //Se responde
    fun markAsAnswered(){
        var quest: Int = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        questions[quest].itsAnswer = true
    }

    //Verificar status
    fun itsAswered() : Boolean{
        var quest: Int = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        return questions[quest].itsAnswer
    }

    //Verificar respuesta
    fun verifyans(answer : CharSequence) : Boolean{
        var quest = 0
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                quest = questions[i].id - 1
            }
        }
        return answer == questions[quest].answer
    }

    //Obtener Categoria
    fun getTopic() : String{
        for (i in 0 until questions.size){
            if (saveQuestions[currentQuestion] == questions[i].question){
                return questions[i].topic
            }
        }
        return "a"
    }
}