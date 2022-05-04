package com.example.diagonal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diagonal.data.repositories.BooksListRepositories


/**
 * Created by Noushad N on 04-05-2022.
 */

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(
    private val repository: BooksListRepositories
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainListViewModel(repository) as T
    }

}