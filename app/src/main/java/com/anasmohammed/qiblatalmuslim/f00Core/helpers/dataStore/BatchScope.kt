package com.anasmohammed.qiblatalmuslim.f00Core.helpers.dataStore

import androidx.datastore.preferences.core.Preferences

interface BatchScope {

    /**
     * Get the value associated to the given [key], if no such value exists returns `null`.
     */
    fun <T> get(key: Preferences.Key<T>): T?

    /**
     * Save the given [key]-[value] pair, overwrites any other saved value.
     */
    fun <T> save(key: Preferences.Key<T>, value: T)

    /**
     * Update the value associated to the given [key], if no such value exists then the [default]
     * value is used and passed to the [transform] lambda, returns the updated value.
     */
    fun <T> update(key: Preferences.Key<T>, default: T, transform: (T) -> T): T

    /**
     * Delete the value associated to the given [key] and return it, returns `null` if no
     * value was deleted.
     */
    fun <T> delete(key: Preferences.Key<T>): T?

    /**
     * Delete the value associated to the given [key] if [predicate] returns `true`, returns
     * the deleted value or `null` if no value was deleted because it didn't exist or [predicate]
     * returned `false`.
     */
    fun <T> deleteIf(key: Preferences.Key<T>, predicate: (T) -> Boolean): T?

    fun getAllKeysWithValues(): Map<Preferences.Key<*>, Any?>
}

