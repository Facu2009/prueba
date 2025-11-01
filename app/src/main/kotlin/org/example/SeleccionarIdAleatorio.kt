package org.example

class SeleccionarIdAleatorio(private val archivo: String) {
    fun run(): String? {
        val ids = LeerIdsDeArchivo(archivo).run()
        return if (ids.isNotEmpty()) ids.random() else null
    }
}
