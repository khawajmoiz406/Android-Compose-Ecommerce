package com.example.myapplication.config.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object AppUtils {
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", text)
        clipboard.setPrimaryClip(clip)
    }
}