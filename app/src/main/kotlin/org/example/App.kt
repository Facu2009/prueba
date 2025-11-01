package org.example

suspend fun main(args: Array<String>) {
    val spotify = Autenticacion().authenticate()

    if ("--web" in args) startWebServer(spotify)
    else Consola(spotify).run()
}
