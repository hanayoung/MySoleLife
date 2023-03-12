package com.aoreo.mysolelife.utils

import com.google.firebase.database.FirebaseDatabase

class FBRef {
    companion object{
        private val database = FirebaseDatabase.getInstance()

        val category1 = database.getReference("message")
        val category2 = database.getReference("category2")

        val bookmarkRef = database.getReference("bookmark_list")

        val boardRef = database.getReference("board")

        val commentRef = database.getReference("comment")
    }
}