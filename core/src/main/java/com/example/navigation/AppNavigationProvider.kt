package com.example.navigation

import android.content.Context
import android.content.Intent

/**
 * This interface provides the Intents to navigate between Activities from different feature modules
 * that don't know about each other.
 */
interface AppNavigationProvider {
    fun getChatExampleIntent(context: Context): Intent
}