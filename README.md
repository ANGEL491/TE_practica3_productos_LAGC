# TE_practica3_productos_LAGC
PRACTICA N 3 CONEXION A BD

Nombre :Luis Angel Gutierrez Cantuta

Materia : Tecnologias Emergentes II

fecha: 27  octubre 2020

creacion de la base de datos:

CREATE DATABASE bd_almacen CHARACTER SET utf8 COLLATE utf8_general_ci;
use bd_almacen;
create table productos (
id int unsigned auto_increment primary key,
nombre_producto varchar(250) not null,
precio double not null,
stock int not null
);
