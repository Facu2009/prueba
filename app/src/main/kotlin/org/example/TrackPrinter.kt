package org.example

import org.example.FormatDuration

class TrackPrinter(private val track: Track) {
    fun print() {
        println("\n" + "=".repeat(50))
        println("INFORMACION DE LA CANCION")
        println("=".repeat(50))
        println("ID: ${track.id}")
        println("Nombre: ${track.name}")
        println("Artistas: ${track.artists.joinToString(", ") { it.name }}")
        println("Album: ${track.album.name}")
        println("Duracion: ${FormatDuration(track.durationMs).run()}")
        println("Popularidad: ${track.popularity}/100")
        println("Explicito: ${if (track.explicit) "Si" else "No"}")
        println("Numero de track: ${track.trackNumber}")
        println("Preview URL: ${track.previewUrl ?: "No disponible"}")
    }
}
