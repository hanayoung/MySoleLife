package com.aoreo.mysolelife.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.aoreo.mysolelife.R

class BoardListLVAdapter(val boardList : MutableList<BoardModel>): BaseAdapter() {
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
       return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var converView = convertView
        if(converView == null){
            converView = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item,parent,false)
        }

        val title = converView?.findViewById<TextView>(R.id.titleArea)
        title!!.text=boardList[position].title

        val content = converView?.findViewById<TextView>(R.id.contentArea)
        content!!.text=boardList[position].content


        return converView!!
    }
}