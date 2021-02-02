package dev.gigafyde.froggyowl.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger("Database");
    private final AtomicReference<Connection> connection = new AtomicReference<>();
    private final MysqlDataSource dataSource = new MysqlDataSource();
    private final AtomicBoolean isReconnecting = new AtomicBoolean(false);
    private final String databaseName = "s2_froggyowl";

    public Database() throws SQLException {
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));
        dataSource.setPort(Integer.parseInt(System.getenv("MYSQL_PORT")));
        dataSource.setServerName(System.getenv("MYSQL_HOST"));
        connection.set(dataSource.getConnection());

    }

    void printException(SQLException e) {
        try {
            if (connection.get().isValid(0)) LOGGER.warn("A Database error occurred", e);
        } catch (SQLException ignored) {
        }

        if (!isReconnecting.get()) {
            LOGGER.warn("A Database error occurred", e);
            isReconnecting.set(true);
            reconnect(0);
            isReconnecting.set(false);
            LOGGER.trace("Notifying all...");
            synchronized (this) {
                notifyAll();
            }
            LOGGER.trace("Notified all");
        } else {
            LOGGER.trace("Waiting...");
            while (isReconnecting.get()) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException ignored) {
                }
            }
            LOGGER.trace("Waited");
        }
    }

    private void reconnect(int attempt) {
        try {
            if (!connection.get().isValid(0)) connection.set(dataSource.getConnection());
            LOGGER.info("Database Reconnected");
        } catch (SQLException ignored) {
            try {
                Thread.sleep(Math.min(10_000 + (1_000 * attempt++), 60_000));
            } catch (InterruptedException ignore) {
            }
            reconnect(attempt);
        }
    }

    public void setStatusMessageId(Long message_id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement("INSERT INTO `" + databaseName + "`.`discord_bot` (`message_id`)\n" +
                "VALUES (?)\n")) {
            statement.setLong(1, message_id);
            statement.execute();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public Long getStatusMessageId() {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT `message_id` FROM `" + databaseName + "`.`discord_bot`")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("message_id");
            }
        } catch (SQLException e) {
            printException(e);
        }
        return 0L;
    }
}
