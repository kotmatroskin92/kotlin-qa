package net.sample.tests.mobile.tests

import Config
import io.appium.java_client.android.Activity
import io.kotest.core.spec.style.AnnotationSpec
import net.sample.browser.DriverManager
import net.sample.browser.SeleniumDriver
import utils.PropertyReader
import java.util.*

open class BaseTest : AnnotationSpec(){

    protected lateinit var mobileDriver: SeleniumDriver
    private lateinit var properties: Properties

    @AnnotationSpec.BeforeAll
    fun globalSetup() {
        properties = PropertyReader.getInstance().getProperties(Config.MOBILE_PROPERTIES_PATH)!!
    }
    @AnnotationSpec.BeforeClass
    fun setupAppium() {
        mobileDriver = DriverManager.getMobileDriver()!!
        mobileDriver.runApp(Activity(properties.getProperty("capabilities.package"),
            properties.getProperty("capabilities.activity")))
//        MobileVideoRecorder.startRecording(mobileDriver);
    }

    @AnnotationSpec.AfterClass
    fun tearDownAppium() {
//        val destinationPath: = MobileVideoRecorder.stopRecording(mobileDriver)
        DriverManager.closeAllOpenedBrowsers()
        DriverManager.closeDefaultMobileDriver()
    }
}