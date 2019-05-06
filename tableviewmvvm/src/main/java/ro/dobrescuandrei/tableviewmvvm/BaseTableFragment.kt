package ro.dobrescuandrei.tableviewmvvm

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.evrencoskun.tableview.TableView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import org.greenrobot.eventbus.Subscribe
import ro.andreidobrescu.basefilter.BaseFilter
import ro.dobrescuandrei.mvvm.BaseFragment
import ro.dobrescuandrei.mvvm.eventbus.OnKeyboardClosedEvent
import ro.dobrescuandrei.mvvm.eventbus.OnKeyboardOpenedEvent
import ro.dobrescuandrei.mvvm.navigation.ARG_FILTER
import ro.dobrescuandrei.tableviewmvvm.adapter.SimpleTableAdapter

abstract class BaseTableFragment<VIEW_MODEL : BaseTableViewModel<*, *, *, FILTER>, ADAPTER : SimpleTableAdapter<*, *, *>, FILTER : BaseFilter> : BaseFragment<VIEW_MODEL>()
{
    lateinit var tableView : TableView
    lateinit var emptyView : TextView
    var addButton : FloatingActionButton? = null

    override fun loadDataFromArguments()
    {
        val initialFilter=arguments?.getSerializable(ARG_FILTER) as? FILTER
        if (initialFilter!=null) viewModel.filterLiveData.value=initialFilter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view=super.onCreateView(inflater, container, savedInstanceState)!!

        tableView=view.findViewById(R.id.tableView)
        emptyView=view.findViewById(R.id.emptyView)
        addButton=view.findViewById(R.id.addButton)

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

        tableView.adapter=provideAdapter()
        emptyView.text=provideEmptyViewText()

        viewModel.onCreate()

        viewModel.itemsLiveData.observe { items ->
            tableView.adapter?.setAllItems(items.columns, items.rows, items.cells)
            tableView.invalidate()
            tableView.requestLayout()
            tableView.scrollToRowPosition(0)
            viewModel.itemsLiveData.value=null
        }

        viewModel.isEmptyLiveData.observe { isEmpty ->
            if (isEmpty)
            {
                emptyView.visibility=View.VISIBLE
                tableView.visibility=View.GONE
            }
            else
            {
                emptyView.visibility=View.GONE
                tableView.visibility=View.VISIBLE
            }
        }

        viewModel.loadData()

        return view
    }

    abstract fun provideAdapter() : ADAPTER
    open fun provideEmptyViewText(): String = getString(R.string.no_items)

    override fun shouldFinishActivityOnBackPressed() : Boolean
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

                return false
            }

            return true
        }
        catch (e : Exception)
        {
            return true
        }
    }

    @Subscribe
    override fun onKeyboardOpened(event : OnKeyboardOpenedEvent)
    {
        addButton?.visibility=View.GONE
    }

    @Subscribe
    override fun onKeyboardClosed(event : OnKeyboardClosedEvent)
    {
        addButton?.visibility=View.VISIBLE
    }

    open fun onAddButtonClicked()
    {
    }
}
