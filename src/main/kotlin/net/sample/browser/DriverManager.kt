package net.sample.browser

import Config
import org.apache.logging.log4j.LogManager
import org.openqa.selenium.WebDriverException
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class DriverManager {

    companion object {
        private val logger = LogManager.getLogger("DriverManager")
        private val defaultDriver: ThreadLocal<SeleniumDriver?> = ThreadLocal<SeleniumDriver?>()
        private val defaultMobileDriver: ThreadLocal<SeleniumDriver> = ThreadLocal<SeleniumDriver>()
        private var drivers: MutableList<SeleniumDriver> = CopyOnWriteArrayList<SeleniumDriver>()
        private val localDriverList: ThreadLocal<MutableList<SeleniumDriver>> = ThreadLocal<MutableList<SeleniumDriver>>()

        fun getDriver(): SeleniumDriver {
            if (null == defaultDriver.get()) {
                getNewDriver()
            }
            return defaultDriver.get()!!
        }

        fun getMobileDriver(): SeleniumDriver? {
            if (null == defaultMobileDriver.get()) {
                getNewDriver(Browsers.ANDROID)
            }
            return defaultMobileDriver.get()
        }

        fun getNewDriver(browsers: Browsers): SeleniumDriver {
            logger.debug("Create new instance of Driver.")
            val driver = SeleniumDriver(browsers)
            when (browsers) {
                Browsers.ANDROID -> defaultMobileDriver.set(driver)
                else -> setDefaultDriver(driver)
            }
            drivers.add(driver)
            val localList: MutableList<SeleniumDriver> =
                if (null == localDriverList.get()) ArrayList() else localDriverList.get() as MutableList<SeleniumDriver>
            localList.add(driver)
            localDriverList.set(localList)
            return driver
        }

        fun getNewDriver(): SeleniumDriver {
            return getNewDriver(Browsers.valueOf(Config.BROWSER))
        }

        fun setDefaultDriver(driver: SeleniumDriver) {
            defaultDriver.set(driver)
        }

        /**
         * Close default driver
         */
        fun closeDefaultMobileDriver() {
            val current: SeleniumDriver = defaultMobileDriver.get()
            val browsersCountBeforeClosing: Int = drivers.size
            if (null == defaultMobileDriver.get()) {
                logger.debug("There are no opened mobile drivers")
                return
            }
            logger.debug("Active drivers count before closing is $browsersCountBeforeClosing")
            try {
                closeDriver(current)
            } finally {
                val browsersCountAfterClosing: Int = drivers.size
                logger.debug("Active drivers count after closing is $browsersCountAfterClosing")
                defaultMobileDriver.set(null)
            }
        }

        fun closeDriver(driver: SeleniumDriver?) {
            logger.debug("Trying to close browser (driver.quit()): $driver")
            try {
                if (driver != null) {
                    driver.close()
                    driver.quit()
                    drivers.remove(driver)
                }
            } catch (exc: WebDriverException) {
                exc.printStackTrace()
            } finally {
                drivers.remove(driver)
            }
        }

        /**
         * Trying to close all opened during test executing browsers (driver
         * instances)
         */
        fun closeAllOpenedBrowsers() {
            logger.debug("Close all opened during tests executing browsers")
            var i = 0
            val driversToClose: MutableList<SeleniumDriver> = LinkedList()
            try {
                driversToClose.addAll(drivers)
                for (driver in driversToClose) {
                    logger.debug("Close Browser $i")
                    i++
                    closeDriver(driver)
                    logger.debug("Success")
                }
            } finally {
                defaultDriver.set(null)
                defaultMobileDriver.set(null)
            }
        }
    }





}