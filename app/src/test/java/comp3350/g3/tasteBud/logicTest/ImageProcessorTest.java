package comp3350.g3.tasteBud.logicTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import comp3350.g3.tasteBud.logic.ImageProcessor;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ImageProcessorTest {

    @Test
    public void testBuildImageUri() throws IOException {

        File testDirectory = Files.createTempDirectory("testDirectory").toFile();
        byte[] dummyData = "dummyData".getBytes();
        InputStream dummyInputStream = new ByteArrayInputStream(dummyData);
        ImageProcessor imageProcessor = new ImageProcessor();

        Uri uri = imageProcessor.buildImageUri(testDirectory, dummyInputStream);

        File resultFile = new File(uri.getPath());
        assertTrue(resultFile.exists());
        String resultFileContent = new String(Files.readAllBytes(resultFile.toPath()));
        assertEquals("dummyData", resultFileContent);

    }
}
