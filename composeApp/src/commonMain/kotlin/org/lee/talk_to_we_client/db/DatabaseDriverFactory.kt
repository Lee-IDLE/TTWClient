package org.lee.talk_to_we_client.db

import app.cash.sqldelight.db.SqlDriver

// Sqlidelight 구현 방법 보자
// https://medium.com/@michalankiersztajn/sqldelight-kmp-kotlin-multiplatform-tutorial-e39ab460a348
// https://github.com/AndroBrain/SQLDelightExample?source=post_page-----e39ab460a348--------------------------------

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}