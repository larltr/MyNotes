package com.angelika.mynotes.presentation.screens.authentication.register

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelika.mynotes.R
import com.angelika.mynotes.ui.theme.DarkGray
import com.angelika.mynotes.ui.theme.Gray
import com.angelika.mynotes.ui.theme.Orange

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    isErrorPassword: Boolean,
    registerByEmail: (userEmail: String, userPassword: String) -> Unit,
    signByEmail: (userEmail: String, userPassword: String) -> Unit
) {
    Box(modifier = modifier) {
        var mainText by remember {
            mutableStateOf("Регистрация по email")
        }
        var signText by remember {
            mutableStateOf("Регистрация")
        }
        var isRegister by remember {
            mutableStateOf(true)
        }
        var swichText by remember {
            mutableStateOf("Уже есть аккаунт")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BaseImage(
                modifier = Modifier
                    .size(114.dp)
                    .clip(RoundedCornerShape(20.dp)),
                connectDescription = stringResource(R.string.icon_notes),
                painterResource = R.drawable.ic_notes
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = mainText, color = Gray, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(50.dp))

            val userEmail = BaseEditText(
                labelText = "Введите ваш email",
                imageVector = Icons.Default.Phone,
                contentDescription = "Icon phone",
                isError = false
            )
            val userPassword = BaseEditText(
                labelText = "Введите пароль",
                imageVector = Icons.Default.Email,
                contentDescription = "Icon email",
                isError = isErrorPassword,
            )

            Button(
                modifier = Modifier
                    .height(46.dp)
                    .width(200.dp), onClick = {
                    if (isRegister) {
                        registerByEmail(userEmail, userPassword)
                    } else {
                        signByEmail(userEmail, userPassword)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Text(
                    text = signText,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Text(
                modifier = Modifier.clickable {
                    if (isRegister) {
                        isRegister = false
                        mainText = "Вход по email"
                        signText = "Вход"
                        swichText  = "Нет аккаунта"
                    }else {
                        isRegister = true
                        mainText = "Регистрация по Email"
                        signText = "Регистрация"
                        swichText  = "Уже есть аккаунт"
                    }
                },
                text = swichText,
                fontSize = 12.sp,
                color = Orange
            )
        }
    }
}

@Composable
fun BaseImage(
    modifier: Modifier,
    connectDescription: String,
    @DrawableRes painterResource: Int
) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = painterResource),
        contentDescription = connectDescription,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseEditText(
    modifier: Modifier = Modifier,
    labelText: String,
    imageVector: ImageVector,
    contentDescription: String,
    isError: Boolean
): String {
    var textState by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier,
        value = textState,
        isError = isError,
        onValueChange = { textState = it },
        label = {
            Text(text = labelText, color = Gray)
        },
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = contentDescription) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            containerColor = DarkGray,
            focusedIndicatorColor = Gray,
            unfocusedIndicatorColor = Gray,
        )
    )
    return textState
}


