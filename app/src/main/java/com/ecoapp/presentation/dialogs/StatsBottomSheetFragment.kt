package com.ecoapp.presentation.dialogs

import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecoapp.databinding.FragmentStatsBottomSheetBinding
import com.ecoapp.utils.ITEM_STATS_FORMAT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@AndroidEntryPoint
class StatsBottomSheetFragment(
    private val itemCounts: List<Pair<String, Int>>,
    private val topCharacters: List<Pair<Char, Int>>
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentStatsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayStatistics()
    }

    private fun displayStatistics() {
        val itemStats = itemCounts.joinToString("\n") { ITEM_STATS_FORMAT.format(it.first, it.second) }
        val characterStats = topCharacters.joinToString("\n") { "${it.first} = ${it.second}" }

        with(binding) {
            itemCountTextView.text = itemStats
            characterCountTextView.text = characterStats
        }
    }

}
