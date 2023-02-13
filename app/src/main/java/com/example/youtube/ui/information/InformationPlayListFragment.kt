package com.example.youtube.ui.information

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.youtube.R
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.BaseFragment
import com.example.youtube.data.remote.model.Item
import com.example.youtube.data.remote.model.PlayListInformation
import com.example.youtube.databinding.FragmentInformationPlaylistBinding
import com.example.youtube.utils.CheckInternet


class InformationPlayListFragment :
    BaseFragment<FragmentInformationPlaylistBinding, InformationViewModel>() {
    private lateinit var testInternet: CheckInternet
    private lateinit var adapter: InformationAdapter
    private var playList: ArrayList<Item> = arrayListOf()
    val data: PlayListInformation by lazy {
        arguments?.getSerializable("key") as PlayListInformation
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInformationPlaylistBinding {
        return FragmentInformationPlaylistBinding.inflate(inflater, container, false)
    }

    override val viewModel: InformationViewModel by lazy {
        ViewModelProvider(this)[InformationViewModel::class.java]
    }

    override fun checkInternet() {
        super.checkInternet()
        testInternet = CheckInternet(requireActivity().application)
        testInternet.observe(viewLifecycleOwner) { isConnected ->
            binding.internetContainer.isVisible = isConnected
            binding.recyclerView.isVisible = !isConnected
            binding.coordinatorContainer.isVisible = !isConnected
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        adapter = InformationAdapter()
        binding.recyclerView.adapter = adapter
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.desc
        binding.tvVideo.text = data.itemCount.toString() + getString(R.string.video)
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.getInformationPlayList(data.id, data.itemCount)
            .observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        playList = it.data!!.items!!
                        adapter.addData(playList)
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
    }

    override fun initListeners() {
        super.initListeners()
        binding.toolbar.tvBack.setOnClickListener {
            findNavController().navigate(R.id.playListFragment)
        }
    }

}