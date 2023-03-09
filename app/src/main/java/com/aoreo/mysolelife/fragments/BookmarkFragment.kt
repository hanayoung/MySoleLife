package com.aoreo.mysolelife.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.contentsList.BookmarkRVAdapter
import com.aoreo.mysolelife.contentsList.ContentModel
import com.aoreo.mysolelife.databinding.FragmentBookmarkBinding
import com.aoreo.mysolelife.utils.FBAuth
import com.aoreo.mysolelife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    private lateinit var binding:FragmentBookmarkBinding

    private val TAG = BookmarkFragment::class.java.simpleName //class명 저장

    lateinit var rvAdapter: BookmarkRVAdapter

    val bookmarkIdList = mutableListOf<String>()
    val items = ArrayList<ContentModel>()
    val itemKeyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)

        rvAdapter = BookmarkRVAdapter(requireContext(),items,itemKeyList,bookmarkIdList)

        val rv: RecyclerView = binding.bookmarkRV

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        getBookmarkData()

        binding.homeTap.setOnClickListener{
            Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        }
        binding.talkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)

        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)
        }
        return binding.root
    }
private fun getCategoryData(){
    items.clear()
    itemKeyList.clear()
    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.d("$TAG before",itemKeyList.toString())
            for (dataModel in dataSnapshot.children) {
                val item = dataModel.getValue(ContentModel::class.java)
                if(bookmarkIdList.contains(dataModel.key.toString())){
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                    Log.d("$TAG after",itemKeyList.toString())
                }
            }
            rvAdapter.notifyDataSetChanged()
        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    }
    FBRef.category1.addValueEventListener(postListener)
    FBRef.category2.addValueEventListener(postListener)
}
    private fun getBookmarkData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookmarkIdList.clear()
                for (dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                    Log.d("$TAG bookmark",bookmarkIdList.toString())
                }
               getCategoryData() // 컨텐츠 데이터 가져오기
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener) //각각 가지고있는 uid 값으로 Firebase에서 값 가져오기
    }
}