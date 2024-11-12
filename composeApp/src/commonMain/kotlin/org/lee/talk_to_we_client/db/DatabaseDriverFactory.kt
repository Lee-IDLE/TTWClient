package org.lee.talk_to_we_client.db

import app.cash.sqldelight.db.SqlDriver


expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}