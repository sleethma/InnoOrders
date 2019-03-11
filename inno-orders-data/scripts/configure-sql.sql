--Database grants
GRANT INSERT ON inn_dev.* to 'inn_dev_user'@'localhost';
GRANT DELETE ON inn_dev.* to 'inn_dev_user'@'localhost';
GRANT UPDATE ON inn_dev.* to 'inn_dev_user'@'localhost';
GRANT SELECT ON inn_prod.* to 'inn_prod_user'@'localhost';
GRANT INSERT ON inn_prod.* to 'inn_prod_user'@'localhost';
GRANT DELETE ON inn_prod.* to 'inn_prod_user'@'localhost';
GRANT UPDATE ON inn_prod.* to 'inn_prod_user'@'localhost';