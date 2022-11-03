package net.sample.page

import io.appium.java_client.AppiumBy
import io.appium.java_client.pagefactory.AndroidFindBy
import net.sample.browser.SeleniumDriver
import net.sample.element.MobileElement
import org.openqa.selenium.By
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

class ScreenFactory {
    companion object {
        fun initElements(driver: SeleniumDriver, pageClass: Class<Screen>): Screen {
            val page = instantiatePage(pageClass)
            initCustomElements(driver, page)
            val list = getSuperClasses(page.javaClass)
            for (superClass in list) {
                initFields(page, superClass!!.declaredFields, driver)
            }
            return page
        }

        private fun <T : Screen?> instantiatePage(pageClass: Class<T>): T {
            return try {
                try {
                    val constructor = pageClass
                        .getConstructor()
                    constructor.newInstance()
                } catch (e: NoSuchMethodException) {
                    pageClass.newInstance()
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        private fun initFields(screen: Screen, fields: Array<Field>, driver: SeleniumDriver) {
            for (field in fields) {
                try {
                    val fieldClass = field.type
                    if (MutableList::class.java.isAssignableFrom(fieldClass)) {
                        val genericType = field.genericType
                        val listElementClass = (genericType as ParameterizedType).actualTypeArguments[0] as Class<*>
                    }
                    when ("ANDROID") {
                        "ANDROID" -> if (MobileElement::class.java.isAssignableFrom(fieldClass)) {
                            val annotation = field.getAnnotation(AndroidFindBy::class.java)
                            val by = setAndroidMobileFindByToElement(annotation)
                            val fieldConstructor = fieldClass.getConstructor(
                                SeleniumDriver::class.java,
                                By::class.java
                            )
                            field.isAccessible = true
                            field[screen] = fieldConstructor.newInstance(driver, by)
                        }
                        "IOS" -> {}
                    }
                } catch (e: Exception) {
                    throw RuntimeException(e.message)
                }
            }
        }


        fun initCustomElements(driver: SeleniumDriver, screen: Screen) {
            initFields(screen, screen.javaClass.declaredFields, driver)
        }

        fun getSuperClasses(_clazz: Class<*>): List<Class<*>?> {
            var clazz = _clazz
            val classList: MutableList<Class<*>?> = ArrayList()
            var superclass = clazz.superclass
            classList.add(superclass)
            while (superclass != null) {
                clazz = superclass
                superclass = clazz.superclass
                if (superclass == Screen::class.java || superclass == Any::class.java) {
                    classList.remove(Screen::class.java)
                    classList.remove(Any::class.java)
                    break
                }
                classList.add(superclass)
            }
            return classList
        }

        private fun setAndroidMobileFindByToElement(annotation: AndroidFindBy?): By? {
            var by: By? = null
            if (null != annotation) {
                val id: String = annotation.id
                var xpath: String = annotation.xpath
                val accessibility: String = annotation.accessibility
                val uiAutomator: String = annotation.uiAutomator
                val tagName: String = annotation.tagName
                if (!id.isEmpty()) {
                    by = AppiumBy.id(id)
                } else if (!xpath.isEmpty()) {
//                xpath = ConfigUtils.getMobileLocale(xpath)
                    by = AppiumBy.xpath(xpath)
                } else if (!accessibility.isEmpty()) {
                    by = AppiumBy.accessibilityId(accessibility)
                } else if (!uiAutomator.isEmpty()) {
                    by = AppiumBy.androidUIAutomator(uiAutomator)
                } else if (!tagName.isEmpty()) {
                    by = AppiumBy.androidViewTag(tagName)
                }
            }
            return by
        }
    }
}