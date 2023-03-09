package com.aoreo.mysolelife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.databinding.ActivityBoardInsideBinding
import com.aoreo.mysolelife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardInsideActivity : AppCompatActivity() {
    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_inside)

        // 첫번째 방법
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//
//        binding.titleArea.text=title
//        binding.textArea.text=content
//        binding.timeArea.text=time

        // 두 번째 방법

        val key = intent.getStringExtra("key")
        getBoardData(key.toString())
    }
    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java) // 리스트로 오기 때문에 반복문 써줄 필요 x
                if(dataModel!=null){
                    Log.d(TAG,dataModel.title)
                }
                binding.titleArea.text=dataModel!!.title
                binding.textArea.text=dataModel!!.content
                binding.timeArea.text=dataModel!!.time
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}