# Architecture
## UI Layer
### Main
Main will be the main screen of our application, the starting screen that the user sees when first booting up the app.

### AddRecipe
AddRecipe will be the screen the user will be taken to when adding a new recipe, with boxes for inputting a name, description, and ingredients.

### SearchPage
SearchPage is the screen within which the user will be able to search for recipes and view a list of recipes relevant to their query.

## Logic Layer
### RecipeCreator 
RecipeCreator will handle the logic of taking in the user's inputted parameters for a recipe and push them up into RecipePersistence to be stored.

### SearchHandler
SearchHandler will be the class for handling the logic and returning the results of querying through the database of recipes.

## Data Layer
### RecipePersistence
RecipePersistence is the class that handles the storage of recipes within a database.

## DSOs
### Recipe
Recipe will be our main object that will be passed through the three layers. This object will be populated with all of the details of a specific recipe.


