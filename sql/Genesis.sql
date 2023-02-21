drop table if exists Agent;
create table Agent (
    Id int PRIMARY KEY AUTO_INCREMENT,
    Name varchar(50) NOT NULL,
    Description varchar(2000),
    InputSize int not null,
    OutputSize int not null,
    HiddenLayerSize int not null,
    HiddenLayerCount int not null
);

drop table if exists AgentData;
create table AgentData (
    Id int PRIMARY KEY AUTO_INCREMENT,
    AgentId int not null,
    Data blob not null,
    FOREIGN KEY (AgentId) REFERENCES Agent(Id)
);

drop table if exists TrainingData;
create table TrainingData (
    Id int PRIMARY KEY AUTO_INCREMENT,
    AgentId int not null,
    InputData blob not null,
    OutputData blob not null,
    FOREIGN KEY (AgentId) REFERENCES Agent(Id)
);