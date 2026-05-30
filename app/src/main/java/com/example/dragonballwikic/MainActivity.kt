package com.example.dragonballwikic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dragonballwikic.navigation.AppNavigation
import com.example.dragonballwikic.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Verificar si hay sesión activa
        val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
            Routes.SEARCH
        } else {
            Routes.LOGIN
        }

        setContent {
            AppNavigation(startDestination = startDestination)
        }
    }
}