package Utilities;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Base {
	
	DesiredCapabilities cap;
	private AppiumDriver<MobileElement> driver;
	AppiumDriverLocalService appiumService;
	String appiumServiceUrl;
	AppiumServiceBuilder builder;

	
	@BeforeSuite
	
	public void initialize () {
		
		/*we will initialize appium service*/
		
		AppiumDriverLocalService appiumService = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
						.usingDriverExecutable(new File("/usr/local/bin/node")).withIPAddress("127.0.0.1")
						.usingAnyFreePort()
					//	.usingPort(4723)
						.withArgument(GeneralServerFlag.SESSION_OVERRIDE));
		appiumService.start();		
		appiumServiceUrl= appiumService.getUrl().toString();
		System.out.println("Appium Service Address:-   " + appiumServiceUrl);
	
		
	}
	
	@BeforeTest
	
	public void SetUp() throws MalformedURLException {
	
	cap = new DesiredCapabilities();
	cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
	cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
	cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
	cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Harleen's Iphone Simulator");
	cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.5");
//		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
//		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
//		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
//		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
//		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.4");
//		cap.setCapability(MobileCapabilityType.UDID, "e51d3c1c17ac7c05a7b24b2f99f9aa2fa5eccad2");
	    driver = new AppiumDriver <MobileElement> (new URL(appiumServiceUrl),cap);
	    System.out.println("Inside Set up");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	
	}
	
	public AppiumDriver<MobileElement> getdriver () {
		
		return driver;
	}
	
	@AfterTest 
	
	public void TearDown() {
		
		driver.close();
		driver.quit();
		
	}
	
	@AfterSuite
	
	public void ShutDown() {
		
		// shutdown appium server 
	}
		
}

