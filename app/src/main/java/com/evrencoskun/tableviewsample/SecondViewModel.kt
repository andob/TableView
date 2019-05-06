package com.evrencoskun.tableviewsample

import com.github.debop.kodatimes.startOfMonth
import io.reactivex.Single
import org.joda.time.DateTime
import ro.dobrescuandrei.mvvm.utils.DummyFilter
import ro.dobrescuandrei.tableviewmvvm.BaseTableViewModel
import ro.dobrescuandrei.utils.yieldListOf
import java.util.*

class SecondViewModel : BaseTableViewModel<Int, String, Int, DummyFilter>(DummyFilter())
{
    override fun getColumns(filter : DummyFilter) = Single.fromCallable {
        yieldListOf<Int> {
            val dayOfMonth=DateTime.now().startOfMonth().dayOfMonth()
            for (day in dayOfMonth.minimumValue..dayOfMonth.maximumValue)
                yield(day)
        }
    }

    override fun getRows(filter : DummyFilter) = Single.fromCallable {
        yieldListOf<String> {
            for (i in 1..100)
                yield("test $i\nsubtitle p $i")
        }
    }

    override fun getCells(filter : DummyFilter) = Single.fromCallable {
        yieldListOf<List<Int>> {
            val random= Random(System.currentTimeMillis())
            for (i in 1..getRows(filter).blockingGet().size)
                yield(yieldListOf {
                    val dayOfMonth=DateTime.now().startOfMonth().dayOfMonth()
                    for (day in dayOfMonth.minimumValue..dayOfMonth.maximumValue)
                        yield(if (random.nextBoolean()) 0
                            else random.nextInt(10)+1)
                })
        }
    }
}
