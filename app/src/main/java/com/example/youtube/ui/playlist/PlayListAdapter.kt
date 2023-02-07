package com.example.youtube.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.databinding.ItemPlaylistBinding
import com.example.youtube.model.Item

class PlayListAdapter(
    private val onClick: (Item) -> Unit,
    private val playList: ArrayList<Item> = arrayListOf()
) :
    RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        return PlayListViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.bind(playList[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addData(data:ArrayList<Item>){
        this.playList.clear()
        this.playList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return playList.size
    }

    inner class PlayListViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playList: Item) = with(binding) {
            tvTitle.text = playList.snippet?.title
            tvDescription.text = playList.snippet?.description
        }
        init {
            itemView.setOnClickListener {
                onClick(playList[adapterPosition])
            }
        }
    }
}