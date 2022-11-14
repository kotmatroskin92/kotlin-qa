package net.sample.element

import io.appium.java_client.AppiumBy
import org.openqa.selenium.By

class FindByUtils {

    companion object {
        private fun getByObject(locator: String): By? {
            if (locator.startsWith("By.xpath: ")) {
                return By.xpath(locator.replace("By.xpath: ", ""))
            }
            if (locator.startsWith("By.cssSelector: ")) {
                return By.cssSelector(locator.replace("By.cssSelector: ", ""))
            }
            if (locator.startsWith("By.id: ")) {
                return By.id(locator.replace("By.id: ", ""))
            }
            if (locator.startsWith("By.name: ")) {
                return By.name(locator.replace("By.name: ", ""))
            }
            if (locator.startsWith("By.className: ")) {
                return By.className(locator.replace("By.className: ", ""))
            }
            return if (locator.startsWith("By.AccessibilityId: ")) {
                AppiumBy.accessibilityId(locator.replace("By.AccessibilityId: ", ""))
            } else null
        }

        fun getByNestedObject(locator: String): By {
            return getByObject(locator)!!
        }
    }
}