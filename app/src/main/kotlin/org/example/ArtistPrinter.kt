package org.example

class ArtistPrinter(private val artist: Artist) {
    fun print() {
        println("\n" + "=".repeat(50))
        println("INFORMACION DEL ARTISTA")
        println("=".repeat(50))
        println("ID: ${artist.id}")
        println("Nombre: ${artist.name}")
        println("Generos: ${artist.genres.joinToString(", ").ifEmpty { "No especificados" }}")
        println("Popularidad: ${artist.popularity}/100")
        println("Seguidores: ${artist.followers?.total ?: "N/A"}")
    }
}
