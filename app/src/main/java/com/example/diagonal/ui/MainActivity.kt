package com.example.diagonal.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diagonal.R
import com.example.diagonal.data.model.Content
import com.example.diagonal.databinding.ActivityMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.lang.Exception


class MainActivity : AppCompatActivity(), KodeinAware {
    private lateinit var binding : ActivityMainBinding
    override val kodein by kodein()
    private lateinit var viewModel: MainListViewModel
    private val factory: ListViewModelFactory by instance()
    var list : MutableList<Content> ? = mutableListOf()
    lateinit var adapter: MainListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory).get(MainListViewModel::class.java)
        setContentView(binding?.root)
        supportActionBar?.hide()
        var spanCount = resources.getInteger(R.integer.span_count)
        val layoutManager = GridLayoutManager(
            this,
            spanCount
        )
        adapter = MainListAdapter(this, mutableListOf())
        binding?.rlList.layoutManager = layoutManager
        binding.rlList.adapter = adapter
        if (viewModel.mutableContents?.value?.size == null) {

            viewModel.getBookList(viewModel.pageNo)
        }else{
            viewModel.loading = false
            adapter?.submitList(viewModel?.list!!)
        }
        viewModel.mutableContents?.observe(this) {
            if (viewModel.loading){
                list?.addAll(it)
                adapter?.submitList(it)
            }

        }
        binding?.rlList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (viewModel.pageNo<3){
                        viewModel.pageNo++
                        viewModel.getBookList(viewModel.pageNo)
                    }

                }
            }
        })
        binding?.ivSearch?.setOnClickListener {
            binding?.rlHeader?.visibility = View.GONE
            binding?.rlSearch?.visibility = View.VISIBLE
            binding?.etSearch?.requestFocus()
        }
        binding?.ivClose?.setOnClickListener {
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding?.etSearch?.windowToken, 0)
            }catch (e:Exception){

            }

            adapter?.filter.filter("")
            binding?.rlHeader?.visibility = View.VISIBLE
            binding?.rlSearch?.visibility = View.GONE

        }
        binding?.etSearch?.doAfterTextChanged {

            if (it?.length!!>2)
                adapter?.filter.filter(it)
            if (it?.length == 0)
                adapter?.filter.filter("")
        }
        binding?.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


}