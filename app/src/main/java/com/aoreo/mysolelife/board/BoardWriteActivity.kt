package com.aoreo.mysolelife.board

import android.content.Intent
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
            FBRef.boardRef
                .push() //랜덤값생성
                .setValue(BoardModel(title,content,uid,time))
            Toast.makeText(this,"게시글 작성 완료",Toast.LENGTH_SHORT).show()

            finish()

        }
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}