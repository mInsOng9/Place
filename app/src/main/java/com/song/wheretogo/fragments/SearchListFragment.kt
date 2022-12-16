package com.song.wheretogo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.wheretogo.activities.MainActivity
import com.song.wheretogo.adapters.PlaceListRecyclerAdapter
import com.song.wheretogo.databinding.FragmentSearchListBinding


class SearchListFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val binding: FragmentSearchListBinding by lazy { FragmentSearchListBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ///Mainactivity 참조하기
        val ma=activity as MainActivity

        //아직 MainActivity 에서 파싱작업이 완료되지 않았다면 데이터가 없음
        if()return;
        binding.recyclerView.adapter=PlaceListRecyclerAdapter(requireContext(),ma.searchPlaceResponse)
    }

}