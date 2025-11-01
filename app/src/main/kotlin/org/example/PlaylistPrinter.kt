package org.example

class PlaylistPrinter(private val playlist: Playlist) {
    fun print() {
        println("\n" + "=".repeat(50))
        println("INFORMACION DE LA PLAYLIST")
        println("=".repeat(50))
        println("ID: ${playlist.id}")
        println("Nombre: ${playlist.name}")
        println("Descripcion: ${playlist.description ?: "Sin descripcion"}")
        println("Creada por: ${playlist.owner.displayName ?: playlist.owner.id}")
        println("Publica: ${if (playlist.public == true) "Si" else if (playlist.public == false) "No" else "No especificado"}")
        println("Colaborativa: ${if (playlist.collaborative) "Si" else "No"}")
        println("Seguidores: ${playlist.followers.total}")
        println("Total de canciones: ${playlist.tracks.total}")
    }
}
