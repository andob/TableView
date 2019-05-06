package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnCellClickedEvent

class TableCellViewHolder<CELL> : AbstractViewHolder
{
    constructor(itemView: View?) : super(itemView)

    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_cell
    }

    fun setData(cell : CELL,
                columnPosition : Int,
                rowPosition : Int,
                width : Int,
                height : Int,
                cellFormatter : ((CELL) -> (String))?)
    {
        val cellLabel=itemView.findViewById<TextView>(R.id.cellLabel)
        cellLabel.text=cellFormatter?.invoke(cell)?:cell.toString()

        itemView.layoutParams=RecyclerView.LayoutParams(width, height)
        itemView.setOnClickListener {
            ForegroundEventBus.post(OnCellClickedEvent(cell, columnPosition, rowPosition))
        }
    }
}