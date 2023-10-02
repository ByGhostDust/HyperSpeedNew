package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class perfil_UsuarioActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            // Agrega la lógica para volver a la pantalla com.ghostdust.hyperspeednew.PantallaPActivity
            val intent = Intent(this, PantallaPActivity::class.java)
            startActivity(intent)
        }

        // Obtener el ID del usuario actualmente autenticado
        val userId = mAuth.currentUser?.uid ?: ""

        // Obtener referencia al documento del usuario en Firestore
        val userDocRef = db.collection("usuarios").document(userId)

        // Mostrar los datos del usuario en los TextViews
        userDocRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val nombre = document.getString("nombre")
                val correo = document.getString("correo")
                val telefono = document.getString("telefono")

                val tvNombre = findViewById<TextView>(R.id.textViewNombre)
                val tvCorreo = findViewById<TextView>(R.id.textViewCorreo)
                val tvTelefono = findViewById<TextView>(R.id.textViewTelefono)

                tvNombre.text = "Nombre: $nombre"
                tvCorreo.text = "Correo: $correo"
                tvTelefono.text = "Teléfono: $telefono"
            }
        }
    }
}
