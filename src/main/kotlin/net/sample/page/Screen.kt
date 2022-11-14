package net.sample.page

import net.sample.browser.DriverManager
import net.sample.browser.SeleniumDriver

abstract class Screen {

    val mobileDriver: SeleniumDriver = DriverManager.getMobileDriver()!!

    init {
        ScreenFactory.initElements(mobileDriver, this.javaClass)
    }

    abstract fun waitForScreenIsLoaded()

}