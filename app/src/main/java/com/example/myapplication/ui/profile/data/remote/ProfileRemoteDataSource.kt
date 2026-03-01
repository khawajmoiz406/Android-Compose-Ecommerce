package com.example.myapplication.ui.profile.data.remote

import android.content.Context
import com.example.myapplication.base.BaseDataSource
import com.example.myapplication.core.remote.ApiService

class ProfileRemoteDataSource(context: Context, private val apis: ApiService) :
    BaseDataSource(context)