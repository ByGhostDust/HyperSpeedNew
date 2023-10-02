package com.ghostdust.hyperspeednew

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CompletarFormularioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completar_formulario)

        // Obtener los componentes de la interfaz
        val editTextDireccionDestino = findViewById<EditText>(R.id.editTextDireccionDestino)
        val editTextDireccionRetiro = findViewById<EditText>(R.id.editTextDireccionRetiro)
        val editTextNumeroTelefono = findViewById<EditText>(R.id.editTextNumeroTelefono)
        val buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)

        // Botón "Finalizar"
        buttonFinalizar.setOnClickListener {
            val direccionDestino = editTextDireccionDestino.text.toString()
            val direccionRetiro = editTextDireccionRetiro.text.toString()
            val numeroTelefono = editTextNumeroTelefono.text.toString()

            // Agrega aquí la lógica para finalizar el proceso y realizar alguna acción adicional
            showCompletedMessage(direccionDestino, direccionRetiro, numeroTelefono)
        }
    }

    private fun showCompletedMessage(direccionDestino: String, direccionRetiro: String, numeroTelefono: String) {
        val message = "¡Se acaba de generar un token!\n\n" +
                "Dirección de Destino: $direccionDestino\n" +
                "Dirección de Retiro: $direccionRetiro\n" +
                "Número de Teléfono: $numeroTelefono"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Cierra esta actividad y vuelve a la actividad anterior
        finish()
    }
}
