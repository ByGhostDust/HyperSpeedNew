package com.ghostdust.hyperspeednew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class PantallaPActivity : AppCompatActivity() {
    private lateinit var editlongitud: EditText
    private lateinit var editaltura: EditText
    private lateinit var editancho: EditText
    private lateinit var editpeso: EditText
    private lateinit var btnCalcularTarifa: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnPerfil: ImageButton
    private lateinit var textViewResultado: TextView
    private lateinit var btncompletarF: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_pactivity)

        // Asignar los EditText y el botón mediante findViewById
        editlongitud = findViewById(R.id.editlongitud)
        editaltura = findViewById(R.id.editaltura)
        editancho = findViewById(R.id.editancho)
        editpeso = findViewById(R.id.editpeso)
        btnCalcularTarifa = findViewById(R.id.btncalcular)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnPerfil = findViewById(R.id.btnPerfil)
        textViewResultado = findViewById(R.id.textViewResultado)
        btncompletarF = findViewById(R.id.btncompletarF)
        btnCalcularTarifa.setOnClickListener {
            calcularTarifaEnvio()
        }

        // Configurar el onClickListener para el botón "Cancelar"
        btnCancelar.setOnClickListener {
            limpiarCampos()
        }

        // Configurar el onClickListener para el botón "Perfil"
        btnPerfil.setOnClickListener {
            irAPerfil()
        }
        btncompletarF.setOnClickListener {
            CompletarFormulario()
        }
    }

    private fun limpiarCampos() {
        // Limpiar los campos de entrada y el resultado
        editlongitud.text.clear()
        editancho.text.clear()
        editaltura.text.clear()
        editpeso.text.clear()
        textViewResultado.text = ""
    }

    private fun calcularTarifaEnvio() {
        val longitud = editlongitud.text.toString().toDoubleOrNull() ?: 0.0
        val ancho = editancho.text.toString().toDoubleOrNull() ?: 0.0
        val altura = editaltura.text.toString().toDoubleOrNull() ?: 0.0
        val peso = editpeso.text.toString().toDoubleOrNull() ?: 0.0

        val resultado = (longitud + ancho + altura) * peso

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        val tarifaFormateada = numberFormat.format(resultado)

        textViewResultado.text = "Tarifa de Envío: $tarifaFormateada"
    }

    private fun CompletarFormulario() {
        val intent = Intent(this, CompletarFormularioActivity::class.java)
        startActivity(intent)
    }
    private fun irAPerfil() {
        val intent = Intent(this, perfil_UsuarioActivity::class.java)
        startActivity(intent)
    }

}