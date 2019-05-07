package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnColumnHeaderClickedEvent

class TableColumnHeaderViewModel<COLUMN> : AbstractViewHolder
{
    constructor(itemView: View?) : super(itemView)

    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_column_header
    }

    fun setData(column : COLUMN,
                width : Int,
                height : Int,
                columnFormatter : ((COLUMN) -> (String))?)
    {
        val columnLabel=itemView.findViewById<TextView>(R.id.columnLabel)
        columnLabel.text=columnFormatter?.invoke(column)?:column.toString()

        itemView.layoutParams=RecyclerView.LayoutParams(width, height)
        itemView.setOnClickListener {
            ForegroundEventBus.post(OnColumnHeaderClickedEvent(
                senderView = itemView,
                column = column))
        }
    }
}
