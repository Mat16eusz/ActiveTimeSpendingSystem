package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.databinding.ItemRankingBinding
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.utils.ONE

class RankingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var idSocialMedia: String? = null
    var items = mutableListOf<RankingDomain>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRankingBinding.inflate(layoutInflater, parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as RankingViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RankingViewHolder(
        private val binding: ItemRankingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RankingDomain) {
            binding.name.text = item.fullName
            binding.score.text = item.score.toString()
            binding.positionInRanking.text = (adapterPosition.plus(ONE)).toString()
            if (idSocialMedia.equals(item.idSocialMedia)) {
                binding.mainViewItem.setBackgroundResource(R.color.backgroundLightBlackColor)
            }
        }
    }
}