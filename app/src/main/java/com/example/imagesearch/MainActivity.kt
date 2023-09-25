package com.example.imagesearch

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearch.adapter.PhotoItemAdapter
import com.example.imagesearch.adapter.SearchHistoryAdapter
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.viewmodel.LayoutType
import com.example.imagesearch.viewmodel.PhotoViewModel
import com.example.imagesearch.viewmodel.PhotoViewModelFactory
import com.example.imagesearch.viewmodel.SearchHistoryViewModel
import com.example.imagesearch.viewmodel.SearchHistoryViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val photoViewModel: PhotoViewModel by lazy {
        PhotoViewModelFactory().create(PhotoViewModel::class.java)
    }
    private val searchHistoryViewModel: SearchHistoryViewModel by lazy {
        ViewModelProvider(
            this,
            SearchHistoryViewModelFactory((application as ImageSearchApplication).database.searchHistoryDao())
        )[SearchHistoryViewModel::class.java]
    }
    private val photoItemAdapter: PhotoItemAdapter by lazy {
        PhotoItemAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = photoViewModel
        binding.recyclerView.adapter = photoItemAdapter
        binding.searchHistoryViewModel = searchHistoryViewModel
        binding.historyRecyclerView.adapter = SearchHistoryAdapter { searchHistory: SearchHistory ->
            update(searchHistory.queryText)
        }

        binding.lifecycleOwner = this

        binding.iconToggleButton.setOnClickListener {
            photoViewModel.toggleView()
            if (photoViewModel.layoutType.value == LayoutType.LIST) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.iconToggleButton.setImageResource(R.drawable.baseline_grid_view_24)
            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.iconToggleButton.setImageResource(R.drawable.baseline_view_list_24)
            }
            photoItemAdapter.setViewType(photoViewModel.layoutType.value!!)
        }

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                update(textView.text.toString())
            }
            false
        }

        lifecycle.coroutineScope.launch {
            searchHistoryViewModel.searchHistories.collect {
                (binding.historyRecyclerView.adapter as SearchHistoryAdapter).submitList(it)
            }
        }
    }

    private fun insertDataToDatabase() {
        val queryText = photoViewModel.searchText.value.toString()
        val searchHistory = SearchHistory(0, queryText)
        searchHistoryViewModel.insert(searchHistory)
    }

    private fun update(queryText: String) {
        photoViewModel.searchText.value = queryText
        insertDataToDatabase()
        photoViewModel.getPhotoItems()
        binding.searchView.hide()
    }
}
