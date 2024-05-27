package com.softgallery.issuemanagementbackEnd.exception;

import java.sql.SQLException;

public class ObjectNotFoundException extends SQLException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
