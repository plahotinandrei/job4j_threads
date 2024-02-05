package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        if (memory.putIfAbsent(model.id(), model) != null) {
            throw new OptimisticException("Base already exists");
        }
        return true;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.id(), (id, base) -> {
            if (model.version() != base.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(id, model.name(), model.version() + 1);
        }) != null;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
