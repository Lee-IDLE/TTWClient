package org.lee.talk_to_we_client.db

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class DatabaseImpl: DatabaseManager {
    val databasePath = ""
    private val driver = JdbcSqliteDriver(databasePath) // JdbcSqliteDriver.IN_MEMORY 메모리에 생성(끄면 사라짐)
    private val database = DatabaseManager(driver)

    init {
        MyDatabase.Schema.create(driver)
    }

    override fun create(): MyQueries {
        return database.myQueries
    }
}