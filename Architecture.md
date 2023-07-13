# Architecture
![Architecture](architecture.png)
## UI Layer
### Main
Main is the main method of our project that creates MainActivity.

### MainActivity
MainActivity will connect all the activities and widgets and leads users to SearchActivity by default.

## Messages
Messages handles displaying system-level exceptions and error messages.

### CreateActivity
CreateActivity will be the screen the user will be taken to when adding a new recipe, with boxes for inputting a name, description, ingredients and tags.

### SearchActivity
SearchActivity is the screen within which the user will be able to search for recipes and view a list of recipes relevant to their query.

### DetailActivity
DetailActivity is the screen users can view individual recipe details such as name, description, ingredients and tags.

### ChefActivity
ChefActivity is the screen within which the recommend a recipe feature will be placed. Since we setup the toolbar, we've implemented a ChefActivity page to finish the toolbar feature.

## Logic Layer
### RecipeProcessor 
RecipeProcessor will handle the logic of taking in the user's inputted parameters for a recipe and push them up into RecipePersistence to be stored.

### PersistenceSingleton
PersistenceSingleton provides a single point of access for our database.

### SearchProcessor
SearchProcessor will be the class for handling the logic and returning the results of querying through the collection(stub/database) of recipes.

### TagListKeySingleton
TagListKeySingleton provides a single point of access for our list of tags that the user can filter on.

### ImageProcessor
Handles displaying and adding images into all of the UI pages. Most importantly, it builds the URIs for each photo so that they are displayed correctly.

### RecipeValidator
A validator class that takes in Recipe parameters and ensures that they constitute a valid instance of a class. Sends error messages back to the UI when needed.

### RefineProcessor
Handles filtering recipes using our list of tags and displaying them in RefineActivity.

### Services
Acts as a middleman between the logic and DB layer, handling queries to the SQL database.

## Data Layer
### IRecipeDb
IRecipeDb is the class that handles the storage of recipes within a database.

### RecipeStub
Our stub database used for testing.

### RecipeHSQLDB
The class that allows access to and handles our recipe database.

### ExceptionHandler
Used to handle any exceptions that arise from use of the SQL database.

## DSOs
### Recipe
Recipe will be our main object that will be passed through the three layers. This object will be populated with all of the details of a specific recipe.

### HomePageAdapter
Updates and displays the list of recipes on the home page.

### Ingredient
Stores an Ingredient from recipes as an instance. Currently unused in this iteration but will be implemented later.

### ImageSetter
Implements a simplified test, ensuring images are retrieved.

### ValidationStatusSetter
Is passed between the UI and RecipeValidator to create error messages that are displayed.

### ViewHolder
Passed between HomePageAdapter and SearchActivity, allows the user to select multiple items for deletion.


