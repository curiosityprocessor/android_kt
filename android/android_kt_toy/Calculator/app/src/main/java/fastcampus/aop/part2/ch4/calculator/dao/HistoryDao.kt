package fastcampus.aop.part2.ch4.calculator.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fastcampus.aop.part2.ch4.calculator.model.History

@Dao
interface HistoryDao {

    @Query(value = "select * from history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query(value = "delete from history")
    fun deleteAll()

    @Delete
    fun deleteOne(history: History)

    @Query(value = "select * from history where result like :result limit 1")
    fun findByResult(result: String) : History
}