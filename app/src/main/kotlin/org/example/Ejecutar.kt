package org.example

import java.io.File
import java.util.Scanner

data class Ejecutar(
    val spotifyClient: SpotifyApiClient,
    val archivoArtistas: String,
    val archivoAlbumes: String,
    val archivoPistas: String,
    val archivoPlaylists: String
) {
    private val scanner = Scanner(System.`in`)
    
    suspend fun ejecutar() {
        try {
            println("\n" + "=".repeat(70))
            println("BIENVENIDO A LA CONSOLA DE SPOTIFY")
            println("=".repeat(70))

            var continuar = true
            while (continuar) {
                mostrarMenu()
                
                print("\nSelecciona una opción: ")
                val input = scanner.nextLine().trim()
                val opcion = input.toIntOrNull()

                when (opcion) {
                    1 -> consultarArtista()
                    2 -> consultarTrack()
                    3 -> consultarAlbum()
                    4 -> consultarPlaylist()
                    5 -> consultarTodoAleatorio()
                    6 -> {
                        println("\n¡Hasta luego!")
                        continuar = false
                    }
                    else -> println("\n❌ Opción inválida. Por favor, selecciona un número del 1 al 6.")
                }

                if (continuar && opcion != null) {
                    println("\n" + "-".repeat(70))
                    print("Presiona ENTER para continuar...")
                    scanner.nextLine()
                }
            }

        } catch (e: Exception) {
            println("❌ Error general: ${e.message}")
            e.printStackTrace()
        } finally {
            scanner.close()
            spotifyClient.close()
        }
    }

    private fun mostrarMenu() {
        println("\n╔════════════════════════════════════════════════════════════════════╗")
        println("║                         MENÚ PRINCIPAL                             ║")
        println("╚════════════════════════════════════════════════════════════════════╝")
        println("  1. 🎤 Consultar Artista")
        println("  2. 🎵 Consultar Canción/Track")
        println("  3. 💿 Consultar Álbum")
        println("  4. 📀 Consultar Playlist")
        println("  5. 🎲 Consultar Todo (Aleatorio)")
        println("  6. 🚪 Salir")
    }

    private fun cargarIds(archivo: String): List<String> {
        return try {
            File(archivo).readLines()
                .map { it.trim() }
                .filter { it.isNotBlank() }
        } catch (e: Exception) {
            println("❌ Error al cargar archivo $archivo: ${e.message}")
            emptyList()
        }
    }

    private fun mostrarListaIds(ids: List<String>, tipo: String) {
        println("\n📋 IDs de $tipo disponibles:")
        ids.forEachIndexed { index, id ->
            println("  ${index + 1}. $id")
        }
    }

    private suspend fun consultarArtista() {
        println("\n" + "=".repeat(70))
        println("CONSULTAR ARTISTA")
        println("=".repeat(70))
        
        val artistasIds = cargarIds(archivoArtistas)
        
        if (artistasIds.isEmpty()) {
            println("❌ No hay artistas disponibles")
            return
        }

        mostrarListaIds(artistasIds, "Artistas")
        
        println("\nElige una opción:")
        println("  1. Seleccionar de la lista")
        println("  2. Ingresar ID manualmente")
        println("  3. Aleatorio")
        print("Opción: ")
        
        val opcion = scanner.nextLine().trim().toIntOrNull()

        val artistaId = when (opcion) {
            1 -> {
                print("Ingresa el número del artista (1-${artistasIds.size}): ")
                val num = scanner.nextLine().trim().toIntOrNull()
                if (num != null && num in 1..artistasIds.size) {
                    artistasIds[num - 1]
                } else {
                    println("❌ Número inválido")
                    null
                }
            }
            2 -> {
                print("Ingresa el ID del artista: ")
                scanner.nextLine().trim()
            }
            3 -> artistasIds.random()
            else -> {
                println("❌ Opción inválida")
                null
            }
        }

        if (artistaId != null) {
            val artist = spotifyClient.getArtist(artistaId)
            if (artist != null) {
                ArtistPrinter(artist).print()
            }
        } else {
            println("❌ No se pudo obtener el ID del artista")
        }
    }

    private suspend fun consultarTrack() {
        println("\n" + "=".repeat(70))
        println("CONSULTAR CANCIÓN")
        println("=".repeat(70))
        
        val pistasIds = cargarIds(archivoPistas)
        
        if (pistasIds.isEmpty()) {
            println("❌ No hay pistas disponibles")
            return
        }

        mostrarListaIds(pistasIds, "Pistas")
        
        println("\nElige una opción:")
        println("  1. Seleccionar de la lista")
        println("  2. Ingresar ID manualmente")
        println("  3. Aleatorio")
        print("Opción: ")
        
        val opcion = scanner.nextLine().trim().toIntOrNull()

        val trackId = when (opcion) {
            1 -> {
                print("Ingresa el número de la pista (1-${pistasIds.size}): ")
                val num = scanner.nextLine().trim().toIntOrNull()
                if (num != null && num in 1..pistasIds.size) {
                    pistasIds[num - 1]
                } else {
                    println("❌ Número inválido")
                    null
                }
            }
            2 -> {
                print("Ingresa el ID de la canción: ")
                scanner.nextLine().trim()
            }
            3 -> pistasIds.random()
            else -> {
                println("❌ Opción inválida")
                null
            }
        }

        if (trackId != null) {
            val track = spotifyClient.getTrack(trackId)
            if (track != null) {
                TrackPrinter(track).print()
            }
        } else {
            println("❌ No se pudo obtener el ID de la canción")
        }
    }

    private suspend fun consultarAlbum() {
        println("\n" + "=".repeat(70))
        println("CONSULTAR ÁLBUM")
        println("=".repeat(70))
        
        val albumesIds = cargarIds(archivoAlbumes)
        
        if (albumesIds.isEmpty()) {
            println("❌ No hay álbumes disponibles")
            return
        }

        mostrarListaIds(albumesIds, "Álbumes")
        
        println("\nElige una opción:")
        println("  1. Seleccionar de la lista")
        println("  2. Ingresar ID manualmente")
        println("  3. Aleatorio")
        print("Opción: ")
        
        val opcion = scanner.nextLine().trim().toIntOrNull()

        val albumId = when (opcion) {
            1 -> {
                print("Ingresa el número del álbum (1-${albumesIds.size}): ")
                val num = scanner.nextLine().trim().toIntOrNull()
                if (num != null && num in 1..albumesIds.size) {
                    albumesIds[num - 1]
                } else {
                    println("❌ Número inválido")
                    null
                }
            }
            2 -> {
                print("Ingresa el ID del álbum: ")
                scanner.nextLine().trim()
            }
            3 -> albumesIds.random()
            else -> {
                println("❌ Opción inválida")
                null
            }
        }

        if (albumId != null) {
            val album = spotifyClient.getAlbum(albumId)
            if (album != null) {
                AlbumPrinter(album).print()
            }
        } else {
            println("❌ No se pudo obtener el ID del álbum")
        }
    }

    private suspend fun consultarPlaylist() {
        println("\n" + "=".repeat(70))
        println("CONSULTAR PLAYLIST")
        println("=".repeat(70))
        
        val playlistsIds = cargarIds(archivoPlaylists)
        
        if (playlistsIds.isEmpty()) {
            println("❌ No hay playlists disponibles")
            return
        }

        mostrarListaIds(playlistsIds, "Playlists")
        
        println("\nElige una opción:")
        println("  1. Seleccionar de la lista")
        println("  2. Ingresar ID manualmente")
        println("  3. Aleatorio")
        print("Opción: ")
        
        val opcion = scanner.nextLine().trim().toIntOrNull()

        val playlistId = when (opcion) {
            1 -> {
                print("Ingresa el número de la playlist (1-${playlistsIds.size}): ")
                val num = scanner.nextLine().trim().toIntOrNull()
                if (num != null && num in 1..playlistsIds.size) {
                    playlistsIds[num - 1]
                } else {
                    println("❌ Número inválido")
                    null
                }
            }
            2 -> {
                print("Ingresa el ID de la playlist: ")
                scanner.nextLine().trim()
            }
            3 -> playlistsIds.random()
            else -> {
                println("❌ Opción inválida")
                null
            }
        }

        if (playlistId != null) {
            val playlist = spotifyClient.getPlaylist(playlistId)
            if (playlist != null) {
                PlaylistPrinter(playlist).print()
            }
        } else {
            println("❌ No se pudo obtener el ID de la playlist")
        }
    }

    private suspend fun consultarTodoAleatorio() {
        println("\n" + "=".repeat(70))
        println("CONSULTANDO TODO ALEATORIAMENTE")
        println("=".repeat(70))

        val artistasIds = cargarIds(archivoArtistas)
        val pistasIds = cargarIds(archivoPistas)
        val albumesIds = cargarIds(archivoAlbumes)
        val playlistsIds = cargarIds(archivoPlaylists)

        if (artistasIds.isNotEmpty()) {
            val artistaId = artistasIds.random()
            println("\n🔍 Consultando artista aleatorio...")
            val artist = spotifyClient.getArtist(artistaId)
            if (artist != null) {
                ArtistPrinter(artist).print()
            }
        }
        
        if (pistasIds.isNotEmpty()) {
            val pistaId = pistasIds.random()
            println("\n🔍 Consultando pista aleatoria...")
            val track = spotifyClient.getTrack(pistaId)
            if (track != null) {
                TrackPrinter(track).print()
            }
        }
        
        if (albumesIds.isNotEmpty()) {
            val albumId = albumesIds.random()
            println("\n🔍 Consultando álbum aleatorio...")
            val album = spotifyClient.getAlbum(albumId)
            if (album != null) {
                AlbumPrinter(album).print()
            }
        }
        
        if (playlistsIds.isNotEmpty()) {
            val playlistId = playlistsIds.random()
            println("\n🔍 Consultando playlist aleatoria...")
            val playlist = spotifyClient.getPlaylist(playlistId)
            if (playlist != null) {
                PlaylistPrinter(playlist).print()
            }
        }

        println("\n✅ Todas las consultas completadas")
    }
}