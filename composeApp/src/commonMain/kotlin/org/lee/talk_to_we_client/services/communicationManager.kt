package org.lee.talk_to_we_client.services

import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.timeout
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.content.Version
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.lee.talk_to_we_client.models.LoginData
import org.lee.talk_to_we_client.models.RequestData
import java.net.ProtocolFamily

class communicationManager {
    suspend fun LoginTest(UserId: String, UserPassword: String) {
        val client = HttpClient(CIO) {
            engine {
                pipelining = false
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 4_000
                connectTimeoutMillis = 4_000
                socketTimeoutMillis = 4_000
            }
            expectSuccess = true
            /*
            install(Logging){

            }
             */
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
            val response = client.post("http://127.0.0.1:8080/requireLogin") {
                timeout{
                    requestTimeoutMillis = 4_000
                    connectTimeoutMillis = 4_000
                    socketTimeoutMillis = 4_000
                }
                contentType(ContentType.Application.Json)
                setBody(jsonData)//jsonData
                //version(HttpProtocolVersion.HTTP_2_0)
            }

            println("connection success")
            if(response.status.isSuccess()){
                println("sendLoginData Success!!")
            } else {
                println("Fuck SendLoginData Error!!:${response.status.value}")
            }
        }catch (e: Exception){
            println("Fuck! sendLoginData Error!!:${e.message}")
        }finally {
            client.close()
        }
    }
}