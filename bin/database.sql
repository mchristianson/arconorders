create database arconorder;
grant all privileges on arconorder.* to arconorder@localhost identified by '!arconorder!';
grant all privileges on arconorder.* to 'arconorder'@'10.0.2.2' identified by '!arconorder!';
flush privileges;

ALTER TABLE arconorder.user ADD (account_expired BIT NOT NULL);

insert into dubow_submission_arcon_orders (dubow_submission_id,arcon_order_id,arcon_orders_idx) select dubow_submission_arcon_orders_id, arcon_order_id, arcon_orders_idx from dubow_submission_arcon_order;