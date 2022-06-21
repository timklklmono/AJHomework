package com.example.ajhomework.repository

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ajhomework.httpRequest.ApiGetQuery
import com.example.ajhomework.httpRequest.HttpCallBack
import com.example.ajhomework.responseModel.DataModel
import com.example.ajhomework.responseModel.time
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.holder_list.view.*
import org.json.JSONObject
import java.util.HashMap

object DataRepository {

    fun getProductList(context: Context, httpCallBack: HttpCallBack) {
        val param: HashMap<String, Any> = HashMap<String, Any>()
        param["Authorization"] = "CWB-E2D54EA8-E658-42E6-8E4E-037F4B4ABC02"
        param["format"] = "JSON"
        param["locationName"] = "臺北市"
        ApiGetQuery(
            context,
            "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001",
            param,
            object : HttpCallBack {
                override fun onSuccess(jsonStr: String?) {
                    val jsonObject = JSONObject(jsonStr)

                    val success = jsonObject.getString("success")
                    if (success == "true") {
                        httpCallBack.onSuccess(jsonStr)
                    }
                }
                override fun onFail(msg: String?) {

                }
            }
        )
    }
}