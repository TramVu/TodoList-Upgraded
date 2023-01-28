package com.example.kotlinproject2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ScaffoldView() {
    val navControl = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { NavBottomBar(navControl)}
    ) {
        NavHost(navController = navControl, startDestination = "create_list" ){
            composable(route = "create_list"){ AddListForm() }
            composable(route = "show_list"){ ToDoListLayout() }


        }
    }
}

@Composable
fun TopBar() {
    Row (
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .background(Color(0xFFFF52D17D))
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        Text(
            text = "ToDoList",
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun NavBottomBar(navController: NavHostController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF52D17D))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(R.drawable.ic_account),
            contentDescription ="user",
            modifier = Modifier.clickable { navController.navigate(route="login") },
            tint = Color.White
        )
        Icon(
            painter = painterResource(R.drawable.ic_playlist_add),
            contentDescription ="add_toDo",
            modifier = Modifier.clickable { navController.navigate(route="create_list") },
            tint = Color.White
        )
        Icon(
            painter = painterResource(R.drawable.ic_view_list),
            contentDescription ="show_toDo",
            modifier = Modifier.clickable { navController.navigate(route="show_list") },
            tint = Color.White
        )
    }
}