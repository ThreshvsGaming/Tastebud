# Iteration 3 Worksheet 
## What technical debt has been cleaned up 
### Show links to a commit where you paid off technical debt. Write 2-5 sentences that explain what debt was paid, and what its classification is. 
 
Link: https://code.cs.umanitoba.ca/3350-summer2023/g3canada-3/-/commit/8ff60e4ca8747426d40156474f6cd5fc7aff92c8#3323f8cbfb31c5a3ae427d78b50528935912611a_0_5 
The technical debt was having the validation in the class that processed the Recipe. This validation should have been in its own class so that the recipe processor does not know what kind of validation is being done as that would violate SRP. This debt's classification is deliberate/prudent because we made the conscious choice to leave it on to finish the feature and prudent because we carefully left it there to be dealt with later. 
 
## What technical debt did you leave? 
### What one item would you like to fix, and can’t? Anything you write will not be marked negatively. Classify this debt. 
 
The coupling of handling images with android studio functionality. This is because to locate/retrieve images, android libraries needed to be used to make it work across the 3 tier architecture. This kind of debt is deliberate/prudent because we had no other way of doing this given the time, and left it in regardless of the awareness of the code smell. 
 
## Discuss a Feature or User Story that was cut/re-prioritized 
### When did you change the priority of a Feature or User Story? Why was it re-prioritized? Provide a link to the Feature or User Story. This can be from any iteration. 
 
Link: https://code.cs.umanitoba.ca/3350-summer2023/g3canada-3/-/issues/12 
Reason for re-prioritizing:  
We had to prioritize project tests and refactoring/removing code smells in the limited time period that we had for iteration 3. 
Adding this feature would have meant more code complexity, increased probability of more code smells that we would not have had the time to fix. 
 
## Acceptance test/end-to-end 
### Write a discussion about an end-to-end test that you wrote. What did you test, how did you set up the test so it was not flaky? Provide a link to that test. 
 
  We created an acceptance test for the CreateRecipe. While writing this test, we simulated the action that users would perform, like adding a recipe name and ingredients, and converted them into Espresso statements to conduct different tests for each feature. We also verified whether the corresponding pop-up windows appeared as expected. 
  To ensure that the tests were not flaky, We initialized a test Recipe at the beginning of each test and deleted the corresponding Test Recipe at the end. This approach prevented any errors caused by Espresso reading incorrect data, thus avoiding test failures when running the next test. 
 
Link: https://code.cs.umanitoba.ca/3350-summer2023/g3canada-3/-/blob/iteration3-AcceptanceTest/app/src/androidTest/java/comp3350/g3/tasteBud/CreateTest.java 
 
## Acceptance test, untestable 
### What challenges did you face when creating acceptance tests? What was difficult or impossible to test? 

We encountered some challenges while writing Acceptance Tests that we couldn't solve initially. For example: (view has effective visibility <VISIBLE> and view.getGlobalVisibleRect() covers at least <90> percent of the view's area). Later, We realized that it was due to some textBars overlapping in the UI, which caused Espresso to fail in recognizing the views properly. By simply adjusting the UI layout, the problem was resolved. 
Also, there is one area that cannot be tested. TasteBud allows users to add their own images when creating a recipe, but if we want to use Espresso for testing, we cannot predict which image the user will use. Therefore, this particular test cannot be completed. However, We still wrote a method to ensure that users can click on this feature and can successfully enter the interface to select an image. 
 
 
## Velocity/teamwork 
### Did your estimates get better or worse through the course? Show some evidence of the estimates/actuals from tasks. 
 
Our estimates got worse throughout the course as evidenced by the velocity chart, our estimates got worse in iteration 2 compared to iteration 1. In iteration 1, we overestimated the time it would require to complete the features, but we overestimated the same in iteration 2.  
 
For example: 
In iteration 1: https://code.cs.umanitoba.ca/3350-summer2023/g3canada-3/-/issues/9 
We estimated it would take 3 days to implement store recipe feature, but it only took us 2 days. 
 
In iteration 2: https://code.cs.umanitoba.ca/3350-summer2023/g3canada-3/-/issues/3 
We estimated it would take 2 days to implement store recipe photos, but ended up needing 1 week for it. 
