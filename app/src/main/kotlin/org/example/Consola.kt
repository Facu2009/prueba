package org.example

class Consola(private val spotify: SpotifyApiClient) {

    suspend fun run() {
        Ejecutar(
            spotify.clientId,
            spotify.clientSecret,
            "C:\\programaci-n-2-2025-gimenez-rodriguez-spotify-gaspatacufa\\data\\artistas.txt",
            "C:\\programaci-n-2-2025-gimenez-rodriguez-spotify-gaspatacufa\\data\\albumes.txt",
            "C:\\programaci-n-2-2025-gimenez-rodriguez-spotify-gaspatacufa\\data\\pistas.txt",
            "C:\\programaci-n-2-2025-gimenez-rodriguez-spotify-gaspatacufa\\data\\playlists.txt"
        ).ejecutar()
    }
}
