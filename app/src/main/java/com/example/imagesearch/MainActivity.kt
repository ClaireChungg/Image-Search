package com.example.imagesearch

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearch.adapter.ItemAdapter
import com.example.imagesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PhotoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemAdapter()
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

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchText.value = textView.text.toString()
                viewModel.getPhotoItems()
                binding.searchView.hide()
            }
            false
        }
//        TODO("clear search bar text on clear button click")
//        TODO("cache when search twice")
    }
}