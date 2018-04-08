package com.breuninger.archplayground

import com.breuninger.archplayground.model.Card
import com.breuninger.archplayground.model.Comment
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.atomic.AtomicLong

class User(val id: Long, val name: String)

//@Scope(value="singleton")
@Component
class Broadcaster : TextWebSocketHandler() {

    val sessionList = HashMap<WebSocketSession, User>()
    var uids = AtomicLong(0)

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }

    @Throws(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val user = User(uids.getAndIncrement(), "user")
        sessionList.put(session, user)
    }

    public override fun handleTextMessage(session: WebSocketSession?, message: TextMessage?) {
        val json = ObjectMapper().readTree(message?.payload)
        when (json.get("type").asText()) {
            "join" -> {
                val user = User(uids.getAndIncrement(), json.get("data").asText())
                sessionList.put(session!!, user)
            }
        }
    }

    fun emitData(session: WebSocketSession, type: String, data: Any) = session.sendMessage(TextMessage("{ \"type\": \"$type\", \"data\": " + jacksonObjectMapper().writeValueAsString(data) + "}"))

    fun broadcast(type: String, data: Any) {
        sessionList.forEach { emitData(it.key, type, data) }
    }

}

@Configuration
@EnableWebSocket
open class WSConfig(private val cardHandler: Broadcaster) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(cardHandler, "/messages").setAllowedOrigins("*").withSockJS()
    }
}
