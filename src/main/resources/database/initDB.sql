CREATE TABLE IF NOT EXISTS inovus.t_car_numbers
(
    id BIGINT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY ,
    letters  VARCHAR(10) NOT NULL ,
    digits NUMERIC(10) NOT NULL
);