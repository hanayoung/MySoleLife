package com.aoreo.mysolelife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.databinding.FragmentTalkBinding


class TalkFragment : Fragment() {
    private lateinit var binding:FragmentTalkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        binding.tipTap.setOnClickListener{
            Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }
        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)

        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }
        return binding.root
    }


}