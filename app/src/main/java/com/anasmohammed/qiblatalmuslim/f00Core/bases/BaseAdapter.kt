package com.anasmohammed.qiblatalmuslim.f00Core.bases

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseAdapter<T : Any, VB : ViewBinding>(
    private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean
) : ListAdapter<T, BaseAdapter.BaseViewHolder<VB>>(DiffCallback<T>()) {

    private lateinit var binding: VB

    class BaseViewHolder<VB : ViewBinding>(val view: VB) : RecyclerView.ViewHolder(view.root)

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val bindingClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VB>
        val method = bindingClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val binding = method.invoke(null, inflater, parent, false) as VB
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        onBindItemViewHolder(holder, position)

        holder.view.root.setOnClickListener {
            actionOnItemClick(getItem(position), position)
            onItemClickListener?.invoke(getItem(position))
            onItemClickListener2?.invoke(getItem(position), holder.view)
        }
    }

    protected open fun actionOnItemClick(item: T, position: Int) {}

    abstract fun onBindItemViewHolder(holder: BaseViewHolder<VB>, position: Int)

    class DiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((T) -> Unit)? = null
    fun setItemClickListener(listener: (T) -> Unit) {
        onItemClickListener = listener
    }

    private var onItemClickListener2: ((T, VB) -> Unit)? = null
    fun setItemClickListener(listener: (T, VB) -> Unit) {
        onItemClickListener2 = listener
    }
}
