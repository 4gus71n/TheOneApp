package com.example.theoneapp

import android.app.Application
import android.content.Context
import android.content.Intent
import com.example.navigation.AppNavigationProvider
import com.example.chatexample.ui.main.ChatExampleActivity
import com.example.whatsapp.LetterComposeActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheOneApplication : Application(), AppNavigationProvider {
    override fun getChatExampleIntent(context: Context): Intent {
        return ChatExampleActivity.getStartIntent(context)
    }

    override fun getLetterComposeIntent(context: Context): Intent {
        return LetterComposeActivity.getStartIntent(context)
    }
}