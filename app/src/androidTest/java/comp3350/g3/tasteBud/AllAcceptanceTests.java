package comp3350.g3.tasteBud;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateTest.class,
        RecommendTest.class,
        EditTest.class,
        FilterTest.class,
        RateTest.class,
        SearchTest.class
})
public class AllAcceptanceTests {
}
