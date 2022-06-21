package com.example.ajhomework.httpRequest

interface HttpCallBack {
    fun onSuccess(jsonStr: String?)
    fun onFail(msg: String?)
}