-- Criando o banco de daods
create database [GestaoStock]
go

use [GestaoStock]
go

-- Criando a tabela [categoria]
create table categoria(
    id int not null identity, 
    nome varchar(256) not null,

    constraint [pk_categoria] primary key ([id])
)
go

-- Criando a tabela [sub_categoria]
create table sub_categoria(
    id int not null identity, 
    categoria_id int not null,
    nome varchar(256) not null,

    constraint [pk_sub_categoria] primary key ([id]),
    constraint [fk_sub_categoria_categoria] foreign key ([categoria_id]) references [categoria]([id])
)
go
-- Criando a tabela [produto]
create table produto(
    id int not null identity, 
    nome varchar(256) not null,
    qtd_stock int not null,
    categoria_id int not null,
    sub_categoria_id int not null,
    data_cadastro Date not null,

    constraint [pk_produto] primary key ([id]),
    -- constraint [fk_produto_categoria] foreign key([categoria_id]) references [categoria]([id]),
    -- constraint [fk_produto_sub_categoria] foreign key([sub_categoria_id]) references [sub_categoria]([id])
)
go

-- Criando a tabela [usuario]
create table usuario(
    id int not null identity, 
    nome_usuario varchar(40) not null,
    senha varchar(100) not null,

    constraint [pk_usuario] primary key ([id]),
)
go
