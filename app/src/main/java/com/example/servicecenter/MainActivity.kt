package com.example.servicecenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.app.presentation.navigation.AppNavHost
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://iqxhbyhwbcxygishldsx.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlxeGhieWh3YmN4eWdpc2hsZHN4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE1ODAxMDMsImV4cCI6MjA1NzE1NjEwM30.MvOWopuo2WP7CHZzSyIjKStx8ylFvl0rqlh4LrsoNM8"
) {
    install(Postgrest)
    install(Auth)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController = navController)
        }
    }
}
