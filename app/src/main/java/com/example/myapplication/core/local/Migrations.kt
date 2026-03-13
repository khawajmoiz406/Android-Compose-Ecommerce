package com.example.myapplication.core.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE `${DatabaseConfig.ORDER}` ADD COLUMN phone_number TEXT NOT NULL DEFAULT ''")
    }
}