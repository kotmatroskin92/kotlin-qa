package net.sample.element

import net.sample.browser.SeleniumDriver
import org.openqa.selenium.By

class MobileElement(_driver: SeleniumDriver, _foundBy: By) : BrowserElement(_driver, _foundBy) {

    fun getText(): String {
        return driver.getAttribute(foundBy, "text")
    }

    fun type(text: String) {
        driver.type(foundBy, text)
    }

    fun click() {
        driver.click(foundBy)
    }
}