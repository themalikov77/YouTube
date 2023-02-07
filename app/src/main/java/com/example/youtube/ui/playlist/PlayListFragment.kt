package com.example.youtube.ui.playlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youtube.R
import com.example.youtube.base.BaseFragment
import com.example.youtube.databinding.FragmentPlayListBinding
import com.example.youtube.model.Item

class PlayListFragment : BaseFragment<FragmentPlayListBinding, PlayListViewModel>() {
    private lateinit var adapter: PlayListAdapter
    private var playList = arrayListOf<Item>()
    override val viewModel: PlayListViewModel by lazy {
        ViewModelProvider(this)[PlayListViewModel::class.java]
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayListBinding {
        return FragmentPlayListBinding.inflate(inflater, container, false)
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.playLists().observe(this) {
            it.items?.let { it1 -> adapter.addData(it1) }
        }
    }

    override fun initView() {
        super.initView()
        adapter = PlayListAdapter(this::onClick, playList)
        binding.recyclerView.adapter = adapter
    }

    private fun onClick(item: Item) {
        findNavController().navigate(
            R.id.secondFragment,
            bundleOf("key" to item.id)
        )
    }
}
