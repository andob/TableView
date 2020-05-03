package com.evrencoskun.tableviewsample

import com.github.debop.kodatimes.startOfMonth
import io.reactivex.Single
import org.joda.time.DateTime
import ro.dobrescuandrei.mvvm.utils.DummyFilter
import ro.dobrescuandrei.tableviewmvvm.BaseTableViewModel
import ro.dobrescuandrei.tableviewmvvm.model.TableMatrix
import ro.dobrescuandrei.utils.yieldListOf
import java.util.*

class SecondViewModel : BaseTableViewModel<Int, String, Int, DummyFilter>(DummyFilter())
{
    override fun getData(filter : DummyFilter) = Single.fromCallable {
        val columns=getColumns(filter)
        val rows=getRows(filter)
        val cells=getCells(filter, rows, columns)
        TableMatrix(columns, rows, cells)
    }

    private fun getColumns(filter : DummyFilter) = yieldListOf<Int> {
        val dayOfMonth=DateTime.now().startOfMonth().dayOfMonth()
        for (day in dayOfMonth.minimumValue..dayOfMonth.maximumValue)
            yield(day)
    }

    private fun getRows(filter : DummyFilter) = yieldListOf<String> {
        for (i in 1..100)
            yield("test $i\nsubtitle p $i")
    }

    private fun getCells(filter: DummyFilter, rows: List<String>, columns: List<Int>) = yieldListOf<List<Int>> {
        val random=Random(System.currentTimeMillis())
        for (row in rows)
            yield(yieldListOf {
                for (column in columns)
                    yield(if (random.nextBoolean()) 0
                        else random.nextInt(10)+1)
            })
    }
}
