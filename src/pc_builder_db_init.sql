CREATE DATABASE IF NOT EXISTS PC_Builder_DB;
USE PC_Builder_DB;

# USER_ACCOUNT (Username, Password)
CREATE TABLE IF NOT EXISTS USER_ACCOUNT (
                              Username VARCHAR(255) NOT NULL PRIMARY KEY,
                              Password VARCHAR(255) NOT NULL
);

# ADMIN_ACCOUNT (Username, Password)
CREATE TABLE IF NOT EXISTS ADMIN_ACCOUNT (
                               Username VARCHAR(255) NOT NULL PRIMARY KEY,
                               Password VARCHAR(255) NOT NULL
);

# COMPUTER (ComputerID, AccUsername [FK -> USER_ACCOUNT.Username], ComputerName)
CREATE TABLE IF NOT EXISTS COMPUTER (
                          ComputerID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          AccUsername VARCHAR(255) NOT NULL,
                          ComputerName VARCHAR(255) NOT NULL,
                          FOREIGN KEY (AccUsername) REFERENCES USER_ACCOUNT(Username) ON DELETE CASCADE
);

# MOTHERBOARD (MotherboardID, MotherboardName, MotherboardPrice, MemorySlots)
CREATE TABLE IF NOT EXISTS MOTHERBOARD (
                             MotherboardID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                             MotherboardName VARCHAR(255) NOT NULL,
                             MotherboardPrice DECIMAL(10,2) NOT NULL,
                             MemorySlots INT NOT NULL
);

# PART (PartID, PartName, PartPrice)
CREATE TABLE IF NOT EXISTS PART (
                      PartID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      PartName VARCHAR(255) NOT NULL,
                      PartPrice DECIMAL(10,2) NOT NULL
);

