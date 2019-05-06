package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnColumnHeaderClickedEvent

class TableColumnHeaderViewModel<COLUMN> : AbstractViewHolder
{
    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_column_header
    }

    constructor(itemView: View?) : super(itemView)

    fun setData(column : COLUMN)
    {
        val columnLabel=itemView.findViewById<TextView>(R.id.columnLabel)
        columnLabel.text=column.toString()

        itemView.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        columnLabel.requestLayout()

        columnLabel.setOnClickListener {
            ForegroundEventBus.post(OnColumnHeaderClickedEvent(column))
        }
    }
}
