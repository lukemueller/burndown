CREATE TABLE projects (
  id          MEDIUMINT    NOT NULL AUTO_INCREMENT,
  name        VARCHAR(255) NOT NULL,
  start_date  DATE         NOT NULL,
  hourly_rate INT          NOT NULL,
  PRIMARY KEY (id)
);