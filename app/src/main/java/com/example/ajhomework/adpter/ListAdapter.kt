package com.example.ajhomework.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ajhomework.R
import com.example.ajhomework.responseModel.time
import kotlinx.android.synthetic.main.holder_list.view.*

class ListAdapter(private var dataList: ArrayList<time>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    private lateinit var onclick: setonClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_list, parent, false)
                ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_list_img, parent, false)
                ViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataList[position].type==0) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var model = dataList[position]

        if (model.type == 0) {
            holder.itemView.tv_startTime.text = model.startTime
            holder.itemView.tv_endTime.text = model.endTime
            holder.itemView.tv_spend.text = model.parameter.parameterName + model.parameter.parameterUnit
            holder.itemView.ly_item.setOnClickListener {
                onclick.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(dataList: ArrayList<time>) {
        this.dataList = dataList
        notifyItemRangeChanged(0,dataList.size)
    }

    interface setonClickListener {
        fun onClick(position: Int)
    }

    fun addonClickListener(onclick: setonClickListener) {
        this.onclick = onclick
    }
}