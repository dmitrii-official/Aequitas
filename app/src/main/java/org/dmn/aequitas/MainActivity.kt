package org.dmn.aequitas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.dmn.aequitas.navigation.DetailsNavDestination
import org.dmn.aequitas.navigation.HomeNavDestination
import org.dmn.aequitas.ui.templates.Details
import org.dmn.aequitas.ui.templates.Home
import org.dmn.aequitas.ui.theme.AequitasTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var darkTheme by remember { mutableStateOf(false) }

            AequitasTheme(darkTheme = darkTheme, dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeNavDestination,
                    ) {
                        composable<HomeNavDestination> {
                            darkTheme = false
                            Home(navController)
                        }
                        composable<DetailsNavDestination> {
                            darkTheme = true
                            Details(navController)
                        }
                    }
                }
            }
        }
    }
}
