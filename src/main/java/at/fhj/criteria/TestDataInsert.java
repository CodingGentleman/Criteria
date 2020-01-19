package at.fhj.criteria;

import at.fhj.criteria.persistence.DatabaseManagementSystem;
import at.fhj.criteria.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

class TestDataInsert {
    private Random random = new Random();
    private static final int BATCH_SIZE = 99999;
    private final int count;
    TestDataInsert(int count){
        this.count = count;
    }

    public void batchInsert() {
        Persistence.INST.inTransaction(this::setSearchPath);
        Persistence.INST.inTransaction(this::deleteContents);
        Persistence.INST.inTransaction(this::insertAddress);
        Persistence.INST.inTransaction(this::insertOrder);
        Persistence.INST.inTransaction(this::insertOrderLine);
        Persistence.INST.inTransaction(this::insertVoucher);
    }

    private void setSearchPath() {
        try {
            if(Persistence.INST.getDatabaseManagementSystem().equals(DatabaseManagementSystem.POSTGRESQL)) {
                getConnection().createStatement().execute("set search_path = 'testdb';");
            }
            if(Persistence.INST.getDatabaseManagementSystem().equals(DatabaseManagementSystem.MARIADB)) {
                getConnection().createStatement().execute("use testdb;");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void deleteContents() {
        try {
            if(Persistence.INST.getDatabaseManagementSystem().equals(DatabaseManagementSystem.MARIADB)) {
                getConnection().createStatement().execute("alter table orders drop foreign key if exists FK815e35tfcxxlx9xkenwgatfe9");
                getConnection().createStatement().execute("alter table orders drop foreign key if exists FKhhuey13s7mgv2n7kutihni42a");
                getConnection().createStatement().execute("alter table voucher_order drop foreign key if exists FKd7f74paunm85bbug2aidesuug");
                getConnection().createStatement().execute("alter table voucher_order drop foreign key if exists FKpytdu5c7lwmkg9injlrfd7gv");
                getConnection().createStatement().execute("alter table voucher_orderline drop foreign key if exists FKcmttgei4ik8hw6aaelxa7459m");
                getConnection().createStatement().execute("alter table voucher_orderline drop foreign key if exists FKmiaq6qc8sqknxy332wevqwoxi");
                getConnection().createStatement().execute("drop table if exists address");
                getConnection().createStatement().execute("drop table if exists orderline");
                getConnection().createStatement().execute("drop table if exists orders");
                getConnection().createStatement().execute("drop table if exists voucher");
                getConnection().createStatement().execute("drop table if exists voucher_order");
                getConnection().createStatement().execute("drop table if exists voucher_orderline");
                getConnection().createStatement().execute("create table address (id bigint not null auto_increment, firstname varchar(255), lastname varchar(255), primary key (id)) engine=InnoDB");
                getConnection().createStatement().execute("create table orderline (id bigint not null auto_increment, name varchar(255), quantity bigint not null, order_id bigint, primary key (id)) engine=InnoDB");
                getConnection().createStatement().execute("create table orders (id bigint not null auto_increment, order_type integer, deliveryAddress_id bigint, invoiceAddress_id bigint not null, primary key (id)) engine=InnoDB");
                getConnection().createStatement().execute("create table voucher (code varchar(255) not null, value double precision, primary key (code)) engine=InnoDB");
                getConnection().createStatement().execute("create table voucher_order (voucher_code varchar(255) not null, order_id bigint not null) engine=InnoDB");
                getConnection().createStatement().execute("create table voucher_orderline (voucher_code varchar(255) not null, orderline_id bigint not null) engine=InnoDB");
                getConnection().createStatement().execute("alter table orders add constraint UK_571fchrhmx8oady23pqy37wk2 unique (invoiceAddress_id)");
                getConnection().createStatement().execute("alter table orderline add constraint FK7jhn7tvm2wi722qnm2ilw06hh foreign key (order_id) references orders (id)");
                getConnection().createStatement().execute("alter table orders add constraint FK815e35tfcxxlx9xkenwgatfe9 foreign key (deliveryAddress_id) references address (id)");
                getConnection().createStatement().execute("alter table orders add constraint FKhhuey13s7mgv2n7kutihni42a foreign key (invoiceAddress_id) references address (id)");
                getConnection().createStatement().execute("alter table voucher_order add constraint FKd7f74paunm85bbug2aidesuug foreign key (order_id) references orders (id)");
                getConnection().createStatement().execute("alter table voucher_order add constraint FKpytdu5c7lwmkg9injlrfd7gv foreign key (voucher_code) references voucher (code)");
                getConnection().createStatement().execute("alter table voucher_orderline add constraint FKcmttgei4ik8hw6aaelxa7459m foreign key (orderline_id) references orderline (id)");
                getConnection().createStatement().execute("alter table voucher_orderline add constraint FKmiaq6qc8sqknxy332wevqwoxi foreign key (voucher_code) references voucher (code)");
            }
            getConnection().createStatement().execute("DELETE FROM voucher_order");
            getConnection().createStatement().execute("DELETE FROM voucher_orderline");
            getConnection().createStatement().execute("DELETE FROM orderline");
            getConnection().createStatement().execute("DELETE FROM orders");
            getConnection().createStatement().execute("DELETE FROM address");
            getConnection().createStatement().execute("DELETE FROM voucher");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertVoucher() {
        try {
            var psVoucher = getConnection().prepareStatement("INSERT INTO voucher (code, value) VALUES (?, ?)");
            var batchCounterVoucher = 0;
            var psVoucherOrder = getConnection().prepareStatement("INSERT INTO voucher_order (order_id, voucher_code) VALUES (?, ?)");
            var batchCounterVoucherOrder = 0;
            var psVoucherOrderLine = getConnection().prepareStatement("INSERT INTO voucher_orderline (orderline_id, voucher_code) VALUES (?, ?)");
            var batchCounterVoucherOrderLine = 0;
            for (var i = 1; i < (count/4)+1; i++) {
                psVoucher.setString(1, "V"+i);
                if(i < 3) {
                    psVoucher.setDouble(2, BigDecimal.valueOf(i).divide(BigDecimal.valueOf(10)).add(BigDecimal.valueOf(5)).doubleValue());
                } else {
                    psVoucher.setDouble(2, 5 + 15 * random.nextDouble());
                }
                psVoucher.addBatch();
                if(++batchCounterVoucher > BATCH_SIZE) {
                    psVoucher.executeBatch();
                    batchCounterVoucher = 0;
                }

                if(i%2==0) {
                    psVoucherOrder.setInt(1, random.nextInt(count)+1);
                    psVoucherOrder.setString(2, "V"+i);
                    psVoucherOrder.addBatch();
                    if(++batchCounterVoucherOrder > BATCH_SIZE) {
                        psVoucherOrder.executeBatch();
                        batchCounterVoucherOrder = 0;
                    }
                } else {
                    psVoucherOrderLine.setInt(1, random.nextInt(count*5)+1);
                    psVoucherOrderLine.setString(2, "V"+i);
                    psVoucherOrderLine.addBatch();
                    if(++batchCounterVoucherOrderLine > BATCH_SIZE) {
                        psVoucherOrderLine.executeBatch();
                        batchCounterVoucherOrderLine = 0;
                    }
                }
            }
            if(batchCounterVoucher != 0) {
                psVoucher.executeBatch();
            }
            if(batchCounterVoucherOrder != 0) {
                psVoucherOrder.executeBatch();
            }
            if(batchCounterVoucherOrderLine != 0) {
                psVoucherOrderLine.executeBatch();
            }
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void insertOrderLine() {
        try {
            var psOrderLine = getConnection().prepareStatement("INSERT INTO orderline (order_id, name, quantity) VALUES (?, ?, ?)");
            var batchCounter = 0;
            for (var i = 1; i < count + 1; i++) {
                for (var j = 0; j < 5; j++) {
                    psOrderLine.setInt(1, i);
                    psOrderLine.setString(2, "N"+i+"_"+j);
                    psOrderLine.setInt(3, random.nextInt(10)+1);
                    psOrderLine.addBatch();
                    if(++batchCounter > BATCH_SIZE) {
                        psOrderLine.executeBatch();
                        batchCounter = 0;
                    }
                }
            }
            if(batchCounter != 0) {
                psOrderLine.executeBatch();
            }
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
                    batchCounter = 0;
                }
            }
            if(batchCounter != 0) {
                psOrder.executeBatch();
            }
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
                    batchCounter = 0;
                }
            }
            for (var i = 1; i < count + 1; i++) {
                ps.setString(1, "detached"+i);
                ps.setString(2, "detached"+i);
                ps.addBatch();
                if(++batchCounter > BATCH_SIZE) {
                    ps.executeBatch();
                    batchCounter = 0;
                }
            }
            if(batchCounter != 0) {
                ps.executeBatch();
            }
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
