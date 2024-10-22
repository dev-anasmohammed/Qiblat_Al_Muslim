package com.anasmohammed.qiblatalmuslim.f00Core.helpers.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelper @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun <T> get(key: Preferences.Key<T>): T? = dataStore.data.first()[key]

    fun <T> getAsFlow(key: Preferences.Key<T>): Flow<T?> =
        dataStore.data.map { snapshot -> snapshot[key] }

    suspend fun getAllKeysWithValues(): Map<Preferences.Key<*>, Any?>? {
        return dataStore.data.map { preferences ->
            preferences.asMap()
        }.firstOrNull()
    }

    suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun <T> update(key: Preferences.Key<T>, default: T, transform: (T) -> T): T {
        val snapshot = dataStore.edit { preferences ->
            preferences[key] = transform(preferences[key] ?: default)
        }
        return snapshot[key]!!
    }

    suspend fun <T> delete(key: Preferences.Key<T>): T? = deleteIf(key) { true }

    private suspend fun <T> deleteIf(key: Preferences.Key<T>, predicate: (T) -> Boolean): T? {
        var deletedValue: T? = null
        dataStore.edit { preferences ->
            val value = preferences[key]
            if (value != null && predicate(value)) {
                preferences.minusAssign(key)
                deletedValue = value
            }
        }

        return deletedValue
    }

    suspend fun batch(block: BatchScope.() -> Unit) {
        dataStore.edit {
            BatchScopeImpl(it).block()
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun clearButKeep(vararg keys: Preferences.Key<*>) {
        dataStore.edit { preferences ->
            val keyValues = keys.associateWith { key ->
                preferences[key]
            }

            preferences.clear()

            for ((key, value) in keyValues) {
                @Suppress("UNCHECKED_CAST") when (value) {
                    is Int -> preferences[intPreferencesKey(key.name)] = value
                    is Double -> preferences[doublePreferencesKey(key.name)] = value
                    is String -> preferences[stringPreferencesKey(key.name)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key.name)] = value
                    is Float -> preferences[floatPreferencesKey(key.name)] = value
                    is Long -> preferences[longPreferencesKey(key.name)] = value
                    is Set<*> -> preferences[stringSetPreferencesKey(key.name)] =
                        value as Set<String>
                }
            }
        }
    }
}

