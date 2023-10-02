package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginUsuarioActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_usuario)

        mAuth = FirebaseAuth.getInstance() // Inicializar la instancia de FirebaseAuth para autenticación con Firebase

        // Configurar el botón de inicio de sesión
        val btnLogin = findViewById<Button>(R.id.btnLoginU)
        val btnRegistro = findViewById<Button>(R.id.btnregistroU)

        btnLogin.setOnClickListener {
            // Obtener el correo electrónico y la contraseña ingresados por el usuario
            val email = findViewById<TextInputEditText>(R.id.EmailU).text.toString()
            val password = findViewById<TextInputEditText>(R.id.contraU).text.toString()

            // Verificar si el correo electrónico y la contraseña están vacíos
            if (email.isEmpty() || password.isEmpty()) {
                // Mostrar un cuadro de diálogo de error si faltan credenciales
                showAlertDialog("Error de Inicio de Sesión", "Por favor, ingresa tu correo electrónico y contraseña.")
            } else {
                // Llamar a la función para iniciar sesión con los datos proporcionados
                loginUser(email, password)
            }
        }

        btnRegistro.setOnClickListener {
            // Redirigir a la pantalla de registro de usuario al hacer clic en el botón de registro
            val intent = Intent(this, RegistroUsuarioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        // Intentar iniciar sesión con el correo electrónico y la contraseña proporcionados
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso, redirigir a com.ghostdust.hyperspeednew.PantallaPActivity
                    val intent = Intent(this, PantallaPActivity::class.java)
                    startActivity(intent)
                    finish() // Cierra la actividad de inicio de sesión para evitar que el usuario vuelva atrás
                } else {
                    // Mostrar un cuadro de diálogo de error si el inicio de sesión falla
                    showAlertDialog("Error de Inicio de Sesión", "No se pudo iniciar sesión. Por favor, verifica tus credenciales.")
                }
            }
    }

    private fun showAlertDialog(title: String, message: String) {
        // Crear y mostrar un cuadro de diálogo de alerta con el título "Aceptar"
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
}
