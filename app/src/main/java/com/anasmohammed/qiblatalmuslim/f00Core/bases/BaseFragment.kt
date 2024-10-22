package com.anasmohammed.qiblatalmuslim.f00Core.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.dataStore.DataStoreHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    //binding
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper

    open fun onCreateViewExtra(savedInstanceState: Bundle?) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //binding
        if (_binding == null) {
            _binding = inflateBinding(inflater, container)
        }
        onCreateViewExtra(savedInstanceState)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        val superClass = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.firstOrNull() as? Class<*>
            ?: throw IllegalArgumentException("ViewBinding class not found.")

        val inflateMethod = superClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return inflateMethod.invoke(null, inflater, container, false) as VB
    }

    fun <T> Flow<T>.observeInLifecycle(collector: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect { uiState ->
                    collector(uiState)
                }
            }
        }
    }
}