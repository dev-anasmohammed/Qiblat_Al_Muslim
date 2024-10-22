package com.anasmohammed.qiblatalmuslim.f00Core.helpers.dataStore

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences

class BatchScopeImpl(private val mutablePreferences: MutablePreferences) : BatchScope {

    override fun <T> get(key: Preferences.Key<T>): T? = mutablePreferences[key]

    override fun <T> save(key: Preferences.Key<T>, value: T) {
        mutablePreferences[key] = value
    }

    override fun getAllKeysWithValues(): Map<Preferences.Key<*>, Any?> {
        return mutablePreferences.asMap()
    }

    override fun <T> update(key: Preferences.Key<T>, default: T, transform: (T) -> T): T {
        return transform(mutablePreferences[key] ?: default).also { newValue ->
            mutablePreferences[key] = newValue
        }
    }

    override fun <T> delete(key: Preferences.Key<T>): T? =
        deleteIf(key) { true }

    override fun <T> deleteIf(key: Preferences.Key<T>, predicate: (T) -> Boolean): T? =
        mutablePreferences[key]?.let { value ->
            if (predicate(value)) {
                mutablePreferences.minusAssign(key)
                value
            } else {
                null
            }
        }
}