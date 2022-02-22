package com.example.appselecttest.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appselecttest.R
import com.example.appselecttest.databinding.MovieItemForRecyclerBinding
import com.example.appselecttest.domain.model.MovieDomainModel

class MovieAdapter :
    ListAdapter<MovieDomainModel, MovieAdapter.MovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemForRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieDomainModel>() {

        override fun areItemsTheSame(oldItem: MovieDomainModel, newItem: MovieDomainModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: MovieDomainModel, newItem: MovieDomainModel) =
            oldItem == newItem
    }

    class MovieViewHolder(binding: MovieItemForRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val movieImage: ImageView = itemView.findViewById(R.id.posterImage)
        private val movieTitle: TextView = itemView.findViewById(R.id.titleText)
        private val movieDescription: TextView = itemView.findViewById(R.id.descriptionText)
        private val movieDate: TextView = itemView.findViewById(R.id.dateText)

        fun bind(movieItem: MovieDomainModel) {
            movieTitle.text = movieItem.displayTitle
            movieDescription.text = movieItem.summaryShort
            movieDate.text = movieItem.publicationDate

            Glide
                .with(itemView)
                .load(movieItem.imageUrl)
                .placeholder(R.drawable.ic_placeholder_image_24)
                .error(R.drawable.ic_error)
                .fallback(R.drawable.ic_placeholder_image_24)
                .centerCrop()
                .into(movieImage)
        }
    }
}

