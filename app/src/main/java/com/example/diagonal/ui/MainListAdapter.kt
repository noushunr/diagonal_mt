package com.example.diagonal.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diagonal.R
import com.example.diagonal.data.model.Content
import com.example.diagonal.databinding.ItemListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Noushad N on 04-05-2022.
 */
class MainListAdapter(
    private var mContext: Context,
    private var items: MutableList<Content>?
) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>(), Filterable {
    private var filteredItems: MutableList<Content>? = items
    private val adapterScope = CoroutineScope(Dispatchers.Default)
    private val diffCallBack = object : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(
            oldItem: Content,
            newItem: Content
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Content,
            newItem: Content
        ): Boolean {
            return oldItem.posterImage == newItem.posterImage
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Content>) {
        filteredItems?.addAll(list)
        notifyItemInserted(filteredItems?.size!!)
//        notifyDataSetChanged()
//        adapterScope.launch {
//            withContext(Dispatchers.Main) {
//                differ.submitList(list.toList())
//            }
//        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.binding?.txtBookName?.text = filteredItems!![position]?.name!!
        var image = mContext.resources.getIdentifier( filteredItems!![position]?.posterImage?.replace(".jpg",""), "drawable",
            mContext.packageName
        )

        try {
            Glide.with(mContext)
                .load(mContext.getDrawable(image))
                .placeholder(R.drawable.ic_placeholder)
                .into(holder?.binding?.imageView)
        }catch (e:Exception){
            Glide.with(mContext)
                .load(R.drawable.ic_placeholder)
                .into(holder?.binding?.imageView)
        }

    }

    override fun getItemCount(): Int {
        return filteredItems?.size!!
    }

    class ViewHolder(
        val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filteredItems = items else {
                    val filteredList = ArrayList<Content>()
                    items?.filter {
                        (it.name?.contains(constraint!!,ignoreCase = true)!!)

                    }?.forEach { filteredList.add(it) }
                    filteredItems = filteredList

                }
                return FilterResults().apply { values = filteredItems }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                filteredItems = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Content>
                notifyDataSetChanged()
            }

        }
    }
}
