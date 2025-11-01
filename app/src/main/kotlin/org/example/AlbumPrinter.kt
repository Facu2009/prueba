package org.example

class AlbumPrinter(private val album: Album) {
    fun print() {
        println("\n" + "=".repeat(50))
        println("INFORMACION DEL ALBUM")
        println("=".repeat(50))
        println("ID: ${album.id}")
        println("Nombre: ${album.name}")
        println("Artistas: ${album.artists.joinToString(", ") { it.name }}")
        println("Tipo: ${album.albumType}")
        println("Fecha de lanzamiento: ${album.releaseDate}")
        println("Total de tracks: ${album.totalTracks}")
        println("Popularidad: ${album.popularity}/100")
        println("Sello discografico: ${album.label ?: "No especificado"}")
    }
}
