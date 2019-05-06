package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnCellClickedEvent

class TableCellViewHolder<CELL> : AbstractViewHolder
{
    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_cell
    }

    constructor(itemView: View?) : super(itemView)

    fun setData(cell : CELL, columnPosition : Int, rowPosition : Int)
    {
        val cellLabel=itemView.findViewById<TextView>(R.id.cellLabel)
        cellLabel.text=cell.toString()

        itemView.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        cellLabel.requestLayout()

        cellLabel.setOnClickListener {
            ForegroundEventBus.post(OnCellClickedEvent(cell, columnPosition, rowPosition))
        }
    }
}