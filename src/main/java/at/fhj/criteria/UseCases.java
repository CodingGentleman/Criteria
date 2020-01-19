package at.fhj.criteria;

import at.fhj.criteria.entities.*;
import at.fhj.criteria.persistence.Criteria;
import at.fhj.criteria.persistence.Persistence;
import at.fhj.criteria.persistence.PersistenceProvider;
import at.fhj.criteria.persistence.PersistenceReader;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UseCases {
    private EntityManager entityManager;
    private String lastname;
    private static int deleteIndex = 1;

    public UseCases(int testDataQuantity) {
        this.lastname = "L" + (new Random().nextInt(testDataQuantity)+1);
        new TestDataInsert(testDataQuantity).batchInsert();
        entityManager = Persistence.INST.getOriginalEntityManager();
    }

    public long selectCriteria() {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Address.class);
        var root = query.from(Address.class);
        query.select(root);
        query.where(cb.equal(root.get(Address_.lastname), lastname));
        return entityManager.createQuery(query).getSingleResult().getId();
    }

    public long selectHql() {
        var query  = entityManager.createQuery("FROM "+Address.class.getSimpleName()+" a WHERE a.lastname = :lastname", Address.class);
        query.setParameter("lastname", lastname);
        return query.getSingleResult().getId();
    }

    public long selectSql() {
        var query = entityManager.createNativeQuery("select id from "+Address.TABLE_NAME+" where lastname = ?");
        query.setParameter(1, lastname);
        var result = query.getSingleResult();
        if(result instanceof Long) {
            return (Long) result;
        }
        if(result instanceof Integer) {
            return (Integer) result;
        }
        return ((BigInteger)result).intValue();
    }

    public int updateCriteria() {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createCriteriaUpdate(Address.class);
        var root = query.from(Address.class);
        query.where(cb.equal(root.get(Address_.lastname), lastname));
        query.set(Address_.firstname, "updated");
        return entityManager.createQuery(query).executeUpdate();
    }

    public int updateHql() {
        var query  = entityManager.createQuery("UPDATE "+Address.class.getSimpleName()+" a SET a.firstname = :newValue WHERE a.lastname = :lastname");
        query.setParameter("newValue", "updated");
        query.setParameter("lastname", lastname);
        return query.executeUpdate();
    }

    public int updateSql() {
        var query = entityManager.createNativeQuery("UPDATE "+Address.TABLE_NAME+" SET firstname = ? WHERE lastname = ?");
        query.setParameter(1, "updated");
        query.setParameter(2, lastname);
        return query.executeUpdate();
    }

    public int deleteCriteria() {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createCriteriaDelete(Address.class);
        var root = query.from(Address.class);
        query.where(cb.equal(root.get(Address_.lastname), "detached"+(deleteIndex++)));
        return entityManager.createQuery(query).executeUpdate();
    }

    public int deleteHql() {
        var query  = entityManager.createQuery("DELETE FROM "+ Address.class.getSimpleName()+" a WHERE a.lastname = :lastname");
        query.setParameter("lastname", "detached"+(deleteIndex++));
        return query.executeUpdate();
    }

    public int deleteSql() {
        var query = entityManager.createNativeQuery("DELETE FROM "+Address.TABLE_NAME+" WHERE lastname = ?");
        query.setParameter(1, "detached"+(deleteIndex++));
        return query.executeUpdate();
    }

    public int queryCriteria() {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Long.class);
        var root = query.from(Order.class);
        var vouchers = root.join(Order_.vouchers);
        var invoiceAddress = root.join(Order_.invoiceAddress);
        query.where(cb.greaterThan(vouchers.get(Voucher_.value), 10.0));
        query.select(cb.count(invoiceAddress));
        return entityManager.createQuery(query).getSingleResult().intValue();
    }

    public int queryHql() {
        var query  = entityManager.createQuery("select count(a) from "+Order.class.getSimpleName()+" o join o.vouchers v join o.invoiceAddress a where v.value > :minValue", Long.class);
        query.setParameter("minValue", 10d);
        return query.getSingleResult().intValue();
    }

    public int querySql() {
        var query  = entityManager.createNativeQuery(
                "select count(a.id) " +
                "from orders o " +
                "join voucher_order vo on o.id = vo.order_id " +
                "join voucher v on v.code = vo.voucher_code " +
                "join address a on o.invoiceaddress_id = a.id " +
                "where v.value > ?");
        query.setParameter(1, 10d);
        var result = query.getSingleResult();
        if(result instanceof Long) {
            return ((Long)result).intValue();
        }
        return ((BigInteger)result).intValue();
    }

    public int subQueryCriteria() {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Long.class);
        var root = query.from(Voucher.class);

        var subQuery = query.subquery(Double.class);
        var subRoot = subQuery.from(Voucher.class);
        subQuery.select(cb.avg(subRoot.get(Voucher_.value)));

        query.where(cb.greaterThan(root.get(Voucher_.value), subQuery));
        query.select(cb.count(root.get(Voucher_.value)));
        return entityManager.createQuery(query).getSingleResult().intValue();
    }

    public int subQueryHql() {
        var query = entityManager.createQuery(
                "select count(aboveAvgVoucher) from "+Voucher.class.getSimpleName()+" aboveAvgVoucher where aboveAvgVoucher.value > ( select avg(v.value) from Voucher v )",
                Long.class);
        return query.getSingleResult().intValue();
    }

    public int subQuerySql() {
        var query = entityManager.createNativeQuery(
                "select count(aboveAvgVoucher.code) from "+Voucher.TABLE_NAME+" aboveAvgVoucher where aboveAvgVoucher.value > ( select avg(v.value) from "+Voucher.TABLE_NAME+" v )");
        var result = query.getSingleResult();
        if(result instanceof Long) {
            return ((Long)result).intValue();
        }
        return ((BigInteger)result).intValue();
    }

    public int castCriteria() {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Long.class);
        var root = query.from(Voucher.class);
        query.where(cb.equal(root.get(Voucher_.value).as(Integer.TYPE), 5));
        query.select(cb.count(root.get(Voucher_.code)));
        return entityManager.createQuery(query).getSingleResult().intValue();
    }

    public int castHql() {
        if(Persistence.INST.getPersistenceProvider().equals(PersistenceProvider.ECLIPSELINK)) {
            var query = entityManager.createQuery(
                    "select count(v.code) from "+Voucher.class.getSimpleName()+" v where cast(v.value integer) = 5",
                    Long.class);
            return query.getResultList().iterator().next().intValue();
        } else {
            var query = entityManager.createQuery(
                    "select count(v.code) from "+Voucher.class.getSimpleName()+" v where cast(v.value as integer) = 5",
                    Long.class);
            return query.getSingleResult().intValue();
        }
    }

    public int castSql() {
        var query = entityManager.createNativeQuery(
                "select count(code) from "+Voucher.TABLE_NAME+" where cast(value as int) = 5");
        var result = query.getSingleResult();
        if(result instanceof Long) {
            return ((Long)result).intValue();
        }
        return ((BigInteger)result).intValue();
    }
}
