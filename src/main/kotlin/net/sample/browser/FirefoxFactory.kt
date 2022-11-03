package net.sample.browser

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile

class FirefoxFactory : BrowserFactory() {

    override fun createBrowser(): WebDriver {
        val profile = FirefoxProfile()
        profile.setAcceptUntrustedCertificates(true)
        profile.setAssumeUntrustedCertificateIssuer(false)
        val options = FirefoxOptions()
        options.setCapability(FirefoxDriver.Capability.PROFILE, profile)
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null")
        options.setCapability("fake", "true")
        options.setCapability("media.navigator.permission.disabled", "true")
        WebDriverManager.firefoxdriver().setup()
        return FirefoxDriver(options)
    }
}