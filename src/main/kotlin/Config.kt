import net.sample.browser.Browsers
import java.nio.file.Paths

class Config {
    companion object {
        val BROWSER: String = System.getProperty("browser", Browsers.CHROME.name).uppercase()
        val DOWNLOAD_PATH: String = Paths.get("build", "downloads").toAbsolutePath().toString()
        val IS_REMOTE: Boolean = System.getProperty("isRemote", "false").toBoolean()
        val REMOTE_DRIVER_HOST: String = System.getProperty("remoteWebdriverHost", "127.0.0.1")
        val MOBILE_PROPERTIES_PATH: String = "src/test/resources/mobile.properties"
    }
    }