package net.sample.tests.mobile.tests

import net.sample.page.ScreenFactory
import net.sample.tests.mobile.entities.CouponMission
import net.sample.tests.mobile.pages.LoginScreen
import net.sample.tests.mobile.utils.UserFactory
import org.junit.Assert
import utils.JsonUtils

const val DATA_PATH = "src/test/resources/data/coupon.apollo.json"
class PaybackTest : BaseTest(){

    @Test
    fun filterCouponTest() {
        val coupon = getTestData()[0]
        val loginScreen = ScreenFactory.initElements(mobileDriver, LoginScreen::class.java)
        val mainFeedScreen = loginScreen.login(UserFactory.getUserByName(coupon.user))
        mainFeedScreen.waitForScreenIsLoaded()
        val couponsScreen = mainFeedScreen.getMenuPanel().openCouponsScreen()
        couponsScreen.waitForScreenIsLoaded()
        couponsScreen.selectFilter(coupon.couponFilter)
        val isActivated = couponsScreen.activateCoupon()
        Assert.assertTrue("Coupon icon", isActivated)
    }

    private fun getTestData(): Array<CouponMission> {
        return JsonUtils.getObjectFromJson(DATA_PATH, Array<CouponMission>::class.java)
    }
}