# RAM (PartID [FK -> PART.PartID], Size, Speed, Modules)
CREATE TABLE IF NOT EXISTS RAM (
                     PartID INT NOT NULL PRIMARY KEY,
                     Size INT NOT NULL,
                     Speed INT NOT NULL,
                     Modules INT NOT NULL,
                     FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# STORAGE (PartID [FK -> PART.PartID], Capacity, Type, Interface, FormFactor)
CREATE TABLE IF NOT EXISTS STORAGE (
                         PartID INT NOT NULL PRIMARY KEY,
                         Capacity INT NOT NULL,
                         Type VARCHAR(255) NOT NULL,
                         Interface VARCHAR(255) NOT NULL,
                         FormFactor VARCHAR(255) NOT NULL,
                         FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# PROCESSOR (PartID [FK -> PART.PartID], CoreCount, CoreClkSpd, BoostClkSpd)
CREATE TABLE IF NOT EXISTS PROCESSOR (
                           PartID INT NOT NULL PRIMARY KEY,
                           CoreCount INT NOT NULL,
                           CoreClkSpd DECIMAL(2,1) NOT NULL,
                           BoostClkSpd DECIMAL(2,1) NOT NULL,
                           FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# GRAPHICS_CARD (PartID [FK -> PART.PartID], Chipset, Memory, Length)
CREATE TABLE IF NOT EXISTS GRAPHICS_CARD (
                               PartID INT NOT NULL PRIMARY KEY,
                               Chipset VARCHAR(255) NOT NULL,
                               Memory INT NOT NULL,
                               Length INT NOT NULL,
                               FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# POWER_SUPPLY (PartID [FK -> PART.PartID], Wattage, EfficiencyRating, Modular)
CREATE TABLE IF NOT EXISTS POWER_SUPPLY (
                              PartID INT NOT NULL PRIMARY KEY,
                              Wattage INT NOT NULL,
                              EfficiencyRating INT NOT NULL,
                              Modular BOOLEAN NOT NULL,
                              FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# CONTAINS_MB (ComputerID [FK -> COMPUTER.ComputerID], MotherboardID [FK -> MOTHERBOARD.MotherboardID])
CREATE TABLE IF NOT EXISTS CONTAINS_MB (
                             ComputerID INT NOT NULL,
                             MotherboardID INT NOT NULL,
                             PRIMARY KEY (ComputerID, MotherboardID),
                             FOREIGN KEY (ComputerID) REFERENCES COMPUTER(ComputerID) ON DELETE CASCADE,
                             FOREIGN KEY (MotherboardID) REFERENCES MOTHERBOARD(MotherboardID) ON DELETE CASCADE
);

# CONTAINS_PART (ComputerID [FK -> COMPUTER.ComputerID], PartID [FK -> PART.PartID], Quantity)
CREATE TABLE IF NOT EXISTS CONTAINS_PART (
                               ComputerID INT NOT NULL,
                               PartID INT NOT NULL,
                               Quantity INT NOT NULL,
                               PRIMARY KEY (ComputerID, PartID),
                               FOREIGN KEY (ComputerID) REFERENCES COMPUTER(ComputerID) ON DELETE CASCADE,
                               FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# MB_COMPATIBLE (MotherboardID [FK -> MOTHERBOARD.MotherboardID], PartID [FK -> PART.PartID])
CREATE TABLE IF NOT EXISTS MB_COMPATIBLE (
                               MotherboardID INT NOT NULL,
                               PartID INT NOT NULL,
                               PRIMARY KEY (MotherboardID, PartID),
                               FOREIGN KEY (MotherboardID) REFERENCES MOTHERBOARD(MotherboardID) ON DELETE CASCADE,
                               FOREIGN KEY (PartID) REFERENCES PART(PartID) ON DELETE CASCADE
);

# PSU_COMPATIBLE (PowerSupplyID [FK -> PART.PartID], GraphicsCardID [FK -> PART.PartID])
CREATE TABLE IF NOT EXISTS PSU_COMPATIBLE (
                                PowerSupplyID INT NOT NULL,
                                GraphicsCardID INT NOT NULL,
                                PRIMARY KEY (PowerSupplyID, GraphicsCardID),
                                FOREIGN KEY (PowerSupplyID) REFERENCES PART(PartID) ON DELETE CASCADE,
                                FOREIGN KEY (GraphicsCardID) REFERENCES PART(PartID) ON DELETE CASCADE
);

INSERT INTO USER_ACCOUNT
VALUES ('johny123', 'pass1234'),
       ('daisiesAreAwesome', 'flowersWin'),
       ('XXsupermanXX', 'hateBatman');

INSERT INTO ADMIN_ACCOUNT
VALUES ('CDigory', 'potterStinks'),
       ('yinYang', 'opposites456'),
       ('OffbrandMario', 'LuigiIsCool');

INSERT INTO COMPUTER (AccUsername, ComputerName)
VALUES ('johny123', 'Computer74'),
       ('XXsupermanXX', 'notYours');

INSERT INTO MOTHERBOARD (MotherboardName, MotherboardPrice, MemorySlots)
VALUES ('MSY C650 GAMING X', 199.99, 4),
       ('ASBlock A770 Pro', 89.99, 2);

INSERT INTO PART (PartName, PartPrice)
VALUES ('Sulfur Power Gaming', 29.99),
       ('L.Skill Tearjaws', 49.99),
       ('Samsong 660 Pro', 149.99),
       ('Prinston NV3', 35.99),
       ('DMA Zyren 5 5000', 89.99),
       ('Inteligence Core y7-14000', 349.99),
       ('CSI JeForce Venus 2E', 319.99),
       ('ACBoulder Challenging D', 249.99),
       ('SonicSea Y 1250 W', 109.99),
       ('Sogetop GN 650 W', 59.99);

INSERT INTO RAM
VALUES (1, 16, 3200, 2),
       (2, 32, 3200, 2);

INSERT INTO STORAGE
VALUES (3, 2000, 'SSD', 'M.2 PCIe 4.0 X4', 'M.2-2280'),
       (4, 500, 'SSD', 'M.2 PCIe 4.0 X4', 'M.2-2280');

INSERT INTO PROCESSOR
VALUES (5, 6, 3.6, 4.2),
       (6, 20, 3.4, 5.6);

INSERT INTO GRAPHICS_CARD
VALUES (7, 'GeForce RTX 3060', 12, 235),
       (8, 'Radeon RX 6600', 8, 269);

INSERT INTO POWER_SUPPLY
VALUES (9, 1250, 80, true),
       (10, 650, 80, false);

INSERT INTO CONTAINS_MB
VALUES (1, 1),
       (2, 2);

INSERT INTO CONTAINS_PART
VALUES (1, 2, 1),
       (1, 3, 2),
       (1, 5, 1),
       (1, 8, 1),
       (1, 9, 1),
       (2, 1, 1),
       (2, 4, 1),
       (2, 6, 1),
       (2, 7, 1),
       (2, 10, 1);

INSERT INTO MB_COMPATIBLE
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (1, 6),
       (1, 8),
       (1, 9),
       (2, 1),
       (2, 2),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 10);


INSERT INTO PSU_COMPATIBLE
VALUES (7, 9),
       (7, 10),
       (8 ,9);
