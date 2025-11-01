package org.example

import java.io.File

class LeerIdsDeArchivo(private val archivo: String) {
    fun run(): List<String> {
        val file = File(archivo)
        if (!file.exists()) {
            println("El archivo $archivo no se encuentra en la ruta especificada.")
            return emptyList()
        }
        return file.readLines()
    }
}
