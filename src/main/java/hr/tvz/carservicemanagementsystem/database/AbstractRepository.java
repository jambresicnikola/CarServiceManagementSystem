package hr.tvz.carservicemanagementsystem.database;

import hr.tvz.carservicemanagementsystem.model.Entity;
import java.util.List;

/**
 * Abstract base class for all database repositories.
 * Defines the common CRUD contract that all repository implementations must fulfill.
 *
 * @param <T> the entity type managed by this repository, must extend {@link Entity}
 */
abstract class AbstractRepository<T extends Entity> {

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity
     * @return the entity, or {@code null} if not found
     */
    public abstract T findById(Long id);

    /**
     * Retrieves all entities of type {@code T} from the database.
     *
     * @return list of all entities, empty list if none exist
     */
    public abstract List<T> findAll();

    /**
     * Persists a new entity to the database.
     *
     * @param entity the entity to save
     */
    public abstract void save(T entity);

    /**
     * Deletes an entity from the database.
     *
     * @param entity the entity to delete
     */
    public abstract void delete(T entity);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity with updated values
     */
    public abstract void update(T entity);
}