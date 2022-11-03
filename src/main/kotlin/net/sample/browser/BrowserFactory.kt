package net.sample.browser

import org.openqa.selenium.WebDriver

abstract class BrowserFactory {
    val REMOTE_WEBDRIVER_URL = "http://%s:4444/wd/hub"

    abstract fun createBrowser(): WebDriver?
}