package com.example.imagesearch

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.adapter.PhotoItemAdapter
import com.example.imagesearch.adapter.SearchHistoryAdapter
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.viewmodel.LayoutType
import com.example.imagesearch.viewmodel.SearchViewModel
import com.example.imagesearch.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchViewModel: SearchViewModel by lazy {
        SearchViewModelFactory((application as ImageSearchApplication).database.searchHistoryDao()).create(
            SearchViewModel::class.java
        )
    }
    private val photoItemAdapter: PhotoItemAdapter by lazy {
        PhotoItemAdapter()
    }
    private val searchHistoryAdapter: SearchHistoryAdapter by lazy {
        SearchHistoryAdapter { searchHistory: SearchHistory ->
            searchViewModel.update(searchHistory.queryText)
            binding.searchView.hide()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchViewModel = searchViewModel
        binding.recyclerView.adapter = photoItemAdapter
        binding.historyRecyclerView.adapter = searchHistoryAdapter

        binding.lifecycleOwner = this

        binding.iconToggleButton.setOnClickListener {
            searchViewModel.toggleView()
            //TODO(move to viewmodel)
            if (searchViewModel.layoutType.value == LayoutType.LIST) {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
                binding.iconToggleButton.setImageResource(R.drawable.baseline_grid_view_24)
            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.iconToggleButton.setImageResource(R.drawable.baseline_view_list_24)
            }
            photoItemAdapter.setViewType(searchViewModel.layoutType.value!!)
        }

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.update(textView.text.toString())
                binding.searchView.hide()
            }
            false
        }

        lifecycle.coroutineScope.launch {
            searchViewModel.searchHistories.collect {
                searchHistoryAdapter.submitList(it)
            }
        }
    }
}
