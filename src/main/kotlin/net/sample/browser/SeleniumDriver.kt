package net.sample.browser

import io.appium.java_client.TouchAction
import io.appium.java_client.android.Activity
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.StartsActivity
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.apache.logging.log4j.LogManager
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.PointerInput
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

    private val pageWaitDuration = Duration.ofSeconds(PAGE_WAIT_TIMEOUT)
    private val minWaitDuration = Duration.ofSeconds(MIN_IMPLICIT_WAIT_TIMEOUT)
    private val defaultWaitDuration = Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT_TIMEOUT)

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
                driver.manage().timeouts().pageLoadTimeout(pageWaitDuration)
            }
            Browsers.ANDROID -> {
                driver = AndroidFactory().createBrowser()
            }
        }
        driver.manage().timeouts().implicitlyWait(defaultWaitDuration)
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

    fun waitForElementDisappeared(locator: By, timeOutInSeconds: Long) {
        logger.debug("Waiting for element is not visible yet'$locator' during $timeOutInSeconds sec timeout ...")
        WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedCondition { d ->
            try {
                d.manage().timeouts().implicitlyWait(minWaitDuration)
                if (d.findElements(locator).size == 0) {
                    return@ExpectedCondition true
                }
                d.manage().timeouts().implicitlyWait(minWaitDuration)
                return@ExpectedCondition !d.findElement(locator).isDisplayed
            } catch (e: Exception) {
                return@ExpectedCondition true
            } finally {
                d.manage().timeouts().implicitlyWait(defaultWaitDuration)
            }
        })
    }

    fun waitForElementDisplayed(locator: By, timeOutInSeconds: Long) {
        logger.debug("Waiting for element '" + locator + "' exists during " + timeOutInSeconds + "sec timeout ...")
        val wait = WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
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

    fun isElementExists(locator: By): Boolean {
        driver.manage().timeouts().implicitlyWait(minWaitDuration)
        return try {
            logger.debug("Check is element exist: $locator")
            driver.findElement(locator)
            true
        } catch (e: java.lang.Exception) {
            false
        } finally {
            driver.manage().timeouts().implicitlyWait(defaultWaitDuration)
        }
    }

    private fun highlightElementOnScreen(locator: By) {
        executeScript(locator, HIGHLIGHT_ELEMENT_JS)
    }

    private fun executeScript(locator: By, script: String) {
        val jse = driver as JavascriptExecutor
        val element = driver.findElement(locator)
        jse.executeScript(script, element)
    }

    /**
     * Scroll screen from down to up. Get up of the screen.
     */
    fun swipeDown() {
        val size = driver.manage().window().size
        val startY = (size.getHeight() * 0.2).toInt()
        val endY = startY * 3
        val x = (size.getWidth() * 0.5).toInt()
        logger.info("Swipe down")
        verticalSwipe(x, startY, endY)
    }

    /**
     * Do vertical swipe. x=0 -> left. y=0 -> up. x=maxWidth -> right. y=maxHeight -> down
     */
    private fun verticalSwipe(x: Int, startY: Int, endY: Int) {
        logger.info("Swipe from x:$x y:$startY, to x:$x y:$endY")
        TouchAction(driver as AndroidDriver).press(PointOption.point(x, startY)).waitAction(
            WaitOptions.waitOptions(
                Duration.ZERO
            )
        ).moveTo(PointOption.point(x, endY)).release().perform()
    }
}