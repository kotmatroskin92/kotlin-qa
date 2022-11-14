package net.sample.element

import net.sample.browser.SeleniumDriver
import org.openqa.selenium.By

const val MAX_SWIPE_COUNT = 5

class MobileElement(_driver: SeleniumDriver, _foundBy: By) : BrowserElement(_driver, _foundBy) {

    fun getText(): String {
        return driver.getAttribute(foundBy, "text")
    }

    fun type(text: String) {
        driver.type(foundBy, text)
    }

    override fun click() {
        driver.click(foundBy)
    }

    fun swipeDownUntilElement(): MobileElement {
        var count = 0
        do {
            if (driver.isElementExists(foundBy)) {
                return this
            } else {
                driver.swipeDown()
            }
        } while (++count != MAX_SWIPE_COUNT)
        return this
    }

    override fun format(vararg replaceString: Any): MobileElement {
        format(replaceString)
        return this
    }
}