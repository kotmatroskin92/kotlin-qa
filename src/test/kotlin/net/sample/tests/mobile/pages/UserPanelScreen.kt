package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement

class UserPanelScreen(): CouponBaseScreen()  {

    @AndroidFindBy(id = "my_account_greetings")
    private lateinit var accountNameLabel: MobileElement
    @AndroidFindBy(id = "more_btn_logout")
    private lateinit var logoutButton: MobileElement


    override fun waitForScreenIsLoaded() {
        accountNameLabel.isExists()
    }

    fun logout() {
        logoutButton.swipeDownUntilElement().click()
    }
}