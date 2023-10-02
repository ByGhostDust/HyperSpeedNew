package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ghostdust.hyperspeednew.LoginRepartidorActivity
import com.ghostdust.hyperspeednew.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegistrosRepaActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros_repa)

        mAuth = FirebaseAuth.getInstance()

        // Obteniendo referencias a las vistas
        val btnRegistrar = findViewById<Button>(R.id.btnregisterRepa)

        // Configurando el click listener para el botón de registro
        btnRegistrar.setOnClickListener {
            val nombre = findViewById<TextInputEditText>(R.id.Rcnombre).text.toString()
            val correo = findViewById<TextInputEditText>(R.id.Rccorreo).text.toString()
            val contraseña = findViewById<TextInputEditText>(R.id.contra).text.toString()
            val dni = findViewById<TextInputEditText>(R.id.Rdni).text.toString()
            val placaVehiculo = findViewById<TextInputEditText>(R.id.placaV).text.toString()

            // Validaciones de correo y contraseña
            if (!isEmailValid(correo)) {
                showAlertDialog("Error de Registro", "Correo electrónico inválido.")
                return@setOnClickListener
            }

            if (!isPasswordValid(contraseña)) {
                showAlertDialog("Error de Registro", "La contraseña debe tener al menos 6 caracteres.")
                return@setOnClickListener
            }

            // Llamando a la función para registrar al repartidor
            registerRepartidor(nombre, correo, contraseña, dni, placaVehiculo)
        }
    }

    // Función para validar el correo electrónico
    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    // Función para validar la contraseña
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    // Función para registrar al repartidor en Firebase Authentication y Firestore
    private fun registerRepartidor(nombre: String, correo: String, contraseña: String, dni: String, placaVehiculo: String) {
        mAuth.createUserWithEmailAndPassword(correo, contraseña)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // ...
                    // Lógica para actualizar el perfil del usuario y guardar en Firestore
                } else {
                    showAlertDialog("Error de Registro", "No se pudo crear la cuenta. Verifica tus datos.")
                }
            }
    }

    // Función para mostrar un cuadro de diálogo
    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    // Función para redirigir a la pantalla de inicio de sesión
    private fun redirectToLogin() {
        val intent = Intent(this, LoginRepartidorActivity::class.java)
        startActivity(intent)
        finish()
    }
}