package com.example.anti_procrastination

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo

import android.os.Bundle
import android.os.CountDownTimer

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.anti_procrastination.ui.theme.AntiprocrastinationTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString

import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

var score = 0

class MainActivity : ComponentActivity() {

    //створюємо систему перевірки чи знаходиться користувач у застосунку
    var isOpened by mutableStateOf(true)
    override fun onPause() {
        super.onPause()

        isOpened = false
        val toast = Toast.makeText(this, "Ріст зкинуто", Toast.LENGTH_SHORT)
        toast.show()
        Log.d("lifecycle", "stopped")
    }

    override fun onResume() {
        super.onResume()
        isOpened = true

        Log.d("lifecycle", "opened")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOpened = true
        setContent {
            AntiprocrastinationTheme {
                //навігація між екранами
                val context = LocalContext.current

                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
                        //оголошуємо кнопку, що перекидає на магазин
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
    //масив має у собі всі фази росту кристалу. За допомогою таймера лічильник збільшується і через це Image змінює свій resource
    val changer = arrayOf(R.drawable.phase_1, R.drawable.phase_2, R.drawable.phase_3, R.drawable.phase_4)
    var counter by rememberSaveable{
        mutableIntStateOf(0)
    }
    val sharedPref = LocalContext.current.getSharedPreferences(
        "com.example.antiprocrastination.SCORE_KEY", Context.MODE_PRIVATE)
    var amethystScore by rememberSaveable{
        mutableIntStateOf(sharedPref.getInt("com.example.antiprocrastination.SCORE_KEY", 0))
    }

    var isPressed by rememberSaveable { mutableStateOf(false) }
    var amethyst = painterResource(changer[counter])


    //через кожну 4-ту фазу збільшуємо кількість аметисту і виводимо у Text
    val timer = remember {
        object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
                if ((counter == 3) and (millisUntilFinished <= 5000))
                {
                    cancel()
                    counter = 0
                    amethystScore += 1
                    with (sharedPref.edit())
                    {
                        putInt("com.example.antiprocrastination.SCORE_KEY", amethystScore)
                        commit()
                    }

                }

            }

            override fun onFinish()
            {
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


    score = amethystScore
    Text(
        text = "Amethysts:$amethystScore",
        fontSize = 26.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.minecraft)),
        modifier = Modifier.padding(16.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
    {
        //зображення аметисту
        Image(
            painter = amethyst,
            contentDescription = "amethyst",
            Modifier
                .size(640.dp)

        )
        //кнопка початку. Не відображати, якщо натиснуто
        var show by rememberSaveable { mutableStateOf(true) }
        Button(
            modifier = Modifier
                .alpha(if (show) 1f else 0f)
                .size(width = 140.dp, height = 80.dp),
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(254, 189, 40)
                ),
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

