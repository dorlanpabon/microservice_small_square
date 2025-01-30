INSERT INTO roles (id, name, description) VALUES
                                                (1, 'ADMINISTRATOR', 'Administrator of the system'),
                                                (2, 'OWNER', 'Owner of a restaurant'),
                                                (3, 'EMPLOYEE', 'Employee of a restaurant'),
                                                (4, 'CLIENT', 'Client of a restaurant')
    ON DUPLICATE KEY UPDATE description = VALUES(description);
