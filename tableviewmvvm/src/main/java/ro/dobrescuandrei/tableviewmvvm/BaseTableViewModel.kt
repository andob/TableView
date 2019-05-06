package ro.dobrescuandrei.tableviewmvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ro.andreidobrescu.basefilter.BaseFilter
import ro.dobrescuandrei.mvvm.BaseViewModel
import ro.dobrescuandrei.mvvm.utils.NonNullableLiveData
import ro.dobrescuandrei.mvvm.utils.notify
import ro.dobrescuandrei.tableviewmvvm.model.TableMatrix

abstract class BaseTableViewModel<COLUMN, ROW, CELL, FILTER : BaseFilter> : BaseViewModel
{
    val filterLiveData : NonNullableLiveData<FILTER>

    constructor(filter : FILTER)
    {
        filterLiveData=NonNullableLiveData(initialValue = filter)
    }

    val itemsLiveData : MutableLiveData<TableMatrix<COLUMN, ROW, CELL>> by lazy { MutableLiveData<TableMatrix<COLUMN, ROW, CELL>>() }
    val isEmptyLiveData : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    abstract fun getColumns(filter : FILTER) : Single<List<COLUMN>>
    abstract fun getRows(filter : FILTER) : Single<List<ROW>>
    abstract fun getCells(filter : FILTER) : Single<List<List<CELL>>>

    override fun onCreate()
    {
        super.onCreate()

        itemsLiveData.value=null
        isEmptyLiveData.value=false
    }

    @SuppressLint("CheckResult")
    fun loadData()
    {
        filterLiveData.value.offset=0

        showLoading()

        Single.fromCallable {
            TableMatrix(
                columns = getColumns(filterLiveData.value).blockingGet(),
                rows = getRows(filterLiveData.value).blockingGet(),
                cells = getCells(filterLiveData.value).blockingGet())
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(onError = { ex ->
            showError(ex)
            hideLoading()
            isEmptyLiveData.value=true
        }, onSuccess = { items ->
            hideLoading()
            itemsLiveData.value=items
            isEmptyLiveData.value=items.isEmpty()
        })
    }

    fun notifyFilterChange(consumer : (FILTER) -> (Unit))
    {
        consumer(filterLiveData.value)
        filterLiveData.notify()

        loadData()
    }
}
