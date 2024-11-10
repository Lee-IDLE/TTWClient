package org.lee.talk_to_we_client.db

import com.squareup.sqldelight.db.SqlDriver
import com.sun.tools.javac.util.Context

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(TTWClientDB.Schema, context, "TTWClient.db")
}