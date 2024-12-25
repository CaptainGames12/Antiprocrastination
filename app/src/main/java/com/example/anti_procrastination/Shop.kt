package com.example.anti_procrastination


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


@Preview
@Composable
fun Shop(){
    //звертаємося до простору всередині програми
    val context = LocalContext.current
    //створюємо дата клас для музикальних пластинок, а також список пластинок, які є екземплярами класу MusicDisc
    data class MusicDisc(val disc: MediaPlayer, val name: String, val cover: Int, val price: Int)
    val playlist =listOf(
        MusicDisc(
            disc = MediaPlayer.create(context, R.raw.pigstep),
            name="Pigstep",
            cover=R.drawable.pigstep,
            price = 5
        ),
        MusicDisc(
            disc = MediaPlayer.create(context, R.raw.precipice),
            name = "Precipice",
            cover = R.drawable.precipice,
            price = 10
        ),
        MusicDisc(
            disc = MediaPlayer.create(context, R.raw.cat),
            name = "Cat",
            cover = R.drawable.cat,
            price = 15
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
    var isPlaying by remember { mutableStateOf(false) }
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
                        try {
                            index -= 1
                            music = playlist[index]

                        }
                        catch(exception:IndexOutOfBoundsException){
                            val lastIndex = playlist.size-1
                            music = playlist[lastIndex]
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
                        try {
                            index += 1
                            music = playlist[index]
                        }
                        catch(exception:IndexOutOfBoundsException){
                            index = 0
                            music = playlist[index]
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
            contentDescription = null,
            modifier = Modifier
                .clickable
                {
                    isPlaying = !isPlaying
                    if(score >= music.price && isPlaying)
                    {
                        music.disc.start()

                    }
                    else if(!isPlaying){
                        music.disc.pause()
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