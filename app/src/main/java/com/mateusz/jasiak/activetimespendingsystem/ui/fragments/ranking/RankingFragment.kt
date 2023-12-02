package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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



        return binding.root
    }
}