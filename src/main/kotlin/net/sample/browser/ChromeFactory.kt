package net.sample.browser

import Config
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import kotlin.Any
import kotlin.String

class ChromeFactory : BrowserFactory() {

    override fun createBrowser(): WebDriver {
        val options = ChromeOptions()

        val chromePreferences = HashMap<String, Any>()
        chromePreferences["profile.password_manager_enabled"] = false
        chromePreferences["profile.default_content_settings.popups"] = 0
        chromePreferences["profile.default_content_setting_values.notifications"] = 1
        chromePreferences["profile.default_content_setting_values.automatic_downloads"] = 1
        chromePreferences["plugins.always_open_pdf_externally"] = true
        options.addArguments("--start-maximized", "--disable-web-security", "--disable-user-media-security=true", "--allow-running-insecure-content", "--no-default-browser-check")
        if (System.getProperty("isHeadless") == "true") {
            options.addArguments("--headless")
        }
        options.setExperimentalOption("prefs", chromePreferences)
        WebDriverManager.chromedriver().setup()
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("enableVideo", true)
        capabilities.setCapability("enableVNC", true)
        capabilities.setCapability("sessionTimeout", "10m")
        options.merge(capabilities)
        val driver: WebDriver
        if (!Config.IS_REMOTE) {
            chromePreferences["download.default_directory"] = Config.DOWNLOAD_PATH
            driver = ChromeDriver()
        }
        else {
            driver = RemoteWebDriver(
                URL(REMOTE_WEBDRIVER_URL.format(Config.REMOTE_DRIVER_HOST)), options)

        }
        return driver
    }
}