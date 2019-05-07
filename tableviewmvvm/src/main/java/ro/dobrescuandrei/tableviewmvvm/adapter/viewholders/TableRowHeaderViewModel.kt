package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnRowHeaderClickedEvent

class TableRowHeaderViewModel<ROW> : AbstractViewHolder
{
    constructor(itemView: View?) : super(itemView)

    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_row_header
    }

    fun setData(row : ROW,
                width : Int,
                height : Int,
                rowFormatter : ((ROW) -> (String))?)
    {
        val rowLabel=itemView.findViewById<TextView>(R.id.rowLabel)
        rowLabel.text=rowFormatter?.invoke(row)?:row.toString()

        itemView.layoutParams=RecyclerView.LayoutParams(width, height)
        itemView.setOnClickListener {
            ForegroundEventBus.post(OnRowHeaderClickedEvent(
                senderView = itemView,
                row = row))
        }
    }
}
