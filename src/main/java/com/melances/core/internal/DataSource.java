package com.melances.core.internal;

import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DataSource {

    private final CoreLogger log;
    private SQLiteDataSource ds;

    public DataSource(CoreLogger log) {
        this.log = log;
    }

    public void open(File dbFile) throws SQLException {
        dbFile.getParentFile().mkdirs();
        ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());

        try (Connection c = conn(); Statement st = c.createStatement()) {
            st.execute("PRAGMA journal_mode=WAL");
            st.execute("PRAGMA synchronous=NORMAL");
            st.execute("PRAGMA foreign_keys=ON");
        }

        log.info("[db] SQLite hazır: " + dbFile.getName());
    }

    public Connection conn() throws SQLException {
        if (ds == null) throw new SQLException("DataSource açık değil");
        return ds.getConnection();
    }

    public void close() {
        ds = null;
    }
}
