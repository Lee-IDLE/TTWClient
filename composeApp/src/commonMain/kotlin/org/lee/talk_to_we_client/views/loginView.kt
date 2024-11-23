package org.lee.talk_to_we_client.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lee.talk_to_we_client.viewModels.loginViewModel

@Composable
fun loginScreen() {
    val viewModel = loginViewModel()
    loginView(viewModel)
}
@Composable
fun loginView(viewModel: loginViewModel) {
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf<CharArray>(CharArray(0)) }
    var showPassword by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    val isLoading by viewModel.isLoading.collectAsState() // collectAsStateWithLifecycle()
    val isLogin by viewModel.isLogin
    val loginMessage by viewModel.loginMessage

    val showDialog = remember { mutableStateOf(false) }

    if(isLogin == false && loginMessage.isNotEmpty()) {
        println("알림 창 띄운다!!!")
        showDialog.value = true
        NotificationDialog("알림", loginMessage, onDismiss = { viewModel.loginMessage.value = "" })
    }
    /*
    tab키 기능 구현
    https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Tab_Navigation/README.md
     */
    if(isLoading == true){
        loadingScreen()
    } else if(!isLoading){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp).fillMaxSize()
        ){
            Column(modifier = Modifier) {
                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // https://kotlinworld.com/238 포커싱 방법 참고

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = userId,
                        onValueChange = { userId = it },
                        singleLine = true,
                        placeholder = { Text("계정") },
                    )
                }
                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /*
                    참고
                    https://stackoverflow.com/questions/65304229/toggle-password-field-jetpack-compose
                    https://alitalhacoban.medium.com/show-hide-password-jetpack-compose-d0c4abac568f
                     */
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = String(userPassword),
                        onValueChange = { newValue ->
                            userPassword = newValue.toCharArray()
                        },
                        singleLine = true,
                        placeholder = { Text("암호") },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                viewModel.login(userId, String(userPassword).trim())
                            }
                        ),
                        visualTransformation =
                        if(showPassword) {
                            VisualTransformation.None
                        }else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            if (showPassword) {
                                IconButton(onClick = { showPassword = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "hide_password"
                                    )
                                }
                            } else {
                                IconButton(onClick = { showPassword = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "hide_password"
                                    )
                                }
                            }
                        }
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.login(userId, String(userPassword))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Text("로그인", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 26.sp)
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {},
                    modifier = Modifier.padding(8.dp),
                ){
                    Text("회원가입", color = Color.Blue)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.width(1.dp).height(20.dp).background(Color.Blue))

                Spacer(modifier = Modifier.width(10.dp))

                TextButton(
                    onClick = {},
                    modifier = Modifier.padding(8.dp),
                ){
                    Text("아이디, 비밀번호 찾기", color = Color.Blue)
                }
            }
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun FocusableBox(
    text: String = "",
    onClick: () -> Unit = {},
    value: String
){
    var changeValue by remember {mutableStateOf(value) }

    // 키 입력 감지
    val keyPressState = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    TextField(modifier = Modifier
        .fillMaxWidth()
        .onPreviewKeyEvent {
            if(it.key == androidx.compose.ui.input.key.Key.Tab) {
                when(it.type) {
                    KeyUp -> {
                        keyPressState.value = true
                    }
                    else -> {
                        keyPressState.value = false
                    }
                }
            }
            if(it.key == androidx.compose.ui.input.key.Key.Enter) {
                if(it.type == KeyUp && text == "암호") {
                    onClick()
                } else if(it.type == KeyUp) {
                    when(it.type) {
                        KeyUp -> {
                            keyPressState.value = true
                        }
                        else -> {
                            keyPressState.value = false
                        }
                    }
                }

            }
            false
        }
        .focusable(interactionSource = interactionSource),
        value = changeValue,
        onValueChange = { changeValue = it },
        placeholder = { Text(text) },
        singleLine = true
    )
}

@Composable
fun NotificationDialog(title: String, message: String, onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier
            .size(280.dp, 240.dp)
            .clip(RoundedCornerShape(16.dp)) // 모서리 둥글게
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(16.dp)) // 테두리 모서리 둥글게
            .zIndex(Float.MAX_VALUE),
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}


@Composable
fun loadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun testPreview(){
    loginScreen()
}