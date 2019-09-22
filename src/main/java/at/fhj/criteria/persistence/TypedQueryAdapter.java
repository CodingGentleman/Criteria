package at.fhj.criteria.persistence;

import at.fhj.criteria.entities.Entity;
import at.fhj.criteria.entities.immutable.EntityView;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypedQueryAdapter<T extends Entity<U>, U extends EntityView<T>> implements ViewTypedQuery<T, U> {
    private final TypedQuery<T> delegate;

    TypedQueryAdapter(TypedQuery<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<U> getResultList() {
        return delegate.getResultList().stream().map(Entity::view).collect(Collectors.toList());
    }

    @Override
    public Stream<U> getResultStream() {
        return delegate.getResultStream().map(Entity::view);
    }

    @Override
    public U getSingleResult() {
        return delegate.getSingleResult().view();
    }

    @Override
    public int executeUpdate() {
        return delegate.executeUpdate();
    }

    @Override
    public TypedQuery<T> setMaxResults(int maxResult) {
        return delegate.setMaxResults(maxResult);
    }

    @Override
    public int getMaxResults() {
        return delegate.getMaxResults();
    }

    @Override
    public TypedQuery<T> setFirstResult(int startPosition) {
        return delegate.setFirstResult(startPosition);
    }

    @Override
    public int getFirstResult() {
        return delegate.getFirstResult();
    }

    @Override
    public TypedQuery<T> setHint(String hintName, Object value) {
        return delegate.setHint(hintName, value);
    }

    @Override
    public Map<String, Object> getHints() {
        return delegate.getHints();
    }

    @Override
    public <X> TypedQuery<T> setParameter(Parameter<X> param, X value) {
        return delegate.setParameter(param, value);
    }

    @Override
    public TypedQuery<T> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
        return delegate.setParameter(param, value, temporalType);
    }

    @Override
    public TypedQuery<T> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
        return delegate.setParameter(param, value, temporalType);
    }

    @Override
    public TypedQuery<T> setParameter(String name, Object value) {
        return delegate.setParameter(name, value);
    }

    @Override
    public TypedQuery<T> setParameter(String name, Calendar value, TemporalType temporalType) {
        return delegate.setParameter(name, value, temporalType);
    }

    @Override
    public TypedQuery<T> setParameter(String name, Date value, TemporalType temporalType) {
        return delegate.setParameter(name, value, temporalType);
    }

    @Override
    public TypedQuery<T> setParameter(int position, Object value) {
        return delegate.setParameter(position, value);
    }

    @Override
    public TypedQuery<T> setParameter(int position, Calendar value, TemporalType temporalType) {
        return delegate.setParameter(position, value, temporalType);
    }

    @Override
    public TypedQuery<T> setParameter(int position, Date value, TemporalType temporalType) {
        return delegate.setParameter(position, value, temporalType);
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        return delegate.getParameters();
    }

    @Override
    public Parameter<?> getParameter(String name) {
        return delegate.getParameter(name);
    }

    @Override
    public <X> Parameter<X> getParameter(String name, Class<X> type) {
        return delegate.getParameter(name, type);
    }

    @Override
    public Parameter<?> getParameter(int position) {
        return delegate.getParameter(position);
    }

    @Override
    public <X> Parameter<X> getParameter(int position, Class<X> type) {
        return delegate.getParameter(position, type);
    }

    @Override
    public boolean isBound(Parameter<?> param) {
        return delegate.isBound(param);
    }

    @Override
    public <X> X getParameterValue(Parameter<X> param) {
        return delegate.getParameterValue(param);
    }

    @Override
    public Object getParameterValue(String name) {
        return delegate.getParameterValue(name);
    }

    @Override
    public Object getParameterValue(int position) {
        return delegate.getParameterValue(position);
    }

    @Override
    public TypedQuery<T> setFlushMode(FlushModeType flushMode) {
        return delegate.setFlushMode(flushMode);
    }

    @Override
    public FlushModeType getFlushMode() {
        return delegate.getFlushMode();
    }

    @Override
    public TypedQuery<T> setLockMode(LockModeType lockMode) {
        return delegate.setLockMode(lockMode);
    }

    @Override
    public LockModeType getLockMode() {
        return delegate.getLockMode();
    }

    @Override
    public <X> X unwrap(Class<X> cls) {
        return delegate.unwrap(cls);
    }
}
