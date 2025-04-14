package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportConfig {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Initialize Spark Reporter
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setDocumentTitle("API Test Report");
            spark.config().setReportName("JSONPlaceholder, OpenWeather, ReqRes API Tests");
            spark.config().setTheme(Theme.DARK);
            spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

            // Initialize ExtentReports and attach reporter
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "Test");
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }
}