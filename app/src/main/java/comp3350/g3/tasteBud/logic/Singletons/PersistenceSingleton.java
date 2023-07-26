package comp3350.g3.tasteBud.logic.Singletons;

public class PersistenceSingleton {
    private Boolean isPersistence = true;
    public Boolean GetIsPersistence() { return isPersistence; }

    private static final PersistenceSingleton persistenceSingleton = new PersistenceSingleton();
    public static PersistenceSingleton getInstance() { return persistenceSingleton; }
}
