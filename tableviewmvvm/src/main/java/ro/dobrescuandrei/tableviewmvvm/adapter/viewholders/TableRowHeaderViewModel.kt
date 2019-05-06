package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnRowHeaderClickedEvent

class TableRowHeaderViewModel<ROW> : AbstractViewHolder
{
    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_row_header
    }

    constructor(itemView: View?) : super(itemView)

    fun setData(row : ROW)
    {
        val rowLabel=itemView.findViewById<TextView>(R.id.rowLabel)
        rowLabel.text=row.toString()

        itemView.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        rowLabel.requestLayout()

        rowLabel.setOnClickListener {
            ForegroundEventBus.post(OnRowHeaderClickedEvent(row))
        }
    }
}
