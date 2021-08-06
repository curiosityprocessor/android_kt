package fastcampus.aop.part2.ch4.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import fastcampus.aop.part2.ch4.calculator.dao.HistoryDao
import fastcampus.aop.part2.ch4.calculator.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}