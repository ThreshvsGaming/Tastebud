# Architecture
## UI Layer
### MainActivity
MainActivity will connect all the activities and widgets and leads users to SearchActivity by default.

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

### SearchProcessor
SearchProcessor will be the class for handling the logic and returning the results of querying through the collection(stub/database) of recipes.

## Data Layer
### IRecipeDb
IRecipeDb is the class that handles the storage of recipes within a database.

## DSOs
### Recipe
Recipe will be our main object that will be passed through the three layers. This object will be populated with all of the details of a specific recipe.


