package com.example.studychat.ui.page

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.studychat.R
import com.example.studychat.data.model.User
import com.example.studychat.utils.UserManageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfPage(navController: NavController){
    val context = LocalContext.current
    val userManage = remember { UserManageUtils(context) }
    val user = User(R.mipmap.ic_default,"username", "message")
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text("我的")
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            userManage.Logout()
                            //onLogout()
                        }
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_logout),
                            contentDescription = "logout")
                    }
                }
            )
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AsyncImage(
                model = user.avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                ItemList(
                    showDescription = "Basic Info",
                    expandedContent = { BasicInfo() }
                )
                ItemList(
                        showDescription = "Settings",
                    expandedContent = { Settings() }
                )
            }
        }
    }
}

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    showDescription: String,
    expandedContent: @Composable (() -> Unit)? = null
) {
    var isExpanded by remember { mutableStateOf(false)}

    Column(
        modifier = modifier
            .animateContentSize() ,
    ){
        ListItem(
            modifier = Modifier.clickable{
                isExpanded = !isExpanded
            },
            headlineContent = {
                Text(text = showDescription)
            },
            trailingContent = {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }
        )

//        if (isExpanded) {
//            HorizontalDivider(
//                modifier = Modifier.padding(horizontal = 16.dp),
//            )
//        }

        if (isExpanded && expandedContent != null) {
            expandedContent()
        }
    }
}

@Preview
@Composable
fun PreviewItemList(){
    ItemList(showDescription = "基本信息")
}
