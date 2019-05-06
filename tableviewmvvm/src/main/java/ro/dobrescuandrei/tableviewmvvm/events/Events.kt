package ro.dobrescuandrei.tableviewmvvm.events

class OnRowHeaderClickedEvent<ROW>
(
    val row : ROW
)

class OnColumnHeaderClickedEvent<COLUMN>
(
    val column : COLUMN
)

class OnCellClickedEvent<CELL>
(
    val cell: CELL,
    val columnPosition : Int,
    val rowPosition : Int
)
