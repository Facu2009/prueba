package org.example

import org.example.FormatDuration // importa clase para formatear duracion
import io.ktor.server.application.* // importa clases del servidor
import io.ktor.server.engine.* // importa motor del servidor
import io.ktor.server.netty.* // importa implementacion netty
import io.ktor.server.response.* // importa funciones para responder
import io.ktor.server.routing.* // importa funciones para definir rutas
import io.ktor.http.* // importa tipos http

fun startWebServer(spotifyClient: SpotifyApiClient) { // funcion que inicia el servidor web
    embeddedServer(Netty, port = 9090) { // crea servidor web con netty en puerto 8080
        routing { // define las rutas del servidor
            
            // Página principal
            get("/") { // ruta raiz, pagina principal
                val html = leerArchivo("static/index.html") // lee el archivo html de inicio
                call.respondText(html, ContentType.Text.Html) // responde con el html
            }

            // API: Buscar Artista por nombre
            get("/api/artista") { // ruta para buscar artista
                val nombre = call.request.queryParameters["nombre"] ?: "" // obtiene el parametro nombre de la url, o string vacio si no existe
                
                if (nombre.isBlank()) { // si el nombre esta vacio
                    call.respondText("Error: Nombre requerido", status = HttpStatusCode.BadRequest) // responde con error 400
                    return@get // sale de la funcion
                }
                
                // Buscar en la base de datos
                val spotifyId = Database.buscarArtistaPorNombre(nombre) // busca el id en la base de datos
                
                if (spotifyId == null) { // si no se encuentra
                    call.respondText("Artista '$nombre' no encontrado en la base de datos", status = HttpStatusCode.NotFound) // responde con error 404
                    return@get // sale de la funcion
                }
                
                // Consultar en Spotify API
                val artist = spotifyClient.getArtist(spotifyId) // consulta el artista en spotify
                
                if (artist == null) { // si hay error al consultar
                    call.respondText("Error al consultar Spotify", status = HttpStatusCode.InternalServerError) // responde con error 500
                    return@get // sale de la funcion
                }
                
                val html = leerArchivo("static/artista.html") // lee la plantilla html
                    .replace("{{nombre}}", artist.name) // reemplaza el placeholder con el nombre
                    .replace("{{id}}", artist.id) // reemplaza el placeholder con el id
                    .replace("{{generos}}", artist.genres.joinToString(", ").ifEmpty { "No especificados" }) // reemplaza con generos
                    .replace("{{popularidad}}", artist.popularity.toString()) // reemplaza con popularidad
                    .replace("{{seguidores}}", artist.followers?.total?.toString() ?: "N/A") // reemplaza con seguidores
                    .replace("{{url}}", artist.externalUrls?.spotify ?: "#") // reemplaza con url de spotify
                
                call.respondText(html, ContentType.Text.Html) // responde con el html completado
            }

            // API: Buscar Track por nombre
            get("/api/track") { // ruta para buscar cancion
                val nombre = call.request.queryParameters["nombre"] ?: "" // obtiene el parametro nombre
                
                if (nombre.isBlank()) { // si el nombre esta vacio
                    call.respondText("Error: Nombre requerido", status = HttpStatusCode.BadRequest) // responde con error 400
                    return@get // sale
                }
                
                val spotifyId = Database.buscarCancionPorNombre(nombre) // busca el id en la base de datos
                
                if (spotifyId == null) { // si no se encuentra
                    call.respondText("Canción '$nombre' no encontrada en la base de datos", status = HttpStatusCode.NotFound) // responde con error 404
                    return@get // sale
                }
                
                val track = spotifyClient.getTrack(spotifyId) // consulta la cancion en spotify
                
                if (track == null) { // si hay error
                    call.respondText("Error al consultar Spotify", status = HttpStatusCode.InternalServerError) // responde con error 500
                    return@get // sale
                }
                
                val html = leerArchivo("static/track.html") // lee la plantilla html
                    .replace("{{nombre}}", track.name) // reemplaza con nombre
                    .replace("{{id}}", track.id) // reemplaza con id
                    .replace("{{artistas}}", track.artists.joinToString(", ") { it.name }) // reemplaza con artistas
                    .replace("{{album}}", track.album.name) // reemplaza con album
                    .replace("{{duracion}}", FormatDuration(track.durationMs).run()) // reemplaza con duracion formateada
                    .replace("{{popularidad}}", track.popularity.toString()) // reemplaza con popularidad
                    .replace("{{explicito}}", if (track.explicit) "Sí" else "No") // reemplaza con si es explicito
                    .replace("{{preview}}", track.previewUrl ?: "") // reemplaza con url de preview
                
                call.respondText(html, ContentType.Text.Html) // responde con el html completado
            }

            // API: Buscar Álbum por nombre
            get("/api/album") { // ruta para buscar album
                val nombre = call.request.queryParameters["nombre"] ?: "" // obtiene el parametro nombre
                
                if (nombre.isBlank()) { // si el nombre esta vacio
                    call.respondText("Error: Nombre requerido", status = HttpStatusCode.BadRequest) // responde con error 400
                    return@get // sale
                }
                
                val spotifyId = Database.buscarAlbumPorNombre(nombre) // busca el id en la base de datos
                
                if (spotifyId == null) { // si no se encuentra
                    call.respondText("Álbum '$nombre' no encontrado en la base de datos", status = HttpStatusCode.NotFound) // responde con error 404
                    return@get // sale
                }
                
                val album = spotifyClient.getAlbum(spotifyId) // consulta el album en spotify
                
                if (album == null) { // si hay error
                    call.respondText("Error al consultar Spotify", status = HttpStatusCode.InternalServerError) // responde con error 500
                    return@get // sale
                }
                
                val html = leerArchivo("static/album.html") // lee la plantilla html
                    .replace("{{nombre}}", album.name) // reemplaza con nombre
                    .replace("{{id}}", album.id) // reemplaza con id
                    .replace("{{artistas}}", album.artists.joinToString(", ") { it.name }) // reemplaza con artistas
                    .replace("{{tipo}}", album.albumType) // reemplaza con tipo
                    .replace("{{fecha}}", album.releaseDate) // reemplaza con fecha
                    .replace("{{tracks}}", album.totalTracks.toString()) // reemplaza con cantidad de canciones
                    .replace("{{popularidad}}", album.popularity.toString()) // reemplaza con popularidad
                    .replace("{{sello}}", album.label ?: "No especificado") // reemplaza con sello discografico
                
                call.respondText(html, ContentType.Text.Html) // responde con el html completado
            }

            // API: Buscar Playlist por nombre
            get("/api/playlist") { // ruta para buscar playlist
                val nombre = call.request.queryParameters["nombre"] ?: "" // obtiene el parametro nombre
                
                if (nombre.isBlank()) { // si el nombre esta vacio
                    call.respondText("Error: Nombre requerido", status = HttpStatusCode.BadRequest) // responde con error 400
                    return@get // sale
                }
                
                val spotifyId = Database.buscarPlaylistPorNombre(nombre) // busca el id en la base de datos
                
                if (spotifyId == null) { // si no se encuentra
                    call.respondText("Playlist '$nombre' no encontrada en la base de datos", status = HttpStatusCode.NotFound) // responde con error 404
                    return@get // sale
                }
                
                val playlist = spotifyClient.getPlaylist(spotifyId) // consulta la playlist en spotify
                
                if (playlist == null) { // si hay error
                    call.respondText("Error al consultar Spotify", status = HttpStatusCode.InternalServerError) // responde con error 500
                    return@get // sale
                }
                
                val html = leerArchivo("static/playlist.html") // lee la plantilla html
                    .replace("{{nombre}}", playlist.name) // reemplaza con nombre
                    .replace("{{id}}", playlist.id) // reemplaza con id
                    .replace("{{descripcion}}", playlist.description ?: "Sin descripción") // reemplaza con descripcion
                    .replace("{{creador}}", playlist.owner.displayName ?: playlist.owner.id) // reemplaza con creador
                    .replace("{{publica}}", when (playlist.public) { // reemplaza segun si es publica
                        true -> "Sí"
                        false -> "No"
                        null -> "No especificado"
                    })
                    .replace("{{colaborativa}}", if (playlist.collaborative) "Sí" else "No") // reemplaza si es colaborativa
                    .replace("{{seguidores}}", playlist.followers.total.toString()) // reemplaza con seguidores
                    .replace("{{canciones}}", playlist.tracks.total.toString()) // reemplaza con cantidad de canciones
                
                call.respondText(html, ContentType.Text.Html) // responde con el html completado
            }
        }
        
        println("\n" + "=".repeat(70)) // imprime linea de separacion
        println("🌐 Servidor web iniciado en: http://localhost:9090") // imprime mensaje de inicio
        println("=".repeat(70)) // imprime linea de separacion
    }.start(wait = true) // inicia el servidor y espera
}

fun leerArchivo(ruta: String): String { // funcion que lee un archivo del directorio resources
    return object {}.javaClass.classLoader // obtiene el classloader
        .getResourceAsStream(ruta) // abre el archivo como stream
        ?.bufferedReader() // lo convierte en buffered reader
        ?.readText() // lee todo el texto
        ?: "Archivo no encontrado: $ruta" // si no existe devuelve mensaje de error
}