package com.aoreo.mysolelife.contentsList

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.utils.FBAuth
import com.aoreo.mysolelife.utils.FBRef
import com.google.firebase.database.*
import java.util.*


class ContentsListActivity : AppCompatActivity() {
    lateinit var myRef: DatabaseReference // 초기화 지연 프로퍼티는 성능 이득

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter: ContentRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)


        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()


        rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList,bookmarkIdList)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()


        val category = intent.getStringExtra("category")

        if (category == "category1") { // category1은 TipFragment에서 putExtra로 넣어준 값
            Log.d("category1", category)
            myRef = database.getReference("message") // Firebase의 realtime database에 정해져있는 경로 이름
        } else if (category == "category2") {
            myRef = database.getReference("category2")
            Log.d("category2", category)
        }


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!! as ContentModel)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        myRef.addValueEventListener(postListener)

        val rv: RecyclerView = findViewById(R.id.rv)

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()
    }

    private fun getBookmarkData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookmarkIdList.clear()
                for (dataModel in dataSnapshot.children) {
                  bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("ContentListActivity",bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }

    }