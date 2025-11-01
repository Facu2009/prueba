package org.example

class Consola(private val spotify: SpotifyApiClient) {

    suspend fun run() {
        Ejecutar(
            spotify,
            "D:\\prueba\\data\\artistas.txt",
            "D:\\prueba\\data\\albumes.txt",
            "D:\\prueba\\data\\pistas.txt",
            "D:\\prueba\\data\\playlists.txt"
        ).ejecutar()
    }
}