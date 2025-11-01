package org.example

class Autenticacion {

    private val clientId = "43551abad28b4f9290ed67904ee20f5e"
    private val clientSecret = "dd2408b1ccae4bdca9fd71735f6649eb"

    suspend fun authenticate(): SpotifyApiClient {
        val spotify = SpotifyApiClient(clientId, clientSecret)
        require(spotify.authenticate()) { "Error: no se pudo autenticar con Spotify" }
        return spotify
    }
}
