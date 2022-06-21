package com.example.ajhomework

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ajhomework.responseModel.time
import kotlinx.android.synthetic.main.activity_show.*


class ShowActivity() : AppCompatActivity() {
    companion object {
        fun launch(context: Context, dateset: time, position: Int) {
            val intent = Intent(context, ShowActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("dateset", dateset)
            intent.putExtras(bundle)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        init()

    }

    private fun init() {
        val bundle = this.intent.extras
        var list = bundle?.getSerializable("dateset") as time
        tv_startTime.text = list.startTime
        tv_endTime.text = list.endTime
        tv_spend.text = list.parameter.parameterName + list.parameter.parameterUnit
    }
}