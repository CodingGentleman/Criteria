package at.fhj.criteria.persistence;

import at.fhj.criteria.entities.Entity;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public enum Persistence {
    INST;
    private ViewEntityManager entityManager;
    private EntityManagerFactory managerFactory;
    private DatabaseManagementSystem databaseManagementSystem;
    private PersistenceProvider persistenceProvider;

    Persistence(){}

    public DatabaseManagementSystem getDatabaseManagementSystem() {
        return databaseManagementSystem;
    }

    public PersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }

    public ViewEntityManager getEntityManager() {
        return entityManager;
    }

    public void register(DatabaseManagementSystem databaseManagementSystem, PersistenceProvider persistenceProvider) {
        this.databaseManagementSystem = databaseManagementSystem;
        this.persistenceProvider = persistenceProvider;
        var persistenceName = databaseManagementSystem.name() + "_" + persistenceProvider.name();
        managerFactory = javax.persistence.Persistence.createEntityManagerFactory(persistenceName.toLowerCase());
        entityManager = new EntityManagerProxy(managerFactory.createEntityManager());
    }

    public void persist(Entity entity) {
        inTransaction(() -> entityManager.persist(entity));
    }

    public void close() {
        entityManager.close();
        managerFactory.close();
    }

    public <T extends Entity> T find(Class<T> entityClass, int id) {
        T result = entityManager.find(entityClass, id);
        // needed to ignore cache
        this.refresh(result);
        return result;
    }

    public <T extends Entity> List<T> findAll(Class<T> entityClass) {
        var query = entityManager.createQuery("from "+entityClass.getSimpleName(), entityClass);
        var resultList = query.getResultList();
        // needed to ignore cache
        resultList.forEach(this::refresh);
        return resultList;
    }

    public <T extends Entity> List<T> findAllCriteria(Class<T> entityClass) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var q = criteriaBuilder.createQuery(entityClass);
        q.select(q.from(entityClass));
        var query = entityManager.createQuery(q);
        var resultList = query.getResultList();
        // needed to ignore cache
        resultList.forEach(this::refresh);
        return resultList;
    }

    public void remove(Entity entity) {
        inTransaction(() -> entityManager.remove(entity));
    }

    public void merge(Entity entity) {
        inTransaction(() -> entityManager.merge(entity));
    }

    public void refresh(Entity entity) {
        if(entity != null) {
            entityManager.refresh(entity);
        }
    }

    public void inTransaction(Runnable runnable) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        runnable.run();
        transaction.commit();
    }
}