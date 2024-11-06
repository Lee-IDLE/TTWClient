package org.lee.talk_to_we_client.objectClass

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object webSocket {
    var webSocketSession: WebSocketSession? = null
    /*
    receive 함수에서 서버로 부터 오는 모든 값을 받고,
    map에 넣어뒀다가 각자 필요한 곳에서 꺼내 가는건 어떨까?
    그 메시지가 어디꺼인지 아는 방법은
    key값은 category로 하고
    value를 Message 내용으로 하는거지
    채팅 방 마다 구분도 필요하니까 Message 내용에 채팅방의 번호도 줘야 할 듯?
     */

    suspend fun connect(){
        if(webSocketSession == null || !webSocketSession!!.isActive) {
            try{
                val client = HttpClient(CIO).config {
                    install(WebSockets)
                }

                // Windows 127.0.0.1:8080
                // 에뮬레이터 10.0.2.2:8080

                webSocketSession = client.webSocketSession(
                    method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = ""
                )
            }catch (e: Error) {
                println("webSocket connection fail: ${e.message}")
            }
        }
    }

    fun send(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            webSocketSession?.send(Frame.Text(message))
        }
    }

    fun receive(){
        CoroutineScope(Dispatchers.IO).launch {
            webSocketSession?.incoming?.consumeEach { frame ->
                when (frame) {
                    is Frame.Text -> {
                        println("Receive to Server: ${frame.readText()}")
                    }
                    else -> { println("EXT...") }
                }
            }
        }
    }
}