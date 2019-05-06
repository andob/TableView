package ro.dobrescuandrei.tableviewmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.adapter.viewholders.TableCellViewHolder
import ro.dobrescuandrei.tableviewmvvm.adapter.viewholders.TableColumnHeaderViewModel
import ro.dobrescuandrei.tableviewmvvm.adapter.viewholders.TableRowHeaderViewModel

abstract class SimpleTableAdapter<COLUMN, ROW, CELL> : AbstractTableAdapter<COLUMN, ROW, CELL>
{
    constructor(context: Context?) : super(context)

    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder =
            TableColumnHeaderViewModel<COLUMN>(LayoutInflater.from(mContext)
                    .inflate(TableColumnHeaderViewModel.LAYOUT, parent, false))

    override fun onBindColumnHeaderViewHolder(holder: AbstractViewHolder?, model: Any?, columnPosition: Int)
    {
        (holder as TableColumnHeaderViewModel<COLUMN>).setData(model as COLUMN)
    }

    override fun onCreateCellViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder =
            TableCellViewHolder<CELL>(LayoutInflater.from(mContext)
                    .inflate(TableCellViewHolder.LAYOUT, parent, false))

    override fun onBindCellViewHolder(holder: AbstractViewHolder?, model: Any?, columnPosition: Int, rowPosition: Int)
    {
        (holder as TableCellViewHolder<COLUMN>).setData(model as COLUMN, columnPosition, rowPosition)
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder =
            TableRowHeaderViewModel<ROW>(LayoutInflater.from(mContext)
                    .inflate(TableRowHeaderViewModel.LAYOUT, parent, false))

    override fun onBindRowHeaderViewHolder(holder: AbstractViewHolder?, model: Any?, rowPosition: Int)
    {
        (holder as TableRowHeaderViewModel<ROW>).setData(model as ROW)
    }

    override fun onCreateCornerView(): View = LayoutInflater.from(mContext)
            .inflate(R.layout.cell_table_corner, null)

    override fun getCellItemViewType(position: Int): Int = 0
    override fun getColumnHeaderItemViewType(position: Int): Int = 0
    override fun getRowHeaderItemViewType(position: Int): Int = 0


}
