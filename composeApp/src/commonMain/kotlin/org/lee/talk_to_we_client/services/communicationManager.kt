package org.lee.talk_to_we_client.services

import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.timeout
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.Version
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import org.lee.talk_to_we_client.models.LoginData
import org.lee.talk_to_we_client.models.RequestData
import java.net.ProtocolFamily

class communicationManager {
    suspend fun loginProcess(UserId: String, UserPassword: String) {
        val client = HttpClient(CIO).config {
            install(WebSockets)
        }

        println("login data start")
        val loginDataList = mutableListOf(LoginData(UserId, UserPassword))
        sendLoginData(client, RequestData<LoginData>("login", loginDataList))
        println("login data end")
        client.close()
    }

    suspend fun sendLoginData(client: HttpClient,loginData: RequestData<LoginData>){
        try{
            println("make json data")
            val jsonData = Json.encodeToString(
                RequestData.serializer(LoginData.serializer()), loginData
            )
            // 참고 https://java-jedi.medium.com/welcome-ktor-client-your-next-http-client-for-kotlin-based-project-part-ii-236462d4c836
            println("try connection")

            client.webSocket(
                method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/login"
            ) {
                println("connection success")

                send(Frame.Text(jsonData))

                println("data send success")

                incoming.consumeEach { frame ->
                    when (frame) {
                        is Frame.Text -> { println("Receive to Server: ${frame.readText()}")}
                        else -> { println("EXT...") }
                    }
                }
            }
        }catch (e: Exception){
            println("Fuck! sendLoginData Error!!:${e.message}")
        }finally {
            //client.close()
        }
    }


}