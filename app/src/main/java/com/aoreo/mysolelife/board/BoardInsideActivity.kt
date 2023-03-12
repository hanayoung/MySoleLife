package com.aoreo.mysolelife.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.aoreo.mysolelife.MainActivity
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.comment.CommentLVAdapter
import com.aoreo.mysolelife.comment.CommentModel
import com.aoreo.mysolelife.databinding.ActivityBoardInsideBinding
import com.aoreo.mysolelife.fragments.TalkFragment
import com.aoreo.mysolelife.utils.FBAuth
import com.aoreo.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {
    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var key : String

    private lateinit var alertDialog: AlertDialog

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

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

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.boardSettingIcon.setOnClickListener {
            Log.d(TAG,"i'm clicked!")
            showDialog()
        }
         binding.commentBtn.setOnClickListener {
             insertComment(key)
         }
        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

        getCommentData(key)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        if(alertDialog != null&& alertDialog!!.isShowing){
//            alertDialog.dismiss()
//        }
//    } // dialog가 끝나면 사라지도록 조치해보기!

    private fun getCommentData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)
    }
    private fun insertComment(key: String){
     // comment
        // -BoardKey
        //    -CommentKey
        //     -CommentData
        //     -CommentData

        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(binding.commentArea.text.toString()))
        binding.commentArea.setText("")
    }
    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this,"aa",Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
            val intent = Intent(this,BoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)

        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            alertDialog.dismiss()
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this,"삭제완료",Toast.LENGTH_SHORT).show()

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun getImageData(key: String){
        val storageReference = Firebase.storage.reference.child(key+".png") // boardWrite에서 사용했던 경로 사용(이미지 저장 경로)

        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener{task ->
            if(task.isSuccessful){
                Glide.with(this /* context */)
                    .load(task.result)
                    .into(imageViewFromFB)
            }else{
                binding.getImageArea.isVisible=false
            }
        }

    }

    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try{
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java) // 리스트로 오기 때문에 반복문 써줄 필요 x
                    if(dataModel!=null){
                        Log.d(TAG,dataModel.title)
                    }
                    binding.titleArea.text=dataModel!!.title
                    binding.textArea.text=dataModel!!.content
                    binding.timeArea.text=dataModel!!.time

                    val myUid=FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid==writerUid){
                        binding.boardSettingIcon.isVisible = true
                    }else{

                    }
                }
                catch (e : java.lang.Exception){
                    Log.d(TAG,"삭제완료")
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}