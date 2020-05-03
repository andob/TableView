package ro.dobrescuandrei.tableviewmvvm

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.evrencoskun.tableview.TableView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.miguelcatalan.materialsearchview.MaterialSearchView
import org.greenrobot.eventbus.Subscribe
import ro.andreidobrescu.basefilter.BaseFilter
import ro.dobrescuandrei.mvvm.BaseActivity
import ro.dobrescuandrei.mvvm.navigation.ARG_FILTER
import ro.dobrescuandrei.tableviewmvvm.adapter.SimpleTableAdapter
import ro.dobrescuandrei.tableviewmvvm.events.OnCellClickedEvent
import ro.dobrescuandrei.tableviewmvvm.events.OnColumnHeaderClickedEvent
import ro.dobrescuandrei.tableviewmvvm.events.OnRowHeaderClickedEvent

abstract class BaseTableActivity<VIEW_MODEL : BaseTableViewModel<COLUMN, ROW, CELL, FILTER>,
        COLUMN, ROW, CELL,
        FILTER : BaseFilter>
    : BaseActivity<VIEW_MODEL>()
{
    lateinit var tableView : TableView
    lateinit var emptyView : View
    var addButton : FloatingActionButton? = null

    override fun loadDataFromIntent()
    {
        val initialFilter=intent?.getSerializableExtra(ARG_FILTER) as? FILTER
        if (initialFilter!=null) viewModel.filterLiveData.value=initialFilter
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        tableView=findViewById(R.id.tableView)
        emptyView=findViewById(R.id.emptyView)
        addButton=findViewById(R.id.addButton)

        addButton?.setOnClickListener { onAddButtonClicked() }

        searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                if (!TextUtils.isEmpty(query))
                {
                    viewModel.notifyFilterChange { filter ->
                        filter.search=query
                    }

                    searchView?.closeSearch()

                    toolbar?.title="[$query] ${toolbar.title}"
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?)
    {
        super.onPostCreate(savedInstanceState)

        tableView.adapter=provideAdapter()

        viewModel.onCreate()

        viewModel.itemsLiveData.observe { items ->
            tableView.adapter?.setAllItems(items.columns, items.rows, items.cells)
            tableView.invalidate()
            tableView.requestLayout()
            tableView.scrollToRowPosition(0)
            tableView.scrollToColumnPosition(0)
            viewModel.itemsLiveData.value=null
        }

        viewModel.isEmptyLiveData.observe { isEmpty ->
            if (isEmpty)
            {
                emptyView.visibility= View.VISIBLE
                tableView.visibility= View.GONE
            }
            else
            {
                emptyView.visibility= View.GONE
                tableView.visibility= View.VISIBLE
            }
        }

        viewModel.loadData()
    }

    open fun provideAdapter() = SimpleTableAdapter<COLUMN, ROW, CELL>(this)

    override fun onBackPressed()
    {
        try
        {
            if (viewModel.filterLiveData.value.search!=null)
            {
                viewModel.notifyFilterChange { filter ->
                    filter.search=null
                }

                if (toolbar?.title?.startsWith('[')==true&&
                        toolbar?.title?.contains("] ")==true)
                    toolbar.title=toolbar.title.split("] ").lastOrNull()?:""
            }
            else
            {
                super.onBackPressed()
            }
        }
        catch (e : Exception)
        {
            super.onBackPressed()
        }
    }

    override fun onKeyboardOpened()
    {
        addButton?.visibility=View.GONE
        super.onKeyboardOpened()
    }

    override fun onKeyboardClosed()
    {
        addButton?.visibility=View.VISIBLE
        super.onKeyboardClosed()
    }

    open fun onAddButtonClicked()
    {
    }

    @Subscribe
    open fun onColumnClicked(event : OnColumnHeaderClickedEvent<COLUMN>) {}

    @Subscribe
    open fun onRowClicked(event : OnRowHeaderClickedEvent<ROW>) {}

    @Subscribe
    fun onCellClicked(event : OnCellClickedEvent<CELL>)
    {
        val adapter=tableView.adapter as SimpleTableAdapter<COLUMN, ROW, CELL>
        val row=adapter.getRowHeaderItem(event.rowPosition)
        val column=adapter.getColumnHeaderItem(event.columnPosition)

        onCellClicked(event.senderView, event.cell, column, row)
    }

    open fun onCellClicked(senderView : View, cell : CELL, column : COLUMN, row : ROW) {}
}
