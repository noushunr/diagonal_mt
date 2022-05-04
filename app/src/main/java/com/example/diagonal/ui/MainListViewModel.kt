package com.example.diagonal.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diagonal.data.model.Content
import com.example.diagonal.data.repositories.BooksListRepositories

/**
 * Created by Noushad N on 04-05-2022.
 */
class MainListViewModel(val repositories: BooksListRepositories) : ViewModel() {

    var mutableContents = MutableLiveData<MutableList<Content>>()
    var list : MutableList<Content> ? = mutableListOf()
    private var total = 1
    var pageNo = 1
    var loading = true
    fun getBookList(pageNo: Int){
        loading = true
//        if (total<list?.size!!){
//
//        }
//        list?.clear()
        var result = repositories.getBooksList(pageNo)
        if (result?.page != null ){
            if (pageNo == 1){
                total = result?.page?.totalContentItems?.toInt()!!
            }
            if (result?.page?.contentItems!=null){
                if (result?.page?.contentItems?.content!=null && result?.page?.contentItems?.content?.isNotEmpty()!!){
                    list?.addAll(result?.page?.contentItems?.content!!)
                    mutableContents.postValue(result?.page?.contentItems?.content!! as MutableList<Content>?)
                }
            }

        }

    }
}