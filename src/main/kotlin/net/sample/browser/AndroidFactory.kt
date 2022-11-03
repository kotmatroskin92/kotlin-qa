package net.sample.browser

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import utils.PropertyReader
import java.lang.IllegalStateException
import java.net.MalformedURLException
import java.net.URL
import java.util.*



class AndroidFactory : BrowserFactory() {
    override fun createBrowser(): WebDriver {
        val capabilities = DesiredCapabilities()
        val prop: Properties = PropertyReader.getInstance().getProperties(Config.MOBILE_PROPERTIES_PATH)!!
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("capabilities.deviceName"))
        capabilities.setCapability("--session-override", true)
//        capabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("capabilities.app"))
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
            prop.getProperty("capabilities.automationName"))
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("capabilities.platformName"))
        capabilities.setCapability(
            MobileCapabilityType.PLATFORM_VERSION,
            prop.getProperty("capabilities.platformVersion")
        )
        capabilities.setCapability(
            MobileCapabilityType.NEW_COMMAND_TIMEOUT,
            prop.getProperty("capabilities.newCommandTimeout").toLong()
        )
        capabilities.setCapability(
            MobileCapabilityType.NO_RESET,
            prop.getProperty("capabilities.noReset").toBoolean()
        )
        capabilities.setCapability(
            "waitForIdleTimeout",
            prop.getProperty("capabilities.waitForIdleTimeout").toLong()
        )
        capabilities.setCapability("instrumentApp", true)
        capabilities.setCapability("autoGrantPermissions", true)
        val driver: AppiumDriver
        try {
            driver = AndroidDriver(
                URL(prop.getProperty("appium_host")),
                capabilities
            )
        } catch (e: MalformedURLException) {
            throw IllegalStateException("Driver isn't created $e")
        }
        return driver
    }
}