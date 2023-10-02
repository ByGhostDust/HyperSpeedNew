package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginRepartidorActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_repartidor)

        // Inicialización de FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

        // Obtención de referencias a los botones de inicio de sesión y registro
        val btnLogin = findViewById<Button>(R.id.btnLoginR)
        val btnRegistro = findViewById<Button>(R.id.btnRregistro)

        // Configuración del listener del botón de inicio de sesión
        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.EmailR).text.toString()
            val password = findViewById<EditText>(R.id.contraR).text.toString()

            // Llamada a la función para iniciar sesión
            loginUser(email, password)
        }

        // Configuración del listener del botón de registro
        btnRegistro.setOnClickListener {
            // Redireccionamiento a la actividad de registro para repartidores
            val intent = Intent(this, RegistrosRepaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        // Inicio de sesión utilizando FirebaseAuth
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    val user = mAuth.currentUser
                    showAlertDialog("Inicio de sesión exitoso", "¡Bienvenido!")
                } else {
                    // Fallo en el inicio de sesión. Mostrar mensaje de error.
                    showAlertDialog("Inicio de sesión fallido", "Por favor, verifica tus credenciales e intenta de nuevo.")
                }
            }
    }

    private fun showAlertDialog(title: String, message: String) {
        // Creación y muestra de un cuadro de diálogo
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
}
