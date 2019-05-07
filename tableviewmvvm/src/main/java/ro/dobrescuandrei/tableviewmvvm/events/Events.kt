package ro.dobrescuandrei.tableviewmvvm.events

import android.view.View

class OnRowHeaderClickedEvent<ROW>
(
    val senderView : View,
    val row : ROW
)

class OnColumnHeaderClickedEvent<COLUMN>
(
    val senderView : View,
    val column : COLUMN
)

class OnCellClickedEvent<CELL>
(
    val senderView : View,
    val cell : CELL,
    val columnPosition : Int,
    val rowPosition : Int
)
