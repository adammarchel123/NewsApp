package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

// RecyclerView merupakan salah satu elemen dari Android Material Design yang menggantikan ListView dan GridView.
// RecyclerView berguna untuk menampilkan sebuah set data yang berjumlah besar (ratusan — atau mungkin sampai jutaan)
// RecyclerView merupakan versi listview (scrollable list) yang lebih canggih
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    // View adalah obyek yang menggambar komponen tampilan ke layar yang mana pengguna dapat melihat dan berinteraksi langsung.
    // View adalah komponen yang terdapat di dalam ViewGroup
    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    // DiffUtil untuk RecyclerView agar lebih optimal dalam melakukan update data.
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    // ViewGroup adalah container atau wadah yang dapat menampung dan mengatur widget lainnya, bahkan ViewGroup itu sendiri
    // ViewGroup merupakan parent class dari layout di android, seperti LinearLayout, RelativeLayout, ConstraintLayout dan FrameLayout
    // ViewGroup adalah sebuah obyek yang mewadahi obyek-obyek View dan ViewGroup itu sendiri sehingga membentuk satu kesatuan tampilan aplikasi yang utuh
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            // LayoutInflater adalah salah satu class atau library yang digunakan ,untuk menjadikan atau mengconvert file layout xml ,sebagai View object baru ,di dalam layout utama
            // kita harus menggunakan LayoutInflater ,agar layout yang berisi konten (item_list.xml),dapat ditampilkan di dalam layout utama (actvity_main.xml).
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }



}