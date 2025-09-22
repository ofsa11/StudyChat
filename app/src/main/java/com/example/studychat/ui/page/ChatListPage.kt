package com.example.studychat.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import coil3.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.example.studychat.R
import com.example.studychat.data.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListPage(
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text("消息") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            items(30) { index ->
                MessageCard(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth(),
                    index = index
                )
            }
        }
    }
}

@Composable
fun MessageCard(
    navController: NavController,
    modifier: Modifier,
    index: Int
){
    val user = User(R.mipmap.ic_default,"username", "message${index + 1}")
//    Surface (
//        shape = MaterialTheme.shapes.medium,
//        shadowElevation = 5.dp,
//        color = MaterialTheme.colorScheme.primary,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    )
    Card (
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp,)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable{
                    navController.navigate("chat")
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = user.avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .size(50.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, shape = CircleShape)
            )
            Column (
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = user.message,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Text(
                text = "14:00",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}