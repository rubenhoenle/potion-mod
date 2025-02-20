package xyz.hoenle.potionmod.api.http

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import net.minecraft.potion.Potions
import xyz.hoenle.potionmod.api.PotionNotifyService
import xyz.hoenle.potionmod.light.PotionLightColor
import xyz.hoenle.potionmod.light.PotionLightState
import java.net.InetSocketAddress
import java.util.concurrent.Executors

class PotionHttpService : PotionNotifyService {
    private var state: PotionLightState = PotionLightState(false, PotionLightColor.RED)
    
    init {
        val server = HttpServer.create(InetSocketAddress(44533), 0)

        // GET Endpoint: /potionlight
        server.createContext("/potionlight", PotionLightHttpHandler(this))

        server.executor = Executors.newCachedThreadPool()
        server.start()
        println("Server started at http://localhost:44533")
    }

    override fun updateLightState(state: PotionLightState) {
        this.state = state
    }

    fun getLightState(): PotionLightState = state
}

class PotionLightHttpHandler(private val httpService: PotionHttpService) : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        if (exchange.requestMethod == "GET") {
            val lightState = httpService.getLightState()

            val response = """{"enabled": ${lightState.enabled}, "color": "${lightState.potionLightColor}"}"""
            exchange.responseHeaders.add("Content-Type", "application/json")
            exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
            exchange.responseBody.use { it.write(response.toByteArray()) }
        } else {
            exchange.sendResponseHeaders(405, 0) // Method Not Allowed
        }
    }
}
