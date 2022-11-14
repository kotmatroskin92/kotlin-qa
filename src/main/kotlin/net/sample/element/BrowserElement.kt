package net.sample.element

import net.sample.browser.SeleniumDriver
import org.apache.logging.log4j.LogManager
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException

const val DEFAULT_WAIT = 10L

open class BrowserElement(_driver: SeleniumDriver, _foundBy: By) {

    private val logger = LogManager.getLogger("BrowserElement")
    private var formatLocator: String? = null
    protected var driver: SeleniumDriver
    protected var foundBy: By


    init {
        driver = _driver
        foundBy = _foundBy
    }

    fun waitForElementClickable(timeoutSeconds: Long) {
        try {
            driver.waitForElementClickable(foundBy, timeoutSeconds)
        } catch (e: Exception) {
            throw TimeoutException("Failed to wait element: " + foundBy + ". " + e.message)
        }
    }

    open fun waitForElementDisappeared() {
        try {
            driver.waitForElementDisappeared(foundBy, DEFAULT_WAIT)
        } catch (e: java.lang.Exception) {
            throw TimeoutException(
                "Failed to wait element disappear: " + foundBy + ". "
                        + e.message
            )
        }
    }

    open fun waitForElementDisplayed() {
        waitForElementDisplayed(DEFAULT_WAIT)
    }

    open fun waitForElementDisplayed(timeoutSeconds: Long) {
        try {
            driver.waitForElementDisplayed(foundBy, timeoutSeconds)
        } catch (e: java.lang.Exception) {
            throw TimeoutException(
                "Failed to wait element: " + foundBy + ". "
                        + e.message
            )
        }
    }

    open fun isExists(): Boolean {
        return driver.isElementExists(foundBy)
    }

    open fun click() {
        driver.click(foundBy)
    }

    open fun format(vararg replaceString: Any): BrowserElement {
        if (formatLocator == null) {
            formatLocator = foundBy.toString()
        }
        val locator = String.format(formatLocator!!, *replaceString)
        foundBy = FindByUtils.getByNestedObject(locator)
        return this
    }
}