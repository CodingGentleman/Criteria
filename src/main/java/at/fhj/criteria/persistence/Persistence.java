package at.fhj.criteria.persistence;

import at.fhj.criteria.entities.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public enum Persistence {
    INST;
    private ViewEntityManager entityManager;
    private EntityManager originalEntityManager;
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

    public EntityManager getOriginalEntityManager() {
        return originalEntityManager;
    }

    public void register(DatabaseManagementSystem databaseManagementSystem, PersistenceProvider persistenceProvider) {
        this.databaseManagementSystem = databaseManagementSystem;
        this.persistenceProvider = persistenceProvider;
        var persistenceName = databaseManagementSystem.name() + "_" + persistenceProvider.name();
        managerFactory = javax.persistence.Persistence.createEntityManagerFactory(persistenceName.toLowerCase());
        originalEntityManager = managerFactory.createEntityManager();
        entityManager = new EntityManagerAdapter(originalEntityManager);
    }

    public void persist(Entity entity) {
        inTransaction(() -> entityManager.persist(entity));
    }

    public void close() {
        entityManager.close();
        managerFactory.close();
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
        if(!transaction.isActive()) {
            transaction.begin();
        }
        runnable.run();
        transaction.commit();
    }
}