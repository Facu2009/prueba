package org.example

class Consola(private val spotify: SpotifyApiClient) { // clase que maneja la consola interactiva

    suspend fun run() { // funcion que inicia la consola
        Ejecutar( // crea un objeto ejecutar con las rutas de los archivos
            spotify, // pasa el cliente de spotify
            "D:\\prueba\\data\\artistas.txt", // ruta al archivo de ids de artistas
            "D:\\prueba\\data\\albumes.txt", // ruta al archivo de ids de albumes
            "D:\\prueba\\data\\pistas.txt", // ruta al archivo de ids de pistas
            "D:\\prueba\\data\\playlists.txt" // ruta al archivo de ids de playlists
        ).ejecutar() // ejecuta el menu interactivo
    }
}