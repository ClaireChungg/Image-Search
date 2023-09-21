package com.example.imagesearch

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearch.adapter.PhotoItemAdapter
import com.example.imagesearch.adapter.SearchHistoryAdapter
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.viewmodel.LayoutType
import com.example.imagesearch.viewmodel.PhotoViewModel
import com.example.imagesearch.viewmodel.SearchHistoryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PhotoViewModel
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PhotoViewModel::class.java]
        searchHistoryViewModel = ViewModelProvider(this)[SearchHistoryViewModel::class.java]

        binding.viewModel = viewModel
        binding.recyclerView.adapter = PhotoItemAdapter()
        binding.searchHistoryViewModel = searchHistoryViewModel
        binding.historyRecyclerView.adapter = SearchHistoryAdapter { searchHistory: SearchHistory ->
            update(searchHistory.queryText)
        }
        binding.lifecycleOwner = this

        binding.iconToggleButton.setOnClickListener {
            viewModel.toggleView()
            if (viewModel.layoutType.value == LayoutType.LIST) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.iconToggleButton.setImageResource(R.drawable.baseline_grid_view_24)
            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.iconToggleButton.setImageResource(R.drawable.baseline_view_list_24)
            }
        }

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                update(textView.text.toString())
            }
            false
        }
//        TODO("clear search bar text on clear button click")
//        TODO("cache when search twice")
    }

    private fun insertDataToDatabase() {
        val queryText = viewModel.searchText.value.toString()
        val searchHistory = SearchHistory(0, queryText)
        searchHistoryViewModel.insert(searchHistory)
    }

    private fun update(queryText: String) {
        viewModel.searchText.value = queryText
        insertDataToDatabase()
        viewModel.getPhotoItems()
        binding.searchView.hide()
    }
}