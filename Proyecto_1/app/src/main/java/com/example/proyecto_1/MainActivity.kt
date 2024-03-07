package com.example.proyecto_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button
import android.view.View
import android.widget.AdapterView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val dificultad = arrayOf("FÁCIL", "NORMAL", "DIFÍCIL")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val btnOpciones = findViewById<Button>(R.id.btnOpciones)
        val spinnerDificultad = findViewById<Spinner>(R.id.spinnerDificultad)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dificultad)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDificultad.adapter = adapter
        spinnerDificultad.onItemSelectedListener = this


        btnJugar.setOnClickListener {
            var dificultadNum = 0
            if(dificultad[spinnerDificultad.selectedItemPosition] == "FÁCIL"){
                dificultadNum = 0
            } else if(dificultad[spinnerDificultad.selectedItemPosition] == "NORMAL") {
                dificultadNum = 1
            } else{
                dificultadNum = 2
            }
            val intent = Intent(this, in_game::class.java)
            intent.putExtra("dificultad", dificultadNum)
            startActivity(intent)
        }

        btnOpciones.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Maneja la situación en la que no se selecciona nada en el Spinner
    }

}