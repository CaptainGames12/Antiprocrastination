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
import android.util.Log
import android.util.Size
import android.widget.ImageView
import android.widget.Toast
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import java.lang.Integer.getInteger
import java.lang.reflect.Array.set

import java.util.concurrent.Flow
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer

import kotlin.math.ceil
var score = 0

class MainActivity : ComponentActivity() {

    var isOpened by mutableStateOf(true)
    override fun onPause() {
        super.onPause()
     
        isOpened = false
        Log.d("lifecycle", "stopped")
    }

    override fun onResume() {
        super.onResume()
        isOpened = true
        val toast = Toast.makeText(this, "Ріст зкинуто", Toast.LENGTH_SHORT)
        toast.show()
        Log.d("lifecycle", "opened")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOpened = true
        setContent {
            AntiprocrastinationTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Main"
                )
                {

                    composable("Main"){
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        )
                        {


                            Background()
                            Amethyst_growth(isOpened = isOpened)


                        }
                        Button(
                            modifier = Modifier
                                .padding(top = 60.dp, start =16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(254, 189, 40)),
                            onClick =
                            {
                                navController.navigate("ShopScreen")
                            },
                            shape = RoundedCornerShape(0),
                            enabled = true
                        )
                        {
                            Text(
                                text = "Shop",
                                fontFamily = FontFamily(Font(R.font.minecraft)),
                                fontSize = 24.sp,

                                )
                            Log.d("nav1", "navigate")
                        }
                    }
                    composable("ShopScreen"){
                        Shop()
                    }
                }


                }
            }
        }

    }

@Composable
fun Amethyst_growth(modifier: Modifier=Modifier, isOpened:Boolean){
    val changer = arrayOf(R.drawable.phase_1, R.drawable.phase_2, R.drawable.phase_3, R.drawable.phase_4)
    var counter by rememberSaveable{
        mutableIntStateOf(0)
    }
    var amethystScore by rememberSaveable {
        mutableIntStateOf(0)
    }
    score = amethystScore
    var isPressed by rememberSaveable { mutableStateOf(false) }
    var amethyst = painterResource(changer[counter])
    val sharedPref = LocalContext.current.getSharedPreferences(
        "Score", Context.MODE_PRIVATE)
    with (sharedPref.edit())
    {
        putInt("Score", amethystScore)
        apply()
    }

    var highScore = sharedPref.getInt("Score", 0)
    val timer = remember {
        object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if ((counter == 3) and (millisUntilFinished <= 5000)) {
                    cancel()
                    counter = 0
                    amethystScore += 1
                }

            }

            override fun onFinish() {

                counter += 1

            }
        }
    }
    if(isOpened and isPressed)
    {
        timer.start()
    }
    else
    {
        counter=0
        timer.cancel()
    }
    Text(
        text = "Amethysts:$highScore",
        fontSize = 26.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.minecraft)),
        modifier = Modifier.padding(16.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
    {

        Image(
            painter = amethyst,
            contentDescription = "amethyst",
            Modifier
                .size(640.dp)

        )
        var show by rememberSaveable { mutableStateOf(true) }
        Button(
            modifier = Modifier
                .alpha(if (show) 1f else 0f),
            onClick =
            {
                isPressed = !isPressed
                show = !show

            },
            shape = RoundedCornerShape(0),
            enabled = if(show) {true} else {false}
        )
        {
            Text(
            text = "Start",
            fontFamily = FontFamily(Font(R.font.minecraft)),
            fontSize = 24.sp
        )
        }
    }
}

@Composable
fun Background(){
    val cave = painterResource(R.drawable.background)
    //Image - composable function for printing resources
    Image(
        painter = cave,
        contentDescription = "background",
        contentScale = ContentScale.FillHeight
    )
}
//попередній перегляд
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AntiprocrastinationTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        )
        {

            Background()
            Amethyst_growth(isOpened = true)
            Button(
                modifier = Modifier
                    .width(240.dp)
                    .height(60.dp)
                    .padding(start = 16.dp, bottom = 730.dp, top = 60.dp, end = 190.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(254, 189, 40)),
                onClick =
                {
                    /*TO DO*/
                },
                shape = RoundedCornerShape(0),
                enabled = true
            )
            {
                Text(
                    text = "Shop",
                    fontFamily = FontFamily(Font(R.font.minecraft)),
                    fontSize = 24.sp,

                )
            }
        }
        }
    }

