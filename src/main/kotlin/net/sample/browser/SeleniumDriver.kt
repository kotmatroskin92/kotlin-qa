package net.sample.browser

import io.appium.java_client.android.Activity
import io.appium.java_client.android.StartsActivity
import org.apache.logging.log4j.LogManager
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

const val PAGE_WAIT_TIMEOUT = 35L
const val DEFAULT_IMPLICIT_WAIT_TIMEOUT  = 5L
const val MIN_IMPLICIT_WAIT_TIMEOUT: Long = 1
private const val HIGHLIGHT_ELEMENT_JS =
    "arguments[0].setAttribute('style', 'border:2px solid green; background:yellow')"


class SeleniumDriver(_browserType: Browsers) : WebDriver{

    private val logger = LogManager.getLogger("DriverManager")
    var driver : WebDriver


    init{
        when (_browserType) {
            Browsers.FF -> {
                throw IllegalArgumentException("Not implemented")
            }
            Browsers.CHROME -> {
                driver = ChromeFactory().createBrowser()
                driver.manage().window().maximize()
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_WAIT_TIMEOUT))
            }
            Browsers.ANDROID -> {
                driver = AndroidFactory().createBrowser()
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT_TIMEOUT))
    }

    override fun findElements(by: By?): MutableList<WebElement> {
        TODO("Not yet implemented")
    }

    override fun findElement(by: By?): WebElement {
        TODO("Not yet implemented")
    }

    override fun get(url: String?) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUrl(): String {
        TODO("Not yet implemented")
    }

    override fun getTitle(): String {
        TODO("Not yet implemented")
    }

    override fun getPageSource(): String {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun quit() {
        TODO("Not yet implemented")
    }

    override fun getWindowHandles(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getWindowHandle(): String {
        TODO("Not yet implemented")
    }

    override fun switchTo(): WebDriver.TargetLocator {
        TODO("Not yet implemented")
    }

    override fun navigate(): WebDriver.Navigation {
        logger.debug("Navigate executing")
        return driver.navigate()
    }

    override fun manage(): WebDriver.Options {
        TODO("Not yet implemented")
    }

    fun getAttribute(foundBy: By, attribute: String) : String {
        return driver.findElement(foundBy).getAttribute(attribute)
    }

    /**
     * Run mobile app
     */
    fun runApp(activity: Activity) {
        (driver as StartsActivity).startActivity(activity)
    }

    fun waitForElementClickable(locator: By) {
        waitForElementClickable(locator, DEFAULT_IMPLICIT_WAIT_TIMEOUT)
    }

    fun waitForElementClickable(locator: By, timeOutInSeconds: Long) {
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
        wait.until(ExpectedConditions.elementToBeClickable(locator))
    }

    fun waitForElementDissappear(locator: By, timeOutInSeconds: Long) {
        logger.debug("Waiting for element is not visible yet'$locator' during $timeOutInSeconds sec timeout ...")
        WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedCondition { d ->
            try {
                d.manage().timeouts().implicitlyWait(Duration.ofSeconds(MIN_IMPLICIT_WAIT_TIMEOUT))
                if (d.findElements(locator).size == 0) {
                    return@ExpectedCondition true
                }
                d.manage().timeouts().implicitlyWait(Duration.ofSeconds(MIN_IMPLICIT_WAIT_TIMEOUT))
                return@ExpectedCondition !d.findElement(locator).isDisplayed
            } catch (e: Exception) {
                return@ExpectedCondition true
            } finally {
                d.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT_TIMEOUT))
            }
        })
    }

    fun type(locator: By, text: String) {
        driver.findElement(locator).clear()
        driver.findElement(locator).sendKeys(text)
        logger.info("Typed text '$text' ...")
    }

    fun click(by: By) {
        highlightElementOnScreen(by)
        waitForElementClickable(by)
    }

    private fun highlightElementOnScreen(locator: By) {
        executeScript(locator, HIGHLIGHT_ELEMENT_JS)
    }

    private fun executeScript(locator: By, script: String) {
        val jse = driver as JavascriptExecutor
        val element = driver.findElement(locator)
        jse.executeScript(script, element)
    }
}