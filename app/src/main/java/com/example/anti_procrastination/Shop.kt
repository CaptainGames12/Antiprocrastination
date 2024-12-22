package com.example.anti_procrastination


import android.media.MediaPlayer
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun Shop(){

    val context = LocalContext.current
    data class MusicDisc(val disc: MediaPlayer, val name: String, val cover: Int, val price: Int)
    val playlist =listOf(
        MusicDisc(
            disc = MediaPlayer.create(context, R.raw.pigstep),
            name="Pigstep",
            cover=R.drawable.pigstep,
            price = 5
        ),
        MusicDisc(
            disc = MediaPlayer.create(context, R.raw.pigstep),
            name = "Precipice",
            cover = R.drawable.precipice,
            price = 10
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
    var index by remember{mutableIntStateOf(0)}
    var music by remember {mutableStateOf(playlist[0])}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),

        )
    {
        Text(
            text = music.name+": "+music.price,
            fontFamily = FontFamily(Font(R.font.minecraft)),
            color = Color.White,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Rounded.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        try {
                            index -= 1
                            music = playlist[index]
                        }
                        catch(exception:IndexOutOfBoundsException){
                            val lastIndex = playlist.size-1
                            music = playlist[lastIndex]
                        }
                    },
                tint = Color.White
            )
            Image(
                painter = painterResource(music.cover),
                contentDescription = music.name
            )

            Icon(
                Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        try {
                            index += 1
                            music = playlist[index]
                        }
                        catch(exception:IndexOutOfBoundsException){
                            index = 0
                            music = playlist[index]
                        }
                    },
                tint = Color.White
            )

        }
        Icon(
            Icons.Rounded.PlayArrow,
            contentDescription = null,
            modifier = Modifier
                .clickable
                {
                    if(score == music.price)
                    {
                        music.disc.start()
                    }
                    else{
                        Toast.makeText(context, "Недостатньо аметисту", LENGTH_SHORT)
                    }
                },
            tint = Color.White
        )
    }
}