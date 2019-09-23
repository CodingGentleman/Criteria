package at.fhj.criteria;

import at.fhj.criteria.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

public class UseCases {
    private Random random = new Random();
    private static final int BATCH_SIZE = 99999;
    private final int count;
    public UseCases(int count){
        this.count = count;
    }

    public void batchInsert() {
        Persistence.INST.inTransaction(this::setSearchPath);
        Persistence.INST.inTransaction(this::insertAddress);
        Persistence.INST.inTransaction(this::insertOrder);
        Persistence.INST.inTransaction(this::insertOrderLine);
        Persistence.INST.inTransaction(this::insertVoucher);
    }

    private void setSearchPath() {
        try {
            getConnection().createStatement().execute("set search_path = 'testdb';");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void insertVoucher() {
        try {
            var psVoucher = getConnection().prepareStatement("INSERT INTO voucher (code, \"value\") VALUES (?, ?)");
            var batchCounterVoucher = 0;
            var psVoucherOrder = getConnection().prepareStatement("INSERT INTO voucher_order (order_id, voucher_code) VALUES (?, ?)");
            var batchCounterVoucherOrder = 0;
            var psVoucherOrderLine = getConnection().prepareStatement("INSERT INTO voucher_orderline (orderline_id, voucher_code) VALUES (?, ?)");
            var batchCounterVoucherOrderLine = 0;
            for (var i = 1; i < (count/4)+1; i++) {
                psVoucher.setString(1, "V"+i);
                psVoucher.setDouble(2, 5 + 15 * random.nextDouble());
                psVoucher.addBatch();
                if(++batchCounterVoucher > BATCH_SIZE) {
                    psVoucher.executeBatch();
                }

                if(i%2==0) {
                    psVoucherOrder.setInt(1, random.nextInt(count)+1);
                    psVoucherOrder.setString(2, "V"+i);
                    psVoucherOrder.addBatch();
                    if(++batchCounterVoucherOrder > BATCH_SIZE) {
                        psVoucherOrder.executeBatch();
                    }
                } else {
                    psVoucherOrderLine.setInt(1, random.nextInt(count*5)+1);
                    psVoucherOrderLine.setString(2, "V"+i);
                    psVoucherOrderLine.addBatch();
                    if(++batchCounterVoucherOrderLine > BATCH_SIZE) {
                        psVoucherOrderLine.executeBatch();
                    }
                }
            }
            psVoucher.executeBatch();
            psVoucherOrder.executeBatch();
            psVoucherOrderLine.executeBatch();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void insertOrderLine() {
        try {
            var psOrderLine = getConnection().prepareStatement("INSERT INTO orderline (order_id, \"name\", quantity) VALUES (?, ?, ?)");
            var batchCounter = 0;
            for (var i = 1; i < count + 1; i++) {
                for (var j = 0; j < 5; j++) {
                    psOrderLine.setInt(1, i);
                    psOrderLine.setString(2, "N"+i+"_"+j);
                    psOrderLine.setInt(3, random.nextInt(10)+1);
                    psOrderLine.addBatch();
                    if(++batchCounter > BATCH_SIZE) {
                        psOrderLine.executeBatch();
                    }
                }
            }
            psOrderLine.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void insertOrder() {
        try {
            var psOrder = getConnection().prepareStatement("INSERT INTO orders (order_type, invoiceaddress_id, deliveryaddress_id) VALUES (?, ?, ?)");
            var batchCounter = 0;
            for (var i = 1; i < count + 1; i++) {
                psOrder.setInt(1, i%2);
                psOrder.setInt(2, i);
                psOrder.setInt(3, random.nextInt(count)+1);
                psOrder.addBatch();
                if(++batchCounter > BATCH_SIZE) {
                    psOrder.executeBatch();
                }
            }
            psOrder.executeBatch();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void insertAddress() {
        try {
            var ps = getConnection().prepareStatement("INSERT INTO address (firstname, lastname) VALUES (?, ?)");
            var batchCounter = 0;
            for (var i = 1; i < count + 1; i++) {
                ps.setString(1, "F" + i);
                ps.setString(2, "L" + i);
                ps.addBatch();
                if(++batchCounter > BATCH_SIZE) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Connection getConnection() {
        switch (Persistence.INST.getPersistenceProvider()) {
            case HIBERNATE:
                final var session = Persistence.INST.getEntityManager().unwrap(Session.class);
                var connectionRetriever = new HibernateConnectionRetriever();
                session.doWork(connectionRetriever);
                return connectionRetriever.getConnection();
            case OPENJPA:
            case ECLIPSELINK:
                return Persistence.INST.getEntityManager().unwrap(java.sql.Connection.class);
        }
        throw new IllegalStateException("connection retrieval not implemented for persistence provider");
    }

    private static class HibernateConnectionRetriever implements Work {
        Connection conn;

        @Override
        public void execute(Connection arg0) throws SQLException {
            this.conn = arg0;
        }

        Connection getConnection() {
            return conn;
        }
    }
}
