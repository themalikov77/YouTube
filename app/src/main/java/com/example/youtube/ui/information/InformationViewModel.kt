package com.example.youtube.ui.information

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.data.remote.model.PlayList

class InformationViewModel : BaseViewModel() {
    fun getInformationPlayList(playList: String?, itemCount: Int?): LiveData<Resource<PlayList>> {
        return App().repository.getInformationPlayList(playList, itemCount)
    }
}