package com.example.servicecenter.Domain.Utils
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://iqxhbyhwbcxygishldsx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlxeGhieWh3YmN4eWdpc2hsZHN4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE1ODAxMDMsImV4cCI6MjA1NzE1NjEwM30.MvOWopuo2WP7CHZzSyIjKStx8ylFvl0rqlh4LrsoNM8"
    ) {
        install(Postgrest)
        install(Auth)
    }

    val serviceItems = supabase.from("service_items")
}