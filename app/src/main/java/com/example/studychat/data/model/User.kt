package com.example.studychat.data.model

import android.media.Image
import androidx.compose.ui.graphics.painter.Painter

data class User(
    val avatar: Int,
    val username: String,
    val message: String
)
