package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.common.BaseFragment
import com.mateusz.jasiak.activetimespendingsystem.databinding.FragmentRankingBinding

class RankingFragment : BaseFragment() {
    private lateinit var binding: FragmentRankingBinding
    override val viewModel: RankingViewModel by lazy {
        viewModelOf(
            RankingViewModel::class
        )
    }
    private val adapter: RankingAdapter by lazy {
        RankingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_ranking,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner

        binding.rankingRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.rankingRecyclerView.adapter = adapter

        viewModel.idSocialMedia = loadDataLogged()
        viewModel.getRankingsFromApi()

        initObserver()

        return binding.root
    }

    private fun initObserver() {
        viewModel.action.observe(viewLifecycleOwner) {
            when (it) {
                is RankingViewModel.Action.GetRankingsData -> {
                    adapter.idSocialMedia = viewModel.idSocialMedia
                    viewModel.rankingList?.let { rankingList ->
                        adapter.items = rankingList.toMutableList()
                    }
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }
}