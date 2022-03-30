
## Criando e populando Banco:

USE clinica;



CREATE TABLE enderecos(
    id INT auto_increment PRIMARY KEY,
    rua VARCHAR(64),
    numero VARCHAR(12),
    cidade VARCHAR(64),
    bairro VARCHAR(64),
    estado VARCHAR(2));

CREATE TABLE dentistas (
    id INT auto_increment PRIMARY KEY,
    nome VARCHAR(64),
    email VARCHAR(64),
    numMatricula INT,
    atendeConvenio INT);

CREATE TABLE usuarios (
    id INT auto_increment PRIMARY KEY,
    nome VARCHAR(64),
    email VARCHAR(64),
    senha VARCHAR(255),
    acesso INT);

CREATE TABLE pacientes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(64),
    sobrenome VARCHAR(64),
    cpf VARCHAR(16),
    dataCad DATE,
    id_endereco INT,
    FOREIGN KEY (id_endereco) REFERENCES enderecos(id));

CREATE TABLE consultas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT,
    id_dentista INT,
    id_usuario INT,
    dataCad DATE,
    dataAtend DATE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id),
    FOREIGN KEY (id_dentista) REFERENCES dentistas(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id));
    
INSERT INTO dentistas (nome, email, numMatricula, atendeConvenio) 
VALUES ("Robert Hooke", "robert.hooke@gmail.com", 32541, 1);
INSERT INTO dentistas (nome, email, numMatricula, atendeConvenio) 
VALUES ("Theodor Schwann", "schwann.dentista@hotmail.com", 14520, 0);
INSERT INTO dentistas (nome, email, numMatricula, atendeConvenio) 
VALUES ("Alexander von Humboldt", "vonhumboldt@outlook.com", 63254, 1);

INSERT INTO usuarios (nome, email, senha, acesso) 
VALUES ("Jean-Jacques Rousseau", "jjrousseau@outlook.com", "zxc321", 1);
INSERT INTO usuarios (nome, email, senha, acesso) 
VALUES ("John Locke", "j.locke@gmail.com", "qwe456", 2);
INSERT INTO usuarios (nome, email, senha, acesso) 
VALUES ("Adam Smith", "adams@hotmail.com", "mnb987", 3);

INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("das Alamedas", "365", "Centro", "Porto Alegre", "RS");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("das hortências", "53", "Centro", "Florianópolis", "SC");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("do Forte", "988", "São Pedro", "Curitiba", "PR");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("Jaguarão", "478", "Industrial", "São Paulo", "SP");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("Congonhas", "433", "Centro", "Rio de Janeiro", "RJ");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("Ruth Almeida", "766", "São Pedro", "Belo Horizonte", "MG");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("Paquetá", "122", "Santa Cecília", "Recife", "PE");
INSERT INTO enderecos (rua, numero, bairro, cidade, estado) 
VALUES ("7 de Setembro", "233", "Industrial", "Belém", "PA");
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Freddie", "Mercury", "111.111.111-11", CURRENT_DATE, 1);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Roger", "Waters", "222.222.222-22", CURRENT_DATE, 2);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Elvis", "Presley", "333.333.333-33", CURRENT_DATE, 3);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Kurt", "Cobain", "444.444.444-44", CURRENT_DATE, 4);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Bob", "Dylan", "555.555.555-55", CURRENT_DATE, 5);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Jimi", "Hendrix", "666.666.666-66", CURRENT_DATE, 6);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("Paul", "McCartney", "777.777.777-77", CURRENT_DATE, 7);
INSERT INTO pacientes (nome, sobrenome, cpf, dataCad, id_endereco) 
VALUES ("David", "Bowie", "888.888.888-88", CURRENT_DATE, 8);