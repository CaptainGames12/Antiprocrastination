package com.example.anti_procrastination

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Preview
@Composable
fun Shop(){
    Text(text = "working")
    val context = LocalContext.current
    data class MusicDisc(val disc: MediaPlayer, val name: String, val cover: Int)
    val playlist =listOf(
        MusicDisc(
        disc = MediaPlayer.create(context, R.raw.pigstep),
        name="Pigstep",
        cover=R.drawable.pigstep
        )
    )
    Image(
            painter = painterResource(R.drawable.shop),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()


        )
    var index by rememberSaveable { mutableIntStateOf(0) }
    var music=playlist[0]
    Row(){
        Icon(
            Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    index+=1
                    music = playlist[index]
                }
        )
        Image(
            painter = painterResource(music.cover),
            contentDescription = null
        )
        Icon(
            Icons.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    index-=1
                    music = playlist[index]
                }
        )
    }
}