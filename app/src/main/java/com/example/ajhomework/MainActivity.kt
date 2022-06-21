package com.example.ajhomework

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ajhomework.adpter.ListAdapter
import com.example.ajhomework.responseModel.time
import com.example.ajhomework.viewmodel.MainViewModel
import com.example.ajhomework.viewmodel.MainViewModelModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileOutputStream
import java.util.*
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private val dataList = ArrayList<time>()
    private lateinit var listAdapter: ListAdapter
//    val viewModel = MainViewModel()
    private lateinit var ViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewModel = ViewModelProvider(this, MainViewModelModelFactory())[MainViewModel::class.java]
        checkfirst()
        getListData()
        initRvView()
    }

    private fun checkfirst() {
        val getRecord = getSharedPreferences("record", MODE_PRIVATE)
            .getString("Field", "null");

        if (getRecord.equals("null")) {
            val record = getSharedPreferences("record", MODE_PRIVATE)
            record.edit()
                .putString("Field", "first")
                .apply()
        }else{
            Toast.makeText(applicationContext, "歡迎回來", Toast.LENGTH_LONG).show();
        }
    }

    private fun initRvView() {
        listAdapter = ListAdapter(dataList)
        ry_list.layoutManager = LinearLayoutManager(this)
        ry_list.itemAnimator?.changeDuration = 0
        ry_list.adapter = listAdapter

        ViewModel.dataList.observe({ lifecycle }) {
            try {
                listAdapter.updateData(it)
                listAdapter.addonClickListener(object : ListAdapter.setonClickListener{
                    override fun onClick(position: Int) {
                        ShowActivity.launch(
                            this@MainActivity,
                            it[position],
                            position
                        )
                    }
                })
            } catch (e: NullPointerException) {

            }
        }
    }

    private fun getListData() {
        ViewModel.loadopendataList(this)
    }

}