package com.example.studychat.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studychat.R
import kotlinx.coroutines.launch

data class MessageData(
    val msg: String,
    val isMe: Boolean
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPage(){

    val messages = remember{
        mutableStateListOf(
            MessageData("什么是尘土？", true),
            MessageData("从大地之肺发出的一声叹息。", false)
        )
    }

    // 用于自动滚动列表
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Chat")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_return),
                            contentDescription = "return"
                        )
                    }

                }
            )
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        ){
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                items(messages){ message->
                    Message(
                        msg = message.msg,
                        isMe = message.isMe
                    )
                }
            }
            UserInput(
                onMessageSent = { text ->
                    messages.add(MessageData(text, true))
                    coroutineScope.launch {
                        listState.animateScrollToItem(messages.size - 1)
                    }
                }
            )
        }
    }
}

@Composable
fun Message(
    msg: String,
    isMe: Boolean
){
    val bubleColor = if(isMe){
        MaterialTheme.colorScheme.primary
    }
    else{
        MaterialTheme.colorScheme.secondary
    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if(isMe) Arrangement.End else Arrangement.Start
    ){
        if(!isMe){
            Image(
                painter = painterResource(R.mipmap.ic_default),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 12.dp,end = 2.dp, top = 12.dp)
                    .clip(CircleShape)
                    .size(35.dp)
            )
        }
        Surface (
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 5.dp,
            color = bubleColor,
            modifier = Modifier
                .padding( horizontal = 8.dp, vertical = 10.dp)
                .weight(1f, fill = false)
        ){
            Text(
                text = msg,
                modifier = Modifier
                    .padding(8.dp)

            )
        }
        if(isMe){
            Image(
                painter = painterResource(R.mipmap.ic_default),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 5.dp,end = 12.dp, top = 12.dp)
                    .clip(CircleShape)
                    .size(35.dp)
            )
        }
    }
}

@Composable
fun UserInput(onMessageSent: (String) -> Unit){
    var text by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .imePadding(),
        verticalAlignment = Alignment.CenterVertically,
    ){
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("waiting input")},
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp),
            maxLines = 4,
            shape = RoundedCornerShape(12.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            modifier = Modifier
                .heightIn(48.dp)
                .align(Alignment.CenterVertically),
            shape = RoundedCornerShape(12.dp),

            onClick = {
                if (text.isNotBlank()) {
                    onMessageSent(text)
                    text = ""
                }
            },

        ){
            Text(
                text = "send",
            )
        }
    }
}

//@Preview
//@Composable
//fun UserInputPreview(){
//    UserInput()
//}
//
//@Preview
//@Composable
//fun ChatPagePreview(){
//    Message("叶子已拿定绿色的主意",true)
//}

@Preview
@Composable
fun ChatPagePreview2(){
    ChatPage()
}