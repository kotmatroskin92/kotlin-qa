package net.sample.tests.mobile.tests

import net.sample.tests.mobile.pages.CouponMainScreen

class PaybackTest : BaseTest(){

    @Test
    fun filterCouponTest() {
        CouponMainScreen().waitForScreenIsLoaded()
    }
}