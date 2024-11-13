package org.lee.talk_to_we_client.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.lee.talk_to_we_client.TTWClientDB

actual class DatabaseDriverFactory{
    actual fun create(): SqlDriver {
        val dbPath = "jdbc:sqlite:TTWClientDB.db"
        val driver: SqlDriver = JdbcSqliteDriver(dbPath)
        TTWClientDB.Schema.create(driver)
        return driver
    }
}