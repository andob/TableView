package ro.dobrescuandrei.tableviewmvvm.adapter.viewholders

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import ro.dobrescuandrei.mvvm.eventbus.ForegroundEventBus
import ro.dobrescuandrei.tableviewmvvm.R
import ro.dobrescuandrei.tableviewmvvm.events.OnRowHeaderClickedEvent
import ro.dobrescuandrei.utils.Color
import ro.dobrescuandrei.utils.Colors

class TableRowHeaderViewModel<ROW> : AbstractViewHolder
{
    constructor(itemView: View) : super(itemView)

    companion object
    {
        val LAYOUT : Int = R.layout.cell_table_row_header
    }

    fun setData(row : ROW,
                width : Int,
                height : Int,
                rowFormatter : ((ROW) -> (String))?,
                rowBackgroundColorProvider : ((ROW) -> (Color))?)
    {
        val rowLabel=itemView.findViewById<TextView>(R.id.rowLabel)
        rowLabel.background=ColorDrawable(rowBackgroundColorProvider?.invoke(row)?.value?:Colors.Transparent.value)
        rowLabel.text=rowFormatter?.invoke(row)?:row.toString()

        itemView.layoutParams=RecyclerView.LayoutParams(width, height)
        itemView.setOnClickListener {
            ForegroundEventBus.post(OnRowHeaderClickedEvent(
                senderView = itemView,
                row = row))
        }
    }
}
