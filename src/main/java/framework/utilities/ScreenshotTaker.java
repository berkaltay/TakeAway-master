package framework.utilities;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static framework.utilities.DriverManager.getDriver;

public class ScreenshotTaker {

    public static String takeBase64Screenshot(String testClassName, String methodName) {
        String destination = getDestination(testClassName, methodName);

        File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(sourceFile);
            byte[] bytes = new byte[(int) sourceFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));

            File newDestination = new File(destination);
            FileUtils.copyFile(sourceFile, newDestination);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedBase64;
    }

    private static String getDestination(String testClassName, String methodName) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy--HH");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String path = System.getProperty("user.dir") + "\\screenshots";

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String directory = path + "\\" + df.format(cal.getTime()).toString() + "\\";

        LocalDateTime dateTime = LocalDateTime.now();
        String date2 = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String dateName = date2.replace(" ", "_").replace(":", ".");

        String fileName = testClassName + "_" + methodName + "_" + dateName + ".png";

        File f = new File(directory);

        if (!f.isDirectory()) {
            f.mkdir();
            System.out.println("Folder was created successfully: " + directory);
        }

        return directory + fileName;
    }
}
