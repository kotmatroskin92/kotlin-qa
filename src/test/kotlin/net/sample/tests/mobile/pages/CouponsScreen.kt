package net.sample.tests.mobile.pages

import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.element.MobileElement
import net.sample.tests.mobile.entities.FiltersEnum

const val TITLE = "Coupons"

class CouponsScreen(): CouponBaseScreen() {


    @AndroidFindBy(id = "filter_button")
    private lateinit var filterButton: MobileElement
    @AndroidFindBy(id = "appbar")
    private lateinit var filterAppBarLabel: MobileElement
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='de.payback.client.android:id/grid']/android.widget.FrameLayout[@resource-id='de.payback.client.android:id/tile'][%s]")
    private lateinit var partnerCouponTemplateButton: MobileElement
    @AndroidFindBy(id = "done")
    private lateinit var doneButton: MobileElement
    @AndroidFindBy(id = "not_activated_button")
    private lateinit var activateCouponButton: MobileElement
    @AndroidFindBy(id = "activated_icon")
    private lateinit var couponActiveLabel: MobileElement

    override fun waitForScreenIsLoaded() {
        checkTitle(TITLE)
    }

    fun selectFilter(filter : FiltersEnum) {
        openFilterTab()
        partnerCouponTemplateButton.format(filter.value).swipeDownUntilElement().click()
        doneButton.click()
    }

    fun activateCoupon(): Boolean {
        activateCouponButton.click()
        return couponActiveLabel.isExists()
    }

    private fun openFilterTab() {
        if (!filterAppBarLabel.isExists()) {
            filterButton.click()
        }
        filterAppBarLabel.waitForElementDisplayed()
    }
}