package com.example.anti_procrastination


import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("SourceLockedOrientationActivity")
@Preview
@Composable
fun Shop(){
    //звертаємося до простору всередині програми
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    //створюємо дата клас для музикальних пластинок, а також список пластинок, які є екземплярами класу MusicDisc
    data class MusicDisc(val disc: Int, val name: String, val cover: Int, val price: Int)
    val playlist =listOf(
        MusicDisc(
            disc = R.raw.pigstep,
            name="Pigstep",
            cover=R.drawable.pigstep,
            price = 5
        ),
        MusicDisc(
            disc = R.raw.precipice,
            name = "Precipice",
            cover = R.drawable.precipice,
            price = 10
        ),
        MusicDisc(
            disc = R.raw.cat,
            name = "Cat",
            cover = R.drawable.cat,
            price = 15
        ),
        MusicDisc(
            disc = R.raw.blocks,
            name = "Blocks",
            cover = R.drawable.blocks,
            price = 8
        ),
        MusicDisc(
            disc = R.raw.chirp,
            name = "Chirp",
            cover = R.drawable.chirp,
            price = 10
        ),
        MusicDisc(
            disc = R.raw.far,
            name = "Far",
            cover = R.drawable.far,
            price = 12
        ),
        MusicDisc(
            disc = R.raw.mall,
            name = "Mall",
            cover = R.drawable.mall,
            price = 20
        ),
        MusicDisc(
            disc = R.raw.mellohi,
            name = "Mellohi",
            cover = R.drawable.mellohi,
            price = 21
        ),
        MusicDisc(
            disc = R.raw.stal,
            name = "Stal",
            cover = R.drawable.stal,
            price = 30
        ),
        MusicDisc(
            disc = R.raw.strad,
            name = "Strad",
            cover = R.drawable.strad,
            price = 28
        ),
        MusicDisc(
            disc = R.raw.ward,
            name = "Ward",
            cover = R.drawable.ward,
            price = 24
        ),
        MusicDisc(
            disc = R.raw.wait,
            name = "Wait",
            cover = R.drawable.wait,
            price = 32
        ),
        MusicDisc(
            disc = R.raw.otherside,
            name = "Otherside",
            cover = R.drawable.otherside,
            price = 35
        ),
        MusicDisc(
            disc = R.raw.relic,
            name = "Relic",
            cover = R.drawable.relic,
            price =40
        ),
        MusicDisc(
            disc = R.raw.creator,
            name = "Creator",
            cover = R.drawable.creator,
            price = 50
        ),
        MusicDisc(
            disc = R.raw.creator_music_box,
            name = "Creator Music Box",
            cover = R.drawable.creator_music_box,
            price = 35
        )
    )
    //створюємо задній фон і за допомогою Crop розтягуємо зображення жеоди на екрані
    Image(
            painter = painterResource(R.drawable.shop),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()


        )
    //усі змінні-стани, що стосуються перемикання пластинок
    var index by remember{mutableIntStateOf(0)}
    var music by remember {mutableStateOf(playlist[0])}
    var isPlaying by rememberSaveable { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),

        )
    {
        //назва пластинки та її ціна
        Text(
            text = music.name+": "+music.price,
            fontFamily = FontFamily(Font(R.font.minecraft)),
            color = Color.White,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            //стрілка для перемикання на попередню пластинку
            Icon(
                Icons.Rounded.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        isPlaying = false
                        try {
                            index -= 1

                            mediaPlayer?.release()
                            mediaPlayer = null
                            music = playlist[index]
                            mediaPlayer = MediaPlayer.create(context, music.disc)

                        } catch (exception: IndexOutOfBoundsException) {
                            index = playlist.size - 1

                            mediaPlayer?.release()
                            mediaPlayer = null
                            music = playlist[index]
                            mediaPlayer = MediaPlayer.create(context, music.disc)

                        }
                    }
                    .size(46.dp),
                tint = Color.White
            )
            //вигляд пластинки
            Image(
                painter = painterResource(music.cover),
                contentDescription = music.name
            )
            //стрілка для перемикання на попередню пластинку
            Icon(
                Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        isPlaying = false
                        try {
                            index += 1
                            mediaPlayer?.release()
                            mediaPlayer = null
                            music = playlist[index]
                            mediaPlayer = MediaPlayer.create(context, music.disc)

                        } catch (exception: IndexOutOfBoundsException) {
                            index = 0
                            mediaPlayer?.release()
                            mediaPlayer = null
                            music = playlist[index]
                            mediaPlayer = MediaPlayer.create(context, music.disc)

                            Log.d("change", "change is done")
                        }
                    }
                    .size(46.dp),
                tint = Color.White
            )

        }
        //кнопка для програвання самого треку
        Icon(
            Icons.Rounded.PlayArrow,
            contentDescription = "програти",
            modifier = Modifier
                .clickable
                {
                    isPlaying = !isPlaying
                    if(score >= music.price && isPlaying)
                    {
                        mediaPlayer?.start()

                    }
                    else if(!isPlaying){
                        mediaPlayer?.pause()
                    }
                    else{
                        //виводимо спливаючий текст у разі недостачі аметисту
                        Toast.makeText(context, "Недостатньо аметисту", LENGTH_SHORT).show()
                    }
                }
                .size(46.dp),
            tint = Color.White,

        )
    }
}