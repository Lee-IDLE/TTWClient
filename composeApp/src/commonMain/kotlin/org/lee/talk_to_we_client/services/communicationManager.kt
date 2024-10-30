package org.lee.talk_to_we_client.services

import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json
import org.lee.talk_to_we_client.models.LoginData
import org.lee.talk_to_we_client.models.RequestData

class communicationManager {
    suspend fun LoginTest(UserId: String, UserPassword: String) {
        val client = HttpClient(CIO).config {
            install(WebSockets)
        }

        println("login data start")
        val loginDataList = mutableListOf(LoginData(UserId, UserPassword))
        sendLoginData(client, RequestData<LoginData>("login", loginDataList))
    }

    suspend fun sendLoginData(client: HttpClient,loginData: RequestData<LoginData>){
        try{
            println("make json data")
            val jsonData = Json.encodeToString(
                RequestData.serializer(LoginData.serializer()), loginData
            )
            // 참고 https://java-jedi.medium.com/welcome-ktor-client-your-next-http-client-for-kotlin-based-project-part-ii-236462d4c836
            println("try connection")
            val response = client.webSocket(
                method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/login"
            ) {
                println("connection success")

                send(Frame.Text(jsonData))

                for (frame in incoming){
                    when (frame) {
                        is Frame.Text -> { println("Receive to Server: ${frame.readText()}")}
                        else -> { println("EXT...") }
                    }
                }
            }
        }catch (e: Exception){
            println("Fuck! sendLoginData Error!!:${e.message}")
        }finally {
            client.close()
        }
    }
}