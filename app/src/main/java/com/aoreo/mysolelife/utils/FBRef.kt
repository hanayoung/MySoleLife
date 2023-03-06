package com.aoreo.mysolelife.utils

import com.google.firebase.database.FirebaseDatabase

class FBRef {
    companion object{
        private val database = FirebaseDatabase.getInstance()

        val bookmarkRef = database.getReference("bookmark_list")
    }
}