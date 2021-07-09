package ordunio.maribel.misnotas

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import java.io.File


class AdaptadorNotas() : BaseAdapter() {
    lateinit var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>) : this() {
        this.context = context
        this.notas = notas
    }


    override fun getCount(): Int {
       return notas.size
    }

    override fun getItem(position: Int): Any {
        return notas[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[position]

        var tv_titulo: TextView = vista.findViewById(R.id.tv_titulo_det)
        var tv_contenido: TextView = vista.findViewById(R.id.tv_contenido_det)
        var btn_borrar: Button = vista.findViewById(R.id.btn_borrar)

        tv_contenido.text = nota.contenido
        tv_titulo.text = nota.titulo

        btn_borrar.setOnClickListener {
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista

    }

    private fun eliminar(titulo: String) {
        if (titulo == "") {
            Toast.makeText(context, "Error: titulo vacio", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val archivo = File(ubicacion(), titulo + ".txt")
                archivo.delete()

                Toast.makeText(context, "Se eliminó el archivo", Toast.LENGTH_SHORT).show()
            }

            catch (e: Exception) {
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): String {
        val album = File(Environment.getExternalStorageDirectory(), "notas")
        if (!album.exists()) {
            album.mkdir()
        }
        return album.absolutePath
    }


}