package com.example.youtube.ui.playlist


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube.databinding.ItemPlaylistBinding
import com.example.youtube.data.remote.model.Item

class PlayListAdapter(
    private val onClick: (Item) -> Unit,
    private var playList: ArrayList<Item> = arrayListOf()
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

    fun addData(data: List<Item>) {
        playList.addAll(data)
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemCount(): Int {
        return playList.size
    }

    inner class PlayListViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(playList: Item) = with(binding) {
            tvTitle.text = playList.snippet?.title
            tvDescription.text = playList.contentDetails?.itemCount.toString() + " video series"
            Glide.with(binding.ivPhoto).load(playList.snippet?.thumbnails?.medium?.url)
                .into(binding.ivPhoto)
        }

        init {
            itemView.setOnClickListener {
                onClick(playList[adapterPosition])
            }
        }
    }
}