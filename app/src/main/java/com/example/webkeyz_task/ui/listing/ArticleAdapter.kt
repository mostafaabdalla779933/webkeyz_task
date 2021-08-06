package com.example.webkeyz_task.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.NewsLayoutBinding
import com.example.webkeyz_task.model.ArticleModel
import com.example.webkeyz_task.util.getLoading
import com.example.webkeyz_task.util.setClickableAnimation
import kotlinx.android.synthetic.main.news_layout.view.*

class ArticleAdapter(val onclick : (ArticleModel) -> Unit) : ListAdapter<ArticleModel, ArticleAdapter.ArticleViewHolder>(
    Callback
){


    class ArticleViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun from(parent: ViewGroup): ArticleViewHolder {
            val binding: NewsLayoutBinding =
                NewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArticleViewHolder(binding.root)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onclick(getItem(position))
        }

        holder.itemView.tv_title.text = getItem(position).title
        

        Glide.with(holder.itemView.context)
            .load(getItem(position).urlToImage)
            .placeholder(getLoading(holder.itemView.context))
            .error(holder.itemView.context.getDrawable(R.drawable.ic_failed))
            .into(holder.itemView.iv_news)

        setClickableAnimation(holder.itemView.context,holder.itemView.cv_news)
    }


    companion object {
        private val Callback = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean =
                oldItem.publishedAt== newItem.publishedAt

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean =
                oldItem == newItem
        }
    }
}





