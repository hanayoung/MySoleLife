package com.aoreo.mysolelife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.databinding.ActivityBoardEditBinding
import com.aoreo.mysolelife.utils.FBAuth
import com.aoreo.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardEditActivity : AppCompatActivity() {

    private lateinit var key : String

    private lateinit var binding : ActivityBoardEditBinding

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()

        getBoardData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }
    }

    private fun editBoardData(key:String){

        FBRef.boardRef
            .child(key)
            .setValue(
            BoardModel(binding.titleArea.text.toString(),
                binding.contentArea.text.toString(),
                writerUid,
                FBAuth.getTime()
            )
        )
        Toast.makeText(this,"수정완료",Toast.LENGTH_SHORT).show()
        finish()

    }
    private fun getImageData(key: String){
        val storageReference = Firebase.storage.reference.child(key+".png") // boardWrite에서 사용했던 경로 사용(이미지 저장 경로)

        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener{task ->
            if(task.isSuccessful){
                Glide.with(this /* context */)
                    .load(task.result)
                    .into(imageViewFromFB)
            }else{

            }
        }

    }

    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val dataModel = dataSnapshot.getValue(BoardModel::class.java) // 리스트로 오기 때문에 반복문 써줄 필요 x

                    binding.titleArea.setText(dataModel?.title)
                    binding.contentArea.setText(dataModel?.content) // time은 안하는 이유는 자동으로 받아오기 때문
                    writerUid = dataModel!!.uid

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}