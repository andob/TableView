package com.evrencoskun.tableviewsample

import android.os.Bundle
import android.view.View
import ro.dobrescuandrei.mvvm.utils.DummyFilter
import ro.dobrescuandrei.tableviewmvvm.BaseTableActivity
import ro.dobrescuandrei.tableviewmvvm.adapter.SimpleTableAdapter

class SecondActivity : BaseTableActivity<SecondViewModel, Int, String, Int, DummyFilter>()
{
    override fun viewModelClass() = SecondViewModel::class.java
    override fun layout() = R.layout.activity_second

    override fun onCellClicked(senderView: View, cell: Int, column: Int, row: String)
    {
        println("$cell $column $row")
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        tableView.setHasFixedWidth(true)
    }

    override fun provideAdapter() = object : SimpleTableAdapter<Int, String, Int>(this)
    {
        override fun getRowHeaderWidth()     = resources.getDimensionPixelSize(R.dimen.sa_row_header_width)
        override fun getRowHeaderHeight()    = resources.getDimensionPixelSize(R.dimen.sa_row_header_height)
        override fun getColumnHeaderWidth()  = resources.getDimensionPixelSize(R.dimen.sa_column_header_width)
        override fun getColumnHeaderHeight() = resources.getDimensionPixelSize(R.dimen.sa_column_header_height)
        override fun getCellWidth()          = resources.getDimensionPixelSize(R.dimen.sa_cell_width)
        override fun getCellHeight()         = resources.getDimensionPixelSize(R.dimen.sa_cell_height)

        override fun getCellFormatter() : ((Int) -> String)? = { cellValue ->
            if (cellValue>0) cellValue.toString()
            else "-"
        }
    }
}
