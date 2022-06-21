package com.example.ajhomework.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ajhomework.httpRequest.HttpCallBack
import com.example.ajhomework.repository.DataRepository
import com.example.ajhomework.responseModel.DataModel
import com.example.ajhomework.responseModel.time

import com.google.gson.Gson


class MainViewModelModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}

class MainViewModel : ViewModel() {
    var page = 0
    var dataList = MutableLiveData<ArrayList<time>>()

    fun loadopendataList(context: Context) {
        DataRepository.getProductList(context,object :HttpCallBack{
            override fun onSuccess(jsonStr: String?) {
                var newJsonStr = jsonStr?.replace('-','/')
                val model = Gson().fromJson(newJsonStr, DataModel::class.java)
                val weatherElement = model.records.location[0].weatherElement
                var type :ArrayList<time> = arrayListOf()
                var fakesize : Int = 0

                for(i in weatherElement.indices){
                    if (weatherElement[i].elementName == "MinT"){
                        type = weatherElement[i].time as ArrayList<time>
                        break
                    }
                }
                var fakeList = weatherElement[0].time[0]
                fakeList.type = 1

                if (type.size % 2 == 0) {
                    fakesize = type.size + (type.size / 2)
                } else {
                    fakesize = type.size + 1
                }

                for (i in 0 until fakesize){
                    if(page - 2 ==0){
                        page = 0
                        type.addAll(i, listOf(fakeList))
                    }else{
                        type[i].type = 0
                        page += 1
                    }
//                    type[i].startTime =type[i].startTime .replace('-','/')
//                    type[i].endTime= type[i].endTime.replace('-','/')
                }
                dataList.postValue(type)
            }
            override fun onFail(msg: String?) {

            }

        })
    }
}