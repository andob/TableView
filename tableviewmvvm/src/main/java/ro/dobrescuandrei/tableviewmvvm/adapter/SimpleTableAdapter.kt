package ro.dobrescuandrei.tableviewmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.adapter.viewholders.TableCellViewHolder
import ro.dobrescuandrei.tableviewmvvm.adapter.viewholders.TableColumnHeaderViewModel
import ro.dobrescuandrei.tableviewmvvm.adapter.viewholders.TableRowHeaderViewModel
import ro.dobrescuandrei.utils.Color

open class SimpleTableAdapter<COLUMN, ROW, CELL>
(
    val context : Context
) : AbstractTableAdapter<COLUMN, ROW, CELL>()
{
    override fun onCreateColumnHeaderViewHolder(parent : ViewGroup, viewType : Int) =
        TableColumnHeaderViewModel<COLUMN>(LayoutInflater.from(context)
            .inflate(TableColumnHeaderViewModel.LAYOUT, parent, false))

    override fun onBindColumnHeaderViewHolder(holder : AbstractViewHolder, columnHeaderItemModel : COLUMN?, columnPosition : Int)
    {
        (holder as TableColumnHeaderViewModel<COLUMN>).setData(
            column = columnHeaderItemModel!!,
            width = getColumnHeaderWidth(),
            height = getColumnHeaderHeight(),
            columnFormatter = getColumnFormatter())
    }

    override fun onCreateCellViewHolder(parent : ViewGroup, viewType : Int) =
        TableCellViewHolder<CELL>(itemView = LayoutInflater.from(context)
            .inflate(TableCellViewHolder.LAYOUT, parent, false))

    override fun onBindCellViewHolder(holder : AbstractViewHolder, cellItemModel : CELL?, columnPosition : Int, rowPosition : Int)
    {
        (holder as TableCellViewHolder<CELL>).setData(
            cell = cellItemModel!!,
            columnPosition = columnPosition,
            rowPosition = rowPosition,
            width = getCellWidth(),
            height = getCellHeight(),
            cellFormatter = getCellFormatter())
    }

    override fun onCreateRowHeaderViewHolder(parent : ViewGroup, viewType : Int) =
        TableRowHeaderViewModel<ROW>(LayoutInflater.from(context)
            .inflate(TableRowHeaderViewModel.LAYOUT, parent, false))

    override fun onBindRowHeaderViewHolder(holder : AbstractViewHolder, rowHeaderItemModel : ROW?, rowPosition : Int)
    {
        (holder as TableRowHeaderViewModel<ROW>).setData(
            row = rowHeaderItemModel!!,
            width = getRowHeaderWidth(),
            height = getRowHeaderHeight(),
            rowFormatter = getRowFormatter(),
            rowBackgroundColorProvider = getRowBackgroundColorProvider())
    }

    override fun onCreateCornerView(parent : ViewGroup) : View =
        LayoutInflater.from(context).inflate(R.layout.cell_table_corner, null)

    override fun getCellItemViewType(position: Int): Int = 0
    override fun getColumnHeaderItemViewType(position: Int): Int = 0
    override fun getRowHeaderItemViewType(position: Int): Int = 0

    open fun getRowHeaderWidth()     : Int = FrameLayout.LayoutParams.WRAP_CONTENT
    open fun getRowHeaderHeight()    : Int = FrameLayout.LayoutParams.WRAP_CONTENT
    open fun getColumnHeaderWidth()  : Int = FrameLayout.LayoutParams.WRAP_CONTENT
    open fun getColumnHeaderHeight() : Int = FrameLayout.LayoutParams.WRAP_CONTENT
    open fun getCellWidth()          : Int = FrameLayout.LayoutParams.WRAP_CONTENT
    open fun getCellHeight()         : Int = FrameLayout.LayoutParams.WRAP_CONTENT

    open fun getRowFormatter()    : ((ROW)    -> (String))? = null
    open fun getColumnFormatter() : ((COLUMN) -> (String))? = null
    open fun getCellFormatter()   : ((CELL)   -> (String))? = null

    open fun getRowBackgroundColorProvider() : ((ROW) -> Color)? = null
}
