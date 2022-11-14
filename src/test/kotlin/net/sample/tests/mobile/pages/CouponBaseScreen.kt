package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement
import net.sample.page.Screen
import net.sample.page.ScreenFactory
import org.junit.Assert

open class CouponBaseScreen: Screen() {

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='de.payback.client.android:id/toolbar']/android.widget.TextView")
    private lateinit var titleLabel: MobileElement
    @AndroidFindBy(accessibility = "Navigate up")
    private lateinit var backButton: MobileElement
    @AndroidFindBy(id = "toolbar_bubble")
    protected lateinit var userAvatarMenuButton: MobileElement

    override fun waitForScreenIsLoaded() {
        TODO("Not yet implemented")
    }

    fun checkTitle(text: String) {
        Assert.assertEquals(titleLabel.getText(), text, "Title")
    }

    fun getMenuPanel(): BottomMenuPanelScreen {
        return ScreenFactory.initElements(mobileDriver, BottomMenuPanelScreen::class.java)
    }

    fun goBack() {
        backButton.click()
    }

    fun getUserAvatarMenu(): UserPanelScreen {
        userAvatarMenuButton.click()
        return ScreenFactory.initElements(mobileDriver, UserPanelScreen::class.java)
    }
}