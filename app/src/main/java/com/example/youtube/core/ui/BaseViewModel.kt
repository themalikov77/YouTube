package com.example.youtube.core.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val  loaderData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}
