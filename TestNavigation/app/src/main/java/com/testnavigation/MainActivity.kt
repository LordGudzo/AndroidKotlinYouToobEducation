package com.testnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testnavigation.ui.theme.TestNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestNavigationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationFun()
                }
            }
        }
    }
}

@Composable
fun NavigationFun() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "parentScreen") {
        composable("parentScreen") {
            ParentScreen { text ->
                navController.navigate("childScreen/$text")
            }
        }
        composable("childScreen/{text}") {
            val text = it.arguments?.getString("text") ?: ""
            ChildScreen(text) { navController.navigate("parentScreen") }
        }
    }
}



