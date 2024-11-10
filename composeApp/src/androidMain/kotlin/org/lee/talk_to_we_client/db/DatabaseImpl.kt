package org.lee.talk_to_we_client.db

import com.squareup.sqldelight.android.AndroidSqliteDriver

class DatabaseImpl : DatabaseManager {
    private val driver = AndroidSqliteDriver(initDB.Schema, context, "TTWClient.db")
    private val database = DatabaseManager(driver)

    override fun create(): MyQueries {
        return database.myQueries
    }
}