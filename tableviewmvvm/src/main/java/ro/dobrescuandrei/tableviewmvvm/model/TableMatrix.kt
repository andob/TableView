package ro.dobrescuandrei.tableviewmvvm.model

class TableMatrix<COLUMN, ROW, CELL>
(
    val columns : List<COLUMN>,
    val rows : List<ROW>,
    val cells : List<List<CELL>>
)
{
    fun isEmpty() = cells.isEmpty()
}
