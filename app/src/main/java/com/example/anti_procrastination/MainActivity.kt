package com.example.anti_procrastination

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.ImageDecoder
import android.graphics.Insets.add
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings.Global.getString
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.anti_procrastination.ui.theme.AntiprocrastinationTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.LocalContext



import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.lang.Integer.getInteger

import java.util.concurrent.Flow
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer

import kotlin.math.ceil


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AntiprocrastinationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Background()

                    Amethyst_growth()
                }
            }
        }

    }
}

@Composable
fun Amethyst_growth(modifier: Modifier=Modifier){
    val changer = arrayOf(R.drawable.phase_1, R.drawable.phase_2, R.drawable.phase_3, R.drawable.phase_4)
    var counter by rememberSaveable {
        mutableIntStateOf(0)
    }
    var amethystScore by rememberSaveable {
        mutableIntStateOf(0)
    }
    var amethyst = painterResource(changer[counter])
    val sharedPref = LocalContext.current.getSharedPreferences(
        "Score", Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putInt("Score", amethystScore)
        apply()
    }

    var highScore = sharedPref.getInt("Score", 0)

    val timer = object : CountDownTimer(20000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if ((counter == 3) and (millisUntilFinished<=5000)) {
                cancel()
                counter = 0
                amethystScore+=1
            }
        }

        override fun onFinish() {

            counter += 1

        }
    }
    timer.start()

    Text(
        text = "Amethysts:$highScore"
    )

    Image(
        painter = amethyst,
        contentDescription = "amethyst" ,
        Modifier
            .size(70.dp)
    )
}

@Composable
fun Background(){
    val cave = painterResource(R.drawable.background)
    Image(
        painter = cave,
        contentDescription = "background")
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AntiprocrastinationTheme {
        Background()
        Amethyst_growth()
    }
}