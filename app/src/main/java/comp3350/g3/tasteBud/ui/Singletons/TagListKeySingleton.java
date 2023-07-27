package comp3350.g3.tasteBud.ui.Singletons;

public class TagListKeySingleton {
    private String tagListKey = "tagList";

    public String GetTagListKey() {
        return tagListKey;
    }

    private static final TagListKeySingleton tagListKeySingleton = new TagListKeySingleton();

    public static TagListKeySingleton getInstance() {
        return tagListKeySingleton;
    }
}
