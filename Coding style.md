# Coding Style
## Naming
In general, methods and code blocks should be self-documenting with variable and method names that have a clear purpose. For example, ``x`` would be a bad variable name for counting the number of cats in a room. Instead we should use the variable name ``numCats``.

Casing should follow the normal Android guidelines, as documented in https://source.android.com/docs/core/architecture/hidl/code-style  with ``lowerCamelCase`` for methods and variables, generally speaking.

Classes should be named with ``UpperCamelCase`` also following Android convention. Constants should be ``ALL_CAPS`` with underscores between words.

## Bracketing
For methods and loops, the opening bracket will be on the first line and the closing bracket on its own line.

``` 
public static test(int numTest) {
// do stuff
}
```

```
for (int i = 0; i < numSize; i++) {
// do stuff
}
```

For if statements, statements should be on the same line as the closing bracket of the previous statement.

```
if (condition) {

} else if (condition) {

} else if (condition) {

} else {

}
```
This also goes for exceptions.

## Commenting
Comments should only be used if the code is not sufficiently self-commenting. Comments should explain why code is there and not necessarily what it's doing.

```
// This method needs to act on multiple kinds of objects, so we pass it a type "Object".
private StringBuffer format(Object arguments, StringBuffer result, FieldPosition pos) {

	// complex code...
	
}
```

Any non-obvious lines of code will be clearly commented.

```
pie.fill(fridge.apple("def")); // Populates Pie object "pie" with an apple from the fridge that is the default type, the Red Delicious.
```
## Magic numbers
Generally, any number that is not 1 or 0 should be contained in its own variable that describes its function.

## Iterators
Iterating variables shall be done with the incrementation syntax "++" or "+= num" syntax if we are iterating by more than one.

```
num = num + 1; // WRONG!!
num++; // Correct

pos = pos + 5; // WRONG!!
pos += 5; // Correct
pos += posIncrement; // Even Correcter :3
```

## Return statements
For simple methods like recursive helper methods, multiple return statements are allowed. However, for more complicated methods that are more than 10 lines long or have complex logic, multiple return statements should be avoided entirely.

## Indentation
Indentation should be equivalent to four spaces. In most IDEs, the "tab" button can be configured to place four spaces instead of a tab character.



