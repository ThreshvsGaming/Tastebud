package comp3350.g3.tasteBud;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.g3.tasteBud.logicTest.RatingsProcessorIntegrationTest;
import comp3350.g3.tasteBud.logicTest.RecommendProcessorIntegrationTest;
import comp3350.g3.tasteBud.logicTest.RefineProcessorIntegrationTest;
import comp3350.g3.tasteBud.logicTest.SearchProcessorIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                SearchProcessorIntegrationTest.class,
                RefineProcessorIntegrationTest.class,
                RefineProcessorIntegrationTest.class,
                RatingsProcessorIntegrationTest.class,
                RecommendProcessorIntegrationTest.class
        }
)
public class AllIntegrationTest {
}
