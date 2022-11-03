package net.sample.page

import net.sample.browser.SeleniumDriver
import net.sample.element.BrowserElement
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class CustomInvocationHandler(_driver: SeleniumDriver, _locator: By,
                              _clazz: Class<*> ) : InvocationHandler {

    private var locator: By = _locator
    private var clazz: Class<*> = _clazz
    private var driver: SeleniumDriver = _driver

    override fun invoke(`object`: Any?, method: Method, objects: Array<Any?>): Any? {
        val elements: MutableList<WebElement> = driver.driver.findElements(locator)
        val customs: MutableList<BrowserElement> = ArrayList()
        for (element in elements) {
            val fieldConstructor = clazz.getConstructor(SeleniumDriver::class.java, By::class.java)
            val locator: By = convertLocatorToXpath(element)
            customs.add(fieldConstructor.newInstance(driver, locator) as BrowserElement)
        }
        return try {
            method.invoke(customs, *objects)
        } catch (e: InvocationTargetException) {
            throw e.cause!!
        }
    }

    private fun convertLocatorToXpath(element: WebElement): By {
        val id = element.getAttribute("id")
        return if (id != null && !id.isEmpty()) {
            val format = "//%s[@id='%s']"
            By.xpath(String.format(format, element.tagName, element.getAttribute("id")))
        } else {
            val format = "//%s[@name='%s']"
            By.xpath(String.format(format, element.tagName, element.getAttribute("name")))
        }
    }
}