package com.breuninger.archplayground

import com.breuninger.archplayground.model.Card
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
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
class CardHandler : TextWebSocketHandler() {

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
//         {type: "join/say", data: "name/msg"}
        when (json.get("type").asText()) {
            "join" -> {
                val user = User(uids.getAndIncrement(), json.get("data").asText())
                sessionList.put(session!!, user)
                // tell this user about all other users
//                emit(session, Message("users", sessionList.values))
//                // tell all other users, about this user
//                broadcastToOthers(session, Message("join", user))
            }
            "say" -> {
//                broadcast(Message("say", json.get("data").asText()))
            }
        }
    }

    fun emit(session: WebSocketSession, card: Card) = session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(card)))
    fun broadcast(card: Card) {
        sessionList.forEach { emit(it.key, card) }
    }
    fun broadcastToOthers(me: WebSocketSession, card: Card) = sessionList.filterNot { it.key == me }.forEach { emit(it.key, card) }
}

@Configuration
@EnableWebSocket
open class WSConfig(private val cardHandler: CardHandler) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(cardHandler, "/chat").setAllowedOrigins("*").withSockJS()
    }
}
