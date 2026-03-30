package com.ecommerce.shoppy.config.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun isGranted(context: Context, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun requestPermission(activityResultLauncher: ActivityResultLauncher<String>, permission: String) {
        activityResultLauncher.launch(permission)
    }
}