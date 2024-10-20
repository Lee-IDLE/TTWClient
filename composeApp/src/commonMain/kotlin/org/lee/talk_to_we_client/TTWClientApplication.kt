package org.lee.talk_to_we_client

import java.io.File

class TTWClientApplication {
    companion object{
        val currentOS = File(System.getProperty("compose.application.resources.dir"))
    }
}