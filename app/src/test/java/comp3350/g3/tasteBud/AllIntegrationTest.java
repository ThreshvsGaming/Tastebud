package comp3350.g3.tasteBud;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.g3.tasteBud.logicTest.RefineProcessorIntegrationTest;
import comp3350.g3.tasteBud.logicTest.SearchProcessorIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                SearchProcessorIntegrationTest.class,
                RefineProcessorIntegrationTest.class,
                RefineProcessorIntegrationTest.class
        }
)
public class AllIntegrationTest {
}
