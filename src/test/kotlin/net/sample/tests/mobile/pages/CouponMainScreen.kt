package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement
import net.sample.page.Screen
import net.sample.page.ScreenFactory

class CouponMainScreen(): Screen() {

    @AndroidFindBy(xpath = "//spinner")
    lateinit var spinnerLabel: MobileElement

    init {
        ScreenFactory.initElements(mobileDriver!!, this.javaClass)
    }

    override fun waitForScreenIsLoaded() {
        spinnerLabel.waitForElementDissappear()
    }


}