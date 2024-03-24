create table accidents_rules(
     id serial primary key,
     rules_id int not null references rules(id),
     accidents_id int not null references accidents(id)
);