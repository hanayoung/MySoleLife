package com.aoreo.mysolelife.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.contentsList.BookmarkModel
import com.aoreo.mysolelife.databinding.ActivityBoardWriteBinding
import com.aoreo.mysolelife.utils.FBAuth
import com.aoreo.mysolelife.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener{

            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time=FBAuth.getTime()
            Log.d(TAG,title)
            Log.d(TAG,content)

            //board
            // - key
            //   - boardModel(title,content,uid,time)
            //push()는 랜덤값생성

            // 게시글에 해당하는 키값과 이미지의 이름을 동일하게 유지하기 위해 미리 키 값을 받아옴
           val key =FBRef.boardRef.push().key.toString()

            FBRef.boardRef
                .child(key) //미리 생성한 키 값을 바탕으로 Board 생성
                .setValue(BoardModel(title,content,uid,time))
            Toast.makeText(this,"게시글 작성 완료",Toast.LENGTH_SHORT).show()

            imageUpload()

            finish()

        }
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,100)
        }

    }

    private fun imageUpload(){
        // Get the data from an ImageView as bytes
        val imageView=binding.imageArea

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child("mountains.jpg")

        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode ==100){
            binding.imageArea.setImageURI(data?.data)
        }

    }
}