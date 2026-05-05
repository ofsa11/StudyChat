package com.example.studychat.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BasicInfo(){
    Column(
        modifier = Modifier
            .padding(5.dp)
    ){
        Text(text = "User Name: test")
        Text(text = "Email: test@test.com")
    }
}

@Composable
fun Settings(){
    Column(
        modifier = Modifier
            .padding(5.dp)
    ){
        Text(text = "test" )
    }
}