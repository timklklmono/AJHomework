package com.example.ajhomework.httpRequest

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ApiGetQuery(val context: Context, val url: String, val param: HashMap<String, Any>, val httpCallBack: HttpCallBack) {
    init {
        val header = HashMap<String, Any>().apply {
        }
        HttpGet(context, url, param, header, httpCallBack)
    }
}

class HttpGet(
    val context: Context?,
    val url: String,
    val param: HashMap<String, Any> = HashMap(),
    val header: HashMap<String, Any> = HashMap(),
    val httpCallBack: HttpCallBack
) {
    var tag = "ImApiGet "
    private fun httpGet() {
//        val client = OkHttpClient()
//            .newBuilder()
//            .addInterceptor(RequestWrapper.logging)
//            .sslSocketFactory(HttpUtils.initSSLSocketFactory(), HttpUtils.initTrustManager())
//            .build()

        val client = OkHttpClient()
            .newBuilder()
            .build()


        val request: Request = Request.Builder()
            .url(getQueryUrl())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (context?.haveInternet() == true) {
                    httpCallBack.onFail(e.message)
                }else {

                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val body = response.body!!.string()

                when(response.code) {
                    500 -> {
                        httpCallBack.onFail("Sorry, Http 500 Error")
                    }
                    200 -> {
                        httpCallBack.onSuccess(body)
                    }
                    else -> {
                        var errorMsg = getResponseError(body)
                        if (errorMsg!!.isEmpty()) {
                            errorMsg = getResponseMessage(body)
                            if (errorMsg!!.isEmpty()) {
                                errorMsg = body
                            }
                        }
                        httpCallBack.onFail(errorMsg)

                    }
                }
                response.body!!.close()
            }
        })
    }

    private fun getQueryUrl(): String {
        val builder: HttpUrl.Builder = url.toHttpUrlOrNull()!!.newBuilder()
        param.forEach { (key, value) ->
            builder.addQueryParameter(key, value.toString())
        }
        return builder.build().toString()
    }

    init {
        httpGet()
    }

    fun Context.haveInternet(): Boolean {
        var result = false
        val connManager = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connManager.activeNetworkInfo
        result = if (info == null || !info.isConnected) {
            false
        } else {
            info.isAvailable
        }
        return result
    }

    fun getResponseStatus(jsonObj: String?): String? {
        return getJsonValue(jsonObj, "KEY_STATUS")
    }

    fun getResponseMessage(jsonObj: String?): String? {
        return getJsonValue(jsonObj, "KEY_MESSAGE")
    }

    fun getResponseData(jsonObj: String?): String? {
        return getJsonValue(jsonObj, "KEY_DATA")
    }

    fun getResponseError(jsonObj: String?): String? {
        return getJsonValue(jsonObj, "KEY_ERROR")
    }

    fun getResponseAuth(jsonObj: String?): String? {
        return getJsonValue(jsonObj, "KEY_AUTH")
    }

    fun getJsonValue(jsonObj: String?, key: String?): String? {
        var value = ""
        try {
            val jsonObject = JSONObject(jsonObj)
            value = jsonObject.getString(key)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return value
    }


}