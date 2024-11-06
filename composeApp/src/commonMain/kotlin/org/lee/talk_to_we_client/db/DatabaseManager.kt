package org.lee.talk_to_we_client.db

import com.squareup.sqldelight.db.SqlDriver

class DatabaseManager(driver: SqlDriver) {
    private val database = AppDatabase(driver)
    private val userQueries = database.userQueries

    // Android에서의 SqlDriver 설정
    val androidDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "test.db")
    val databaseHelper = DatabaseHelper(androidDriver)

    // Desktop에서의 SqlDriver 설정
    val desktopDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    AppDatabase.Schema.create(desktopDriver)
    val databaseHelper = DatabaseHelper(desktopDriver)

    fun insertUser(name: String, age: Int) {
        userQueries.insertUser(name, age)
    }

    fun getAllUsers(): List<User> {
        return userQueries.selectAllUsers().executeAsList()
    }

    fun getUserById(id: Long): User? {
        return userQueries.selectUserById(id).executeAsOneOrNull()
    }

    fun deleteUserById(id: Long) {
        userQueries.deleteUserById(id)
    }

    fun test(){
        // User 데이터 추가
        databaseHelper.insertUser("Alice", 25)
        databaseHelper.insertUser("Bob", 30)

        // 모든 User 조회
        val users = databaseHelper.getAllUsers()
        users.forEach {
            println("User: ${it.name}, Age: ${it.age}")
        }

        // 특정 User 조회
        val user = databaseHelper.getUserById(1)
        println("Fetched User: ${user?.name}, Age: ${user?.age}")

        // User 삭제
        databaseHelper.deleteUserById(1)
        println("User with ID 1 deleted")
    }
}