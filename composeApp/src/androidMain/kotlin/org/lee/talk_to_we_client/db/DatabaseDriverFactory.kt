package org.lee.talk_to_we_client.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.lee.talk_to_we_client.TTWClientDB

actual class DatabaseDriverFactory(private val context: Context){
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(
            schema = TTWClientDB.Schema,
            context = context,
            name = "TTWClient.db"
        )
}