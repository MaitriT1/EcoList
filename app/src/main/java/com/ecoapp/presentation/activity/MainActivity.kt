package com.ecoapp.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ecoapp.R
import com.ecoapp.data.model.EcoDetail
import com.ecoapp.databinding.ActivityMainBinding
import com.ecoapp.presentation.adapter.EcoDetailAdapter
import com.ecoapp.presentation.adapter.EcoItemAdapter
import com.ecoapp.presentation.base.BaseActivity
import com.ecoapp.presentation.dialogs.StatsBottomSheetFragment
import com.ecoapp.presentation.viewmodel.MainViewModel
import com.ecoapp.utils.hide
import com.ecoapp.utils.hideKeyboard
import com.ecoapp.utils.reset
import com.ecoapp.utils.show
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
    get() = R.layout.activity_main

    private val viewModel: MainViewModel by viewModels()
    private lateinit var ecoDetailAdapter: EcoDetailAdapter
    private lateinit var ecoItemAdapter: EcoItemAdapter
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewPager()
        setupRecyclerView()
        attachObservers()
        setupSearch()
        setupFab()
    }

    private fun setupViewPager() {
        ecoItemAdapter = EcoItemAdapter(emptyList())
        binding.viewPager.adapter = ecoItemAdapter
        TabLayoutMediator(binding.pagerDots, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position

                hideKeyboard()
                binding.searchView.reset()

                viewModel.updateFilteredItems(currentPage, null)
            }
        })
    }

    private fun setupRecyclerView() {
       ecoDetailAdapter = EcoDetailAdapter(emptyList())
        binding.contentView.rvCarouselList.apply {
            adapter = ecoDetailAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun attachObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ecoItems.collect { carouselItems ->
                    ecoItemAdapter.updateItems(carouselItems)
                    if (carouselItems.isNotEmpty()) {
                        viewModel.updateFilteredItems(currentPage, null)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredItems.collect { items ->
                    ecoDetailAdapter.updateItems(items)
                    handleEmptyState(items)
                }
            }
        }
    }

    private fun setupSearch() {
        binding.searchView.apply {
            setOnCloseListener {
                viewModel.updateFilteredItems(currentPage, null)
                false
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.updateFilteredItems(currentPage, newText)
                    return true
                }
            })
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            val itemCounts = viewModel.getItemCountsForPage(currentPage)
            val topCharacters = viewModel.getTopCharactersForPage(currentPage)
            showStatsBottomSheet(itemCounts, topCharacters)
        }
    }

    private fun showStatsBottomSheet(
        itemCounts: List<Pair<String, Int>>,
        topCharacters: List<Pair<Char, Int>>
    ) {
        val statsFragment = StatsBottomSheetFragment(itemCounts, topCharacters)
        statsFragment.show(supportFragmentManager, statsFragment.tag)
    }

    private fun handleEmptyState(items: List<EcoDetail>) {
        if (items.isEmpty()) {
            binding.contentView.emptyView.show()
            binding.contentView.rvCarouselList.hide()
        } else {
            binding.contentView.emptyView.hide()
            binding.contentView.rvCarouselList.show()
        }
    }
}
