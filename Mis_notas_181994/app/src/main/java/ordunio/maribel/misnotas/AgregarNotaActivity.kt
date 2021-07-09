package ordunio.maribel.misnotas

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Manifest

class AgregarNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        var btn_guardar: Button= findViewById(R.id.btn_guardar) as Button

        btn_guardar.setOnClickListener {

        }

    }

    fun guardarNota(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235)


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            235 ->
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    guardar()
                else{
                    Toast.makeText(this,"Error:permisos denegados", Toast.LENGTH_SHORT).show()
                }
        }
    }

    public fun guardar() {
        val et_titulo: EditText = findViewById(R.id.et_titulo) as EditText
        val et_contenido: EditText = findViewById(R.id.et_contenido) as EditText

        var titulo = et_titulo.text.toString()
        var cuerpo = et_contenido.text.toString()

        if (titulo == "" || cuerpo == "") {
            Toast.makeText(this, "Error: campos vacios", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val archivo = File(ubicacion(), titulo + ".txt")
                val fos = FileOutputStream(archivo)

                fos.write(cuerpo.toByteArray())
                fos.close()

                Toast.makeText(this, "Se guardó el archivo en la carpeta pública", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                Toast.makeText(this, "Error: no se guardó el archivo", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun ubicacion(): String {
        val carpeta = File(Environment.getExternalStorageDirectory(), "notas")
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }

        return carpeta.absolutePath
    }
}