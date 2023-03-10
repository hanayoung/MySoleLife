package com.aoreo.mysolelife.contentsList

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.utils.FBAuth
import com.aoreo.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import java.util.*

class ContentRVAdapter(
    val context: Context,
    val items:ArrayList<ContentModel>,
    val keyList : ArrayList<String>,
    val bookmarkIdList : MutableList<String>)
    :RecyclerView.Adapter<ContentRVAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item,parent,false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.Viewholder, position: Int) {

        holder.bindItems(items[position],keyList[position])
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ContentModel,key:String) { // layout의 rv_item의 item들을 가져오는 역할

        itemView.setOnClickListener {
            Toast.makeText(context,item.title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,ContentShowActivity::class.java)
            intent.putExtra("url",item.webUrl)
            itemView.context.startActivity(intent)
        }
        val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if(bookmarkIdList.contains(key)){
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            }else{
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }
            bookmarkArea.setOnClickListener {
                Log.d("ContentRVAdapter",FBAuth.getUid())
                Toast.makeText(context,key,Toast.LENGTH_SHORT).show()

                if(bookmarkIdList.contains(key)){

                    FBRef.bookmarkRef.child(FBAuth.getUid())
                        .child(key)
                        .removeValue()
                }else{
                    FBRef.bookmarkRef.child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookmarkModel(true))
                }

            }

            contentTitle.text=item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }
    }
}