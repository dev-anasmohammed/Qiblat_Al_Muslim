package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.entity.PrayerTimeEntity
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.dao.PrayerTimeDao

@Database(entities = [PrayerTimeEntity::class], version = 1)
abstract class PrayerTimeDataBase : RoomDatabase() {
    abstract fun PrayerTimeDao(): PrayerTimeDao
}