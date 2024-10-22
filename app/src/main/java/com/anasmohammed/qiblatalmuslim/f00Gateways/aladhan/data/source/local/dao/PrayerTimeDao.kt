package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.entity.PrayerTimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTimeDao {
    @Query("SELECT * FROM PRAYERTIMEENTITY")
    fun getAllPrayerTimes(): Flow<List<PrayerTimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prayerTimes: List<PrayerTimeEntity>)
}