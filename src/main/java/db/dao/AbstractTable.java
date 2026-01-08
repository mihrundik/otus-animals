package db.dao;

import db.ConnectionManager;

public abstract class AbstractTable {
    protected final ConnectionManager connectionManager;

    public AbstractTable(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

}