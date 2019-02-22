package framework.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static framework.base.TestBase.getDriver;
import static framework.extentFactory.ReportFactory.getChildTest;
import static java.util.Collections.emptyList;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class BasePageMethods {

    protected WebDriver driver = getDriver();
    private WebDriverWait jsWait = new WebDriverWait(driver, 60);
    private JavascriptExecutor jsExec = (JavascriptExecutor) driver;


    // Element Click Method (Includes waitClickable and getElement)
    public void clickWebElement(By locator) {
        WebElement element = waitUntilVisibleByLocator(locator);
        scrollTo(element, 100);
        waitUntilClickableByListOfElement(element).click();
        waitUntilJQueryReadyAndJSReady();
    }


    //wait Element Until Visible
    public WebElement waitUntilVisibleByLocator(By locator) {
        WebElement element = null;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(60))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Throwable t) {
            getChildTest().fail("waitUntilVisibleByLocator Fail: ");
            Assert.fail("waitUntilVisibleByLocator fail", t);
        }

        return element;
    }



    //wait ELement Until Presence
    protected WebElement waitUntilPresenceOfElementByLocator(By locator) {
        WebElement element = null;

        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Throwable t) {
            getChildTest().fail("waitUntilPresenceOfElementByLocator Fail: ");
            Assert.fail("waitUntilPresenceOfElementByLocator fail", t);
        }

        return element;
    }

    //send Keys To Element (Includes presenceOfElement, visibleOfElement and getElement)
    protected void sendKeysToElement(By locator, String text) {
        WebElement element = waitUntilVisibleByLocator(locator);
        scrollTo(element, 100);
        element = waitUntilPresenceOfElementByLocator(locator);
        element.clear();
        element.sendKeys(text);
        waitUntilJQueryReadyAndJSReady();
    }

    protected void sendKeysToElementWithoutCleanInside(By locator, String text) {
        WebElement element = waitUntilVisibleByLocator(locator);
        scrollTo(element, 100);
        element = waitUntilPresenceOfElementByLocator(locator);
        element.sendKeys(text);
        waitUntilJQueryReadyAndJSReady();
    }


    //wait for Only JQuery Load
    protected void waitForJQueryLoad() {
        try {
            // Wait for jQuery to load
            ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
                    .executeScript("return jQuery.active") == 0);
            // Get JQuery is Ready
            boolean jqueryReady = (Boolean) jsExec.executeScript("return window.jQuery != 'undefined' || jQuery.active==0");

            // Wait JQuery until it is Ready!
            if (!jqueryReady) {
                // Wait for jQuery to load
                jsWait.until(jQueryLoad);
            }
        } catch (Throwable t) {
            getChildTest().fail("waitForJQueryLoad fail");
            Assert.fail("waitForJQueryLoad fail", t);
        }
    }

    // Wait for Only AngularJS Load
    protected void waitForAngularJSLoad() {
        try {
            String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

            // Wait for ANGULAR to load
            ExpectedCondition<Boolean> angularLoad = driver -> Boolean
                    .valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());

            // Get Angular is Ready
            boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());

            // Wait ANGULAR until it is Ready!
            if (!angularReady) {
                // Wait for Angular to load
                jsWait.until(angularLoad);
            }
        } catch (Throwable t) {
            getChildTest().fail("waitForAngularJSLoad fail");
            Assert.fail("waitForAngularJSLoad fail", t);
        }
    }

    // Wait for Only JS Ready
    protected void waitUntilJSReady() {
        try {
            // Wait for Javascript to load
            ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").toString().equals("complete");

            // Get JS is Ready
            boolean jsReady = (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");

            // Wait Javascript until it is Ready!
            if (!jsReady) {
                // Wait for Javascript to load
                jsWait.until(jsLoad);
            }
        } catch (Throwable t) {
            getChildTest().fail("waitUntilJSReady fail");
            Assert.fail("waitUntilJSReady fail", t);
        }
    }

    // Wait Until JQuery and JS Ready Together
    protected void waitUntilJQueryReadyAndJSReady() {
        waitForJQueryLoad();
        waitUntilJSReady();
    }


    //wait Element Until Clickable By WebElement
    protected WebElement waitUntilClickableByListOfElement(WebElement webElement) {
        WebElement element = null;

        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (Throwable t) {
            getChildTest().fail("waitUntilClickableByListOfElement fail");
            Assert.fail("waitUntilClickableByListOfElement fail", t);
        }

        return element;
    }


    protected void scrollTo(WebElement element, int margin) {
        scrollTo(element.getLocation().x, element.getLocation().y - margin);
    }

    protected void scrollTo(int x, int y) {
        jsExec.executeScript("scrollTo(" + x + "," + y + ");");
    }


    public boolean isElementPresent(By by, int timeOutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(timeOutInSeconds)).
                pollingEvery(Duration.ofMillis(500)).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        try {
            wait.until(presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException toe) {
            return false;
        }
    }

    protected List<WebElement> visibilityOfAllWait(By by, int timeOutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(timeOutInSeconds)).
                pollingEvery(Duration.ofMillis(500)).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        return wait.until(visibilityOfAllElementsLocatedBy(by));
    }

    public void waitForElementToHide(By elementLocator, int timeOutInSeconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).
                    withTimeout(Duration.ofSeconds(timeOutInSeconds)).
                    pollingEvery(Duration.ofMillis(500)).
                    ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);

            wait.until(invisibilityOfElementLocated(elementLocator));
        } catch (Throwable t) {
            getChildTest().info("Element did not disappear. ");
        }
    }

    protected void clickWebElement(WebElement element) {
        scrollTo(element, 100);
        waitUntilClickableByListOfElement(element).click();
        waitUntilJQueryReadyAndJSReady();
    }


    protected List<WebElement> visibilitiesWaitNested(WebElement element, By by, int timeoutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(timeoutInSeconds)).
                pollingEvery(Duration.ofMillis(500)).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        return wait.until(visibilityOfAllElementsLocatedByIn(by, element));
    }

    protected static ExpectedCondition<List<WebElement>> visibilityOfAllElementsLocatedByIn(
            final By locator, final WebElement parent) {
        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements;
                if (parent != null) {
                    elements = parent.findElements(locator);
                } else {
                    elements = driver.findElements(locator);
                }
                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                        return emptyList();
                    }
                }
                return !elements.isEmpty() ? elements : null;
            }

            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }


    protected void waitForElementToGetAttribute(int seconds, By elementLocator, String attribute, String value) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(attributeContains(elementLocator, attribute, value));
    }


    protected void pressEnter() {
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ENTER).build().perform();

    }

    public WebElement mouseHover(By locator) {

        WebElement element = null;
        try {
            element = driver.findElement(locator);
            Actions builder = new Actions(driver);
            builder.moveToElement(element).build().perform();
        } catch (Throwable t) {
            getChildTest().fail("Couldn't find element");
            Assert.fail("Couldn't find element", t);
        }

        return element;

    }

}
