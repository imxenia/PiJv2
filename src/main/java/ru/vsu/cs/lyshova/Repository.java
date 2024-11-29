package ru.vsu.cs.lyshova;

import ru.vsu.cs.lyshova.objects.*;

import java.util.*;

public class Repository<T extends Identifiable> implements RepositoryInterface<T> {
    private Map<Integer, T> database = new HashMap<>();
    private List<Integer> deletedIndexes = new ArrayList<>();
    private int maxId = 0;

    public Repository() {}

    @Override
    public T save(T obj) {
        int tempId = maxId;
        if (!deletedIndexes.isEmpty()) {
            tempId = deletedIndexes.getFirst();
            deletedIndexes.removeFirst();
        }

        database.put(tempId, obj);
        obj.setId(tempId);
        if (Objects.equals(tempId, maxId)) {
            maxId++;
        }

        return obj;
    }

    @Override
    public T findByID(int id) {
        try {
            return database.get(id);
        } catch (Exception ex) {
            System.out.println("Invalid ID!");
            return null;
        }
    }

    @Override
    public T update(T obj) { //!!!!!!!!!!!!!!!!!!!!!! ID
        int id = obj.getId();
        database.put(id, obj);
        return database.get(id);
    }

    @Override
    public Collection<T> findAll() {
        return database.values();
    }

    @Override
    public boolean deleteById(int id) {
        if (database.get(id) != null) {
            database.remove(id);
            deletedIndexes.add(id);
            return true;
        } else {
            System.out.println("Invalid ID!");
            return false;
        }
    }

    public Map<Integer, T> getDatabase() {
        return database;
    }
}
