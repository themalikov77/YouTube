package com.example.youtube.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youtube.R
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.BaseFragment
import com.example.youtube.databinding.FragmentPlayListBinding
import com.example.youtube.data.remote.model.Item
import com.example.youtube.data.remote.model.PlayListInformation
import com.example.youtube.utils.CheckInternet

class PlayListFragment : BaseFragment<FragmentPlayListBinding, PlayListViewModel>() {
    private lateinit var adapter: PlayListAdapter
    private lateinit var testInternet: CheckInternet
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
        viewModel.getPlayLists(viewModel.pageToken).observe(this) {
            it.data?.items?.let { it1 ->
                adapter.addData(it1)
            }
            loadPlayList()
        }
    }

    private fun loadPlayList() {
        viewModel.getPlayLists(viewModel.pageToken).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.pageToken = it.data?.nextPageToken
                    viewModel.totalCount = it.data?.pageInfo?.totalResults ?: 0
                    it.data?.items?.let { it1 -> adapter.addData(it1) }
                    binding.loading.isVisible = false
                    binding.loadingData.isVisible = false

                }
                Status.LOADING -> {
                    viewModel.loaderData.value = false
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    viewModel.loaderData.value = false
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        adapter = PlayListAdapter(this::onClick, playList)
        binding.recyclerView.adapter = adapter
        setUpPagination()
    }

    override fun checkInternet() {
        super.checkInternet()
        testInternet = CheckInternet(requireActivity().application)
        testInternet.observe(viewLifecycleOwner) { isConnected ->
            binding.internetContainer.isVisible = isConnected
            binding.recyclerView.isVisible = !isConnected
        }
    }

    private fun onClick(item: Item) {
        findNavController().navigate(
            R.id.playlistInformationFragment,
            bundleOf(
                "key" to PlayListInformation(
                    item.id,
                    item.snippet?.title,
                    item.snippet?.description,
                    item.contentDetails?.itemCount
                )
            )
        )
    }

    private fun setUpPagination() {
        binding.nestedScroll.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            val nv = v as NestedScrollView
            if (scrollY == nv.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.loadingData.isVisible = true
                loadPlayList()

            }
        }
    }
}



