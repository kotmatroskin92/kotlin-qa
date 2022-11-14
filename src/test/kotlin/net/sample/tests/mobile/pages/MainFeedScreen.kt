package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement
import net.sample.page.ScreenFactory

class MainFeedScreen(): CouponBaseScreen() {


    @AndroidFindBy(xpath = "//spinner")
    private lateinit var spinnerLabel: MobileElement
    @AndroidFindBy(id = "material_target_prompt_view")
    private lateinit var targetLabel: MobileElement
    @AndroidFindBy(id = "fab_action_card_selection")
    private lateinit var cardSelectionButton: MobileElement

    init {
        ScreenFactory.initElements(mobileDriver!!, this.javaClass)
    }

    override fun waitForScreenIsLoaded() {
        cardSelectionButton.waitForElementDisplayed()
    }
}