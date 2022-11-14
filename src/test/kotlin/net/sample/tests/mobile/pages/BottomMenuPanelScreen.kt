package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement
import net.sample.page.ScreenFactory

class BottomMenuPanelScreen(): CouponBaseScreen() {

    @AndroidFindBy(accessibility = "Aktuell")
    private lateinit var mainFeedButton: MobileElement
    @AndroidFindBy(accessibility = "Coupons")
    private lateinit var couponsButton: MobileElement

    override fun waitForScreenIsLoaded() {
        TODO("Not yet implemented")
    }

    fun openCouponsScreen() : CouponsScreen {
        couponsButton.click()
        return ScreenFactory.initElements(mobileDriver, CouponsScreen::class.java)
    }

    fun openMainFeedScreen() : MainFeedScreen {
        mainFeedButton.click()
        return ScreenFactory.initElements(mobileDriver, MainFeedScreen::class.java)
    }

}