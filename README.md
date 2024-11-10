채팅 앱을 만들어 볼까 합니다.
취준때 만들던 Soribada 프로젝트는... 일단 유기를 할까 합니다.
취업하고 나서 회사 일과 회사 공부가 먼저였다가 지금은 조금 적응했으니
회사에서 쓸 채팅앱을 한번 만들어보고자 해서 만든 프로젝트입니다.

어차피 내가 하고 싶은거 만드려고 했던거니깐 상관 없겠죠!

해당 프로젝트는 KMP를 사용해 AOS, Windows에서 동작하는 채팅앱이 될껍니다.
ios는 폰, 맥북, ios 개발자 계정 등 돈이 많이 들어서 안 할 생각입니다.

화면은 간단하게
사용자 목록 화면(친구 목록), 채팅방 목록, 채팅방 화면
이렇게 만들어볼 생각입니다.

**DB 관련**
WebSocketSession으로 부터 들어오는 모든 메시지들을 DB의 특정 테이블에 저장하고,
코루틴으로 필요한 곳에서 마다 테이블에서 메시지 꺼내와서 사용하도록 할 예정

| Category | Data | Separation |
| -------- | ---- | ---------- |
|          |      |            |


---

This is a Kotlin Multiplatform project targeting Android, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…