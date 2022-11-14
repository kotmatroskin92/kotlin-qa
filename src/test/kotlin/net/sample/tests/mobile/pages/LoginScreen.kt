package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement
import net.sample.page.ScreenFactory
import net.sample.tests.mobile.entities.User

class LoginScreen(): CouponBaseScreen() {

    //main dialog
    @AndroidFindBy(id = "welcome_loginbutton")
    private lateinit var loginButton: MobileElement
    @AndroidFindBy(id = "textview_welcome_enrolled")
    private lateinit var welcomeLabel: MobileElement
    @AndroidFindBy(id = "progressbutton_btn")
    private lateinit var continueButton: MobileElement

    //card dialog
    @AndroidFindBy(id = "txtLoginCheckCardNumber")
    private lateinit var cardNumberInput: MobileElement

    //password dialog
    @AndroidFindBy(id = "login_password_field")
    private lateinit var passwordInput: MobileElement


    override fun waitForScreenIsLoaded() {
        welcomeLabel.waitForElementDisplayed()
    }

    fun login(user: User): MainFeedScreen {
        logoutIfRequired()
        loginButton.click()
        cardNumberInput.type(user.coupon)
        continueButton.click()
        passwordInput.type(user.password)
        continueButton.click()
        return ScreenFactory.initElements(mobileDriver, MainFeedScreen::class.java)
    }

    fun logoutIfRequired() {
        if(!loginButton.isExists() && userAvatarMenuButton.isExists()) {
            getUserAvatarMenu().logout()
        }
    }

}