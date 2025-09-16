package com.example.studychat.ui.page

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

import com.example.studychat.utils.UserManageUtils

@Composable
fun UserManagePage(onLoginSuccess: () -> Unit = {}){
    val context = LocalContext.current
    val userManage = remember { UserManageUtils(context) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            value = username,
            label = {Text("username")},
            onValueChange = {
                username = it
            },
        )

        OutlinedTextField(
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            value = password,
            label = {Text("password")},
            onValueChange = {
                password = it
            },
        )
        Row (
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .weight(1f),
                onClick = {
                    if(username == "" || password == ""){
                        Toast.makeText(context, "用户名或密码不得为空", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        userManage.Register(username, password)
                        Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
                        username = ""
                        password = ""
                    }
                },
            ){
                Text("Register")
            }
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .weight(1f),
                onClick = {
                    if(username == "" || password == ""){
                        Toast.makeText(context, "用户名或密码不得为空", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if(userManage.Login(username,password)){
                            Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        }
                        else{
                            Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            ){
                Text("Login")
            }
        }
    }
}