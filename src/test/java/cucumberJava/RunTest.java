package cucumberJava;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.junit.Cucumber;

/**
 * 
 * @author Usuario
 *
 */
@RunWith(Cucumber.class)
@Cucumber.Options(format= {"pretty", "html:target/cucumber"})
public class RunTest {
}