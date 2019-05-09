package ro.dobrescuandrei.tableviewmvvm.model

class TableMatrix<COLUMN, ROW, CELL>
(
    val columns : List<COLUMN>,
    val rows : List<ROW>,
    var cells : List<List<CELL>> = listOf()
)
{
    fun isEmpty() = cells.isEmpty()
}
