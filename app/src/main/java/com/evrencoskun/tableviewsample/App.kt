package com.evrencoskun.tableviewsample

import android.app.Application
import com.franmontiel.localechanger.LocaleChanger
import net.danlew.android.joda.JodaTimeAndroid
import java.util.*

class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        JodaTimeAndroid.init(this)

        LocaleChanger.initialize(this, listOf(Locale.ENGLISH))
    }
}
