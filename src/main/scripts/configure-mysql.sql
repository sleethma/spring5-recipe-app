-- Use to run mysql db docker image
--# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
-- connect to mysql and run as root user

CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

--Create database service accounts
CREATE USER 'inn_dev_user'@'localhost' IDENTIFIED BY 'inn';
CREATE USER 'inn_prod_user'@'localhost' IDENTIFIED BY 'inn';
CREATE USER 'inn_dev_user'@'%' IDENTIFIED BY 'inn';
CREATE USER 'inn_prod_user'@'%' IDENTIFIED BY 'inn';

--Database grants
GRANT INSERT ON inn_dev.* to 'inn_dev_user'@'localhost';
GRANT DELETE ON inn_dev.* to 'inn_dev_user'@'localhost';
GRANT UPDATE ON inn_dev.* to 'inn_dev_user'@'localhost';
GRANT SELECT ON inn_prod.* to 'inn_prod_user'@'localhost';
GRANT INSERT ON inn_prod.* to 'inn_prod_user'@'localhost';
GRANT DELETE ON inn_prod.* to 'inn_prod_user'@'localhost';
GRANT UPDATE ON inn_prod.* to 'inn_prod_user'@'localhost';

-- @% used for docker
GRANT SELECT ON inn_dev.* to 'inn_dev_user'@'%';
GRANT INSERT ON inn_dev.* to 'inn_dev_user'@'%';
GRANT DELETE ON inn_dev.* to 'inn_dev_user'@'%';
GRANT UPDATE ON inn_dev.* to 'inn_dev_user'@'%';
GRANT SELECT ON inn_prod.* to 'inn_prod_user'@'%';
GRANT INSERT ON inn_prod.* to 'inn_prod_user'@'%';
GRANT DELETE ON inn_prod.* to 'inn_prod_user'@'%';
GRANT UPDATE ON inn_prod.* to 'inn_prod_user'@'%';