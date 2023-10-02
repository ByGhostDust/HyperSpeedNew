package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegistroUsuarioActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        mAuth = FirebaseAuth.getInstance()

        // Obtener referencia al botón de registro
        val btnRegistrar = findViewById<Button>(R.id.btregister)

        btnRegistrar.setOnClickListener {
            // Obtener los valores ingresados por el usuario
            val nombre = findViewById<TextInputEditText>(R.id.Unombre).text.toString()
            val correo = findViewById<TextInputEditText>(R.id.Ucorreo).text.toString()
            val contraseña = findViewById<TextInputEditText>(R.id.Ucontra).text.toString()
            val dni = findViewById<TextInputEditText>(R.id.Udni).text.toString()

            // Validar el formato del correo electrónico
            if (!isEmailValid(correo)) {
                showAlertDialog("Error de Registro", "Correo electrónico inválido.")
                return@setOnClickListener
            }

            // Validar la longitud de la contraseña
            if (!isPasswordValid(contraseña)) {
                showAlertDialog("Error de Registro", "La contraseña debe tener al menos 6 caracteres.")
                return@setOnClickListener
            }

            // Llamar a la función para registrar al usuario
            registerUser(nombre, correo, contraseña, dni)
        }
    }

    // Función para validar el correo electrónico
    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    // Función para validar  la contraseña
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    // Función para registrar al usuario en Firebase Authentication y Firestore
    private fun registerUser(nombre: String, correo: String, contraseña: String, dni: String) {
        mAuth.createUserWithEmailAndPassword(correo, contraseña)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Obtener el usuario actualmente autenticado
                    val user = mAuth.currentUser
                    // Crear una solicitud de cambio de perfil para actualizar el nombre del usuario
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(nombre)
                        .build()

                    // Actualizar el perfil del usuario con el nombre
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Crear un mapa de datos para el usuario
                                val userMap = hashMapOf(
                                    "nombre" to nombre,
                                    "correo" to correo,
                                    "dni" to dni
                                )

                                // Obtener instancia de Firestore y agregar los datos del usuario
                                val db = FirebaseFirestore.getInstance()
                                db.collection("usuarios")
                                    .document(user?.uid ?: "")
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        // Mostrar un cuadro de diálogo de éxito y redirigir a la pantalla de inicio de sesión
                                        showAlertDialog("Registro Exitoso", "Cuenta creada exitosamente.")
                                        redirectToLogin()
                                    }
                                    .addOnFailureListener {
                                        // Mostrar un cuadro de diálogo de error si falla al agregar datos a Firestore
                                        showAlertDialog("Error de Registro", "No se pudo crear el documento de usuario.")
                                    }
                            } else {
                                // Mostrar un cuadro de diálogo de error si falla al actualizar el perfil del usuario
                                showAlertDialog("Error de Registro", "No se pudo actualizar el perfil del usuario.")
                            }
                        }
                } else {
                    // Mostrar un cuadro de diálogo de error si falla el registro del usuario
                    showAlertDialog("Error de Registro", "No se pudo crear la cuenta. Verifica tus datos.")
                }
            }
    }

    // Función para mostrar un cuadro de diálogo de alerta
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
        val intent = Intent(this, LoginUsuarioActivity::class.java)
        startActivity(intent)
        finish()
    }
}
