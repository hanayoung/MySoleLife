package com.aoreo.mysolelife.utils

import com.google.firebase.auth.FirebaseAuth

class FBAuth {

    companion object{ // 함수와 변수를 함께 두기 위해 사용 (참고 : https://onlyfor-me-blog.tistory.com/441)

        private  lateinit var auth : FirebaseAuth

        fun getUid(): String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }
    }
}