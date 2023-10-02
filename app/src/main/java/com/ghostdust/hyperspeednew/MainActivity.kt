package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtención de referencias a los botones de "Repartidor" y "Usuario" mediante sus IDs
        val btnRepartidor = findViewById<Button>(R.id.btnrepartidor)
        val btnUsuario = findViewById<Button>(R.id.btnusuario)

        // Configuración de un click listener para el botón "Repartidor"
        btnRepartidor.setOnClickListener {
            val intent = Intent(this, LoginRepartidorActivity::class.java)
            startActivity(intent)
        }

        // Configuración de un click listener para el botón "Usuario"
        btnUsuario.setOnClickListener {
            val intent = Intent(this, LoginUsuarioActivity::class.java)
            startActivity(intent)
        }
    }
}
