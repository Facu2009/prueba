package org.example 

// importaciones necesarias para trabajar con ktor, htpp y serialización
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName // para mapear nombres de propiedades JSON
import kotlinx.serialization.Serializable // para hacer la clase serializable
import kotlinx.serialization.json.Json
import java.util.*

@Serializable // anotacion que permite convertir esta clase a/desde JSON
data class Album( // clase que representa a album
    val id: String, // id del album
    val name: String, // nombre del album
    @SerialName("album_type") val albumType: String, // tipo de album single album compilation
    val artists: List<SimpleArtist>, // artista del album, list por si son mas
    @SerialName("release_date") val releaseDate: String, // fecha de lanzamiento
    @SerialName("release_date_precision") val releaseDatePrecision: String, // fecha justa año mes dia
    @SerialName("total_tracks") val totalTracks: Int, // numero de canciones
    val genres: List<String> = emptyList(), // genero, es una lista por las dudas que sea mas de uno 
    val label: String? = null, // sello discografico
    val popularity: Int = 0, // popularidad de 0 a 100
    val images: List<Image> = emptyList(), // lista de imagen
    @SerialName("external_urls") val externalUrls: ExternalUrls? = null, // urls externas
    val tracks: TrackList? = null, // lista de canciones 
    val copyrights: List<Copyright> = emptyList() // informacion de copyright
)