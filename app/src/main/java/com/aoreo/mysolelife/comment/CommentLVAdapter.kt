package com.aoreo.mysolelife.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.utils.FBAuth

class CommentLVAdapter(val commentList : MutableList<CommentModel>): BaseAdapter(){
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var converView = convertView

        if(converView == null){
            converView = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_item,parent,false)
        }
        val title = converView?.findViewById<TextView>(R.id.titleArea)
        title!!.text=commentList[position].commentTitle

        val time = converView?.findViewById<TextView>(R.id.timeArea)
        time!!.text=commentList[position].commentCreatedTime


        return converView!!
    }

}