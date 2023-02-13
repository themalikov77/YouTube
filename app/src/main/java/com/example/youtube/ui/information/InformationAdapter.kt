package com.example.youtube.ui.information

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube.data.remote.model.Item
import com.example.youtube.databinding.ItemRecyclerBinding

class InformationAdapter : RecyclerView.Adapter<InformationAdapter.InformationViewHolder>() {

    private var informationList: ArrayList<Item> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        return InformationViewHolder(
            ItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.bind(informationList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<Item>) {
        informationList.clear()
        informationList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return informationList.size
    }

    class InformationViewHolder(private val binding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.tvTitle.text = item.snippet?.title
            binding.tvDescription.text = item.snippet?.publishedAt

            Glide.with(binding.ivPhoto).load(item.snippet?.thumbnails?.medium?.url)
                .into(binding.ivPhoto)
        }
    }
}