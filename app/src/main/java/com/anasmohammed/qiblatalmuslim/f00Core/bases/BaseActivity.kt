package com.anasmohammed.qiblatalmuslim.f00Core.bases

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.dataStore.DataStoreHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper

    open fun onCreateExtra(savedInstanceState: Bundle?) {}

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            val type = superClass.actualTypeArguments[0] as Class<VB>
            val method = type.getMethod("inflate", LayoutInflater::class.java)
            _binding = method.invoke(null, layoutInflater) as VB
            setContentView(binding.root)
        }
        onCreateExtra(savedInstanceState)
    }

    fun <T> Flow<T>.observeInLifecycle(collector: (T) -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectLatest { uiState ->
                    collector(uiState)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}