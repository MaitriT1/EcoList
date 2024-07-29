package com.ecoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoapp.data.model.EcoItem
import com.ecoapp.data.model.EcoDetail
import com.ecoapp.domain.usecase.FetchEcoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchEcoDataUseCase: FetchEcoDataUseCase,
) : ViewModel() {

    private val _ecoItems = MutableStateFlow<List<EcoItem>>(emptyList())
    val ecoItems: StateFlow<List<EcoItem>> get() = _ecoItems.asStateFlow()

    private val _filteredItems = MutableStateFlow<List<EcoDetail>>(emptyList())
    val filteredItems: StateFlow<List<EcoDetail>> get() = _filteredItems.asStateFlow()

    init {
        fetchEcoData()
    }

    private fun fetchEcoData() {
        viewModelScope.launch {
            fetchEcoDataUseCase.execute().also { items ->
                _ecoItems.value = items
                if (items.isNotEmpty()) {
                    updateFilteredItems(0, null)
                }
            }
        }
    }

    fun updateFilteredItems(pageIndex: Int, query: String?) {
        _ecoItems.value.getOrNull(pageIndex)?.subItems?.let { items ->
            _filteredItems.value = if (query.isNullOrEmpty()) {
                items
            } else {
                items.filter {
                    it.title.contains(query, ignoreCase = true) || it.subTitle.contains(query, ignoreCase = true)
                }
            }
        } ?: run {
            _filteredItems.value = emptyList()
        }
    }

    fun getItemCountsForPage(pageIndex: Int): List<Pair<String, Int>> {
        return _ecoItems.value.getOrNull(pageIndex)?.let { item ->
            listOf("List ${pageIndex + 1}" to item.subItems.size)
        } ?: emptyList()
    }

    fun getTopCharactersForPage(pageIndex: Int): List<Pair<Char, Int>> {
        return _ecoItems.value.getOrNull(pageIndex)?.let { item ->
            calculateTopCharacters(item.subItems)
        } ?: emptyList()
    }

    private fun calculateTopCharacters(subItems: List<EcoDetail>): List<Pair<Char, Int>> {
        return subItems
            .flatMap { listOf(it.title, it.subTitle) }
            .joinToString(" ")
            .groupingBy { it }
            .eachCount()
            .filterKeys { it.isLetter() }
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .map { it.toPair() }
    }
}
