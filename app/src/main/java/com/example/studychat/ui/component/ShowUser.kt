package com.example.studychat.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studychat.R

@Composable
fun ShowUser(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.mipmap.ic_default),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "testUsername",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "Signature",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = { /* TODO: 跳转到编辑资料页 */ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "编辑资料"
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowUserPreview(){
    ShowUser()
}


@Preview
@Composable
fun UserInfo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 头像
            Image(
                painter = painterResource(R.mipmap.ic_default),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 用户信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "用户名",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "个性签名或简介",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { /* TODO: 跳转到编辑资料页 */ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "编辑资料"
                )
            }
        }
    }
}