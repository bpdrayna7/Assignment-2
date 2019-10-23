USE bdrayna;

DROP TABLE IF EXISTS InvoiceProduct;
DROP TABLE IF EXISTS Invoice;
DROP TABLE IF EXISTS Amenity;
DROP TABLE IF EXISTS ParkingPass;
DROP TABLE IF EXISTS SaleAgreement;
DROP TABLE IF EXISTS LeaseAgreement;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Email;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Address;

CREATE TABLE Address (
	addressId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(50) NOT NULL,
    city VARCHAR(30) NOT NULL,
    state VARCHAR(20) NOT NULL,
    zipcode VARCHAR(10) NOT NULL,
    country VARCHAR(20) NOT NULL
);

CREATE TABLE Person (
	personId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    personCode VARCHAR(10) NOT NULL,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    addressId INT NOT NULL,
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);

CREATE TABLE Email (
	emailId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    personId INT NOT NULL,
    emailAddress VARCHAR(50) NOT NULL,
    FOREIGN KEY (personId) REFERENCES Person(personId)
);

CREATE TABLE Customer (
	customerId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customerCode VARCHAR(10) NOT NULL,
    type VARCHAR(15) NOT NULL,
    personId INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    addressId INT NOT NULL,
    FOREIGN KEY (personId) REFERENCES Person(personId),
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);

CREATE TABLE Product (
	productId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    productCode VARCHAR(10) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE TABLE LeaseAgreement (
	leaseAgreementId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    productId INT NOT NULL,
    startDate VARCHAR(15) NOT NULL,
    endDate VARCHAR(15) NOT NULL,
    addressId INT NOT NULL,
    customerName VARCHAR(30) NOT NULL,
    deposit DOUBLE NOT NULL,
    monthlyCost DOUBLE NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product(productId),
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);

CREATE TABLE SaleAgreement (
	saleAgreementId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    productId INT NOT NULL,
    dateTime VARCHAR(25) NOT NULL,
    addressId INT NOT NULL,
    totalCost DOUBLE NOT NULL,
    downPayment DOUBLE NOT NULL,
    monthlyPayment DOUBLE NOT NULL,
    payableMonths INT NOT NULL,
    interestRate DOUBLE NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product(productId),
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);

CREATE TABLE ParkingPass (
	parkingPassId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    productId INT NOT NULL,
    parkingFee DOUBLE NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE Amenity (
	amenityId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    productId INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    cost DOUBLE NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE Invoice (
	invoiceId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customerId INT NOT NULL,
    personId INT NOT NULL,
	invoiceDate VARCHAR(15) NOT NULL,
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    FOREIGN KEY (personId) REFERENCES Person(personId)
);

CREATE TABLE InvoiceProduct (
	invoiceProductId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    invoiceId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    agreementId INT,
	FOREIGN KEY (invoiceId) REFERENCES Invoice(invoiceId),
    FOREIGN KEY (productId) REFERENCES Product(productId),
    FOREIGN KEY (agreementId) REFERENCES Product(productId)
);

#Insert Addresses of Persons
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1440 North Viking Street","St. Paul","MN","55129","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1560 Oscar Blvd.","Austin","TX","15600","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("7100 Benjamin Street","Chicago","IL","14789","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("9909 Hunter Place","Dallas","TX","67676","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("144 Frontier Road","Phoenix","AZ","88700","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("9090 Maple Street","Toronto","ON","K12 9G9","Canada");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("3566 O Street","Licoln","NE","12345","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("7789 1st Ave.","Minneapolis","MN","12708","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1200 Grand Ave.","Minneapolis","MN","55125","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1460 2nd Street","St. Paul","MN","55129","USA");

#Insert Persons
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("540s","Bud","Grant",(SELECT addressId FROM Address WHERE street="1440 North Viking Street"));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("660g","John","Smith",(SELECT addressId FROM Address WHERE street="1560 Oscar Blvd."));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("a100","Michael","Jones",(SELECT addressId FROM Address WHERE street="7100 Benjamin Street"));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("77s7","Gavin","Free",(SELECT addressId FROM Address WHERE street="9909 Hunter Place"));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("123s","Peter","Crouch",(SELECT addressId FROM Address WHERE street="144 Frontier Road"));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("2yyu","Jack","Fox",(SELECT addressId FROM Address WHERE street="9090 Maple Street"));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("8890","Pablo","Campos",(SELECT addressId FROM Address WHERE street="3566 O Street"));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("540h","Christian","Ramirez",(SELECT addressId FROM Address WHERE street="7789 1st Ave."));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("590I2","Karl-Anthony","Towns",(SELECT addressId FROM Address WHERE street="1200 Grand Ave."));
INSERT INTO Person (personCode,firstName,lastName,addressId) VALUES ("67oo","Karl-Anthony","Towns Sr.",(SELECT addressId FROM Address WHERE street="1460 2nd Street"));

#Insert Emails
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="540s"),"bgrant@vikings.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="a100"),"mjones@gmail.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="a100"),"michaelj@msn.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="77s7"),"free@hotmail.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="123s"),"crouchP@gmail.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="8890"),"campos@united.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="540h"),"ramirez@united.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="540h"),"cramirez@gmail.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="540h"),"christian@aol.com");
INSERT INTO Email (personId,emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode="67oo"),"townssr@gmail.com");

#Insert Addresses of Customers
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1224 New Street","Woodbury","MN","55129","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("6767 Park Place","New York","NY","33770","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1240 21st Street","Flagstaff","AZ","56678","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("5400 West Broadway Way","Denver","CO","77822","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("7777 Innovation Place","Seattle","WA","92920","USA");

#Insert Customers
INSERT INTO Customer (customerCode,type,personId,name,addressId) VALUES ("C001","G",(SELECT personId FROM Person WHERE personCode="a100"),"Jones Industries",(SELECT addressId FROM Address WHERE street="1224 New Street"));
INSERT INTO Customer (customerCode,type,personId,name,addressId) VALUES ("C002","L",(SELECT personId FROM Person WHERE personCode="540h"),"Jackson Development",(SELECT addressId FROM Address WHERE street="6767 Park Place"));
INSERT INTO Customer (customerCode,type,personId,name,addressId) VALUES ("C003","G",(SELECT personId FROM Person WHERE personCode="67oo"),"Tennis Tools",(SELECT addressId FROM Address WHERE street="1240 21st Street"));
INSERT INTO Customer (customerCode,type,personId,name,addressId) VALUES ("C004","G",(SELECT personId FROM Person WHERE personCode="2yyu"),"Stonebridge",(SELECT addressId FROM Address WHERE street="5400 West Broadway Way"));
INSERT INTO Customer (customerCode,type,personId,name,addressId) VALUES ("C005","L",(SELECT personId FROM Person WHERE personCode="77s7"),"Microsoft",(SELECT addressId FROM Address WHERE street="7777 Innovation Place"));

#Insert Products
INSERT INTO Product (productCode,type) VALUES ("tt8y","L");
INSERT INTO Product (productCode,type) VALUES ("77gg","S");
INSERT INTO Product (productCode,type) VALUES ("88yy","P");
INSERT INTO Product (productCode,type) VALUES ("t6t9","P");
INSERT INTO Product (productCode,type) VALUES ("909j","A");
INSERT INTO Product (productCode,type) VALUES ("77ff","S");
INSERT INTO Product (productCode,type) VALUES ("70oo","L");
INSERT INTO Product (productCode,type) VALUES ("8jjj","A");
INSERT INTO Product (productCode,type) VALUES ("989i","A");
INSERT INTO Product (productCode,type) VALUES ("000a","P");

#Insert Addresses of LeaseAgreements
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("122 S Street","Lincoln","NE","66009","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("1600 Fun Place","Portland","OR","88125","USA");

#Insert LeaseAgreements
INSERT INTO LeaseAgreement (productId,startDate,endDate,addressId,customerName,deposit,monthlyCost) VALUES ((SELECT productId FROM Product WHERE productCode="tt8y"),"2018-02-02","2019-02-02",(SELECT addressId FROM Address WHERE street="122 S Street"),"Tennis Tools",500.00,375.50);
INSERT INTO LeaseAgreement (productId,startDate,endDate,addressId,customerName,deposit,monthlyCost) VALUES ((SELECT productId FROM Product WHERE productCode="70oo"),"2016-01-25","2019-07-25",(SELECT addressId FROM Address WHERE street="1600 Fun Place"),"Microsoft",1000.00,700.00);

#Insert Addresses of SaleAgreements
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("6655 Raven Drive","Anoka","MN","88900","USA");
INSERT INTO Address (street,city,state,zipcode,country) VALUES ("4401 East Ave.","Edina","MN","77012","USA");

#Insert SaleAgreements
INSERT INTO SaleAgreement (productId,dateTime,addressId,totalCost,downPayment,monthlyPayment,payableMonths,interestRate) VALUES ((SELECT productId FROM Product WHERE productCode="77gg"),"2015-03-08 18:30",(SELECT addressId FROM Address WHERE street="6655 Raven Drive"),77500.00,20000.00,5000.00,12,5.05);
INSERT INTO SaleAgreement (productId,dateTime,addressId,totalCost,downPayment,monthlyPayment,payableMonths,interestRate) VALUES ((SELECT productId FROM Product WHERE productCode="77ff"),"2014-12-12 12:00",(SELECT addressId FROM Address WHERE street="4401 East Ave."),100000.00,15000.00,1250.00,36,3.00);

#Insert ParkingPasses
INSERT INTO ParkingPass (productId,parkingFee) VALUES ((SELECT productId FROM Product WHERE productCode="88yy"),35.00);
INSERT INTO ParkingPass (productId,parkingFee) VALUES ((SELECT productId FROM Product WHERE productCode="t6t9"),57.00);
INSERT INTO ParkingPass (productId,parkingFee) VALUES ((SELECT productId FROM Product WHERE productCode="000a"),40.50);

#Insert Amenities
INSERT INTO Amenity (productId,name,cost) VALUES ((SELECT productId FROM Product WHERE productCode="909j"),"Bed Frame",50.00);
INSERT INTO Amenity (productId,name,cost) VALUES ((SELECT productId FROM Product WHERE productCode="8jjj"),"Desk",15.00);
INSERT INTO Amenity (productId,name,cost) VALUES ((SELECT productId FROM Product WHERE productCode="989i"),"Modem",10.99);

#Insert Invoices
INSERT INTO Invoice (customerId,personId,invoiceDate) VALUES ((SELECT customerId FROM Customer WHERE customerCode="C001"),(SELECT personId FROM Person WHERE personCode="77s7"),"2018-03-03");
INSERT INTO Invoice (customerId,personId,invoiceDate) VALUES ((SELECT customerId FROM Customer WHERE customerCode="C002"),(SELECT personId FROM Person WHERE personCode="123s"),"2015-12-13");
INSERT INTO Invoice (customerId,personId,invoiceDate) VALUES ((SELECT customerId FROM Customer WHERE customerCode="C003"),(SELECT personId FROM Person WHERE personCode="2yyu"),"2019-01-01");
INSERT INTO Invoice (customerId,personId,invoiceDate) VALUES ((SELECT customerId FROM Customer WHERE customerCode="C004"),(SELECT personId FROM Person WHERE personCode="540h"),"2016-05-05");
INSERT INTO Invoice (customerId,personId,invoiceDate) VALUES ((SELECT customerId FROM Customer WHERE customerCode="C005"),(SELECT personId FROM Person WHERE personCode="8890"),"2019-09-09");

#Insert InvoiceProducts
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2018-03-03"),(SELECT productId FROM Product WHERE productCode="tt8y"),12,null);
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2018-03-03"),(SELECT productId FROM Product WHERE productCode="88yy"),12,(SELECT productId FROM Product WHERE productCode="tt8y"));
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2018-03-03"),(SELECT productId FROM Product WHERE productCode="989i"),3,null);
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2015-12-13"),(SELECT productId FROM Product WHERE productCode="77ff"),1,null);
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2015-12-13"),(SELECT productId FROM Product WHERE productCode="000a"),1,(SELECT productId FROM Product WHERE productCode="77ff"));
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2019-01-01"),(SELECT productId FROM Product WHERE productCode="t6t9"),4,null);
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2016-05-05"),(SELECT productId FROM Product WHERE productCode="70oo"),6,null);
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2016-05-05"),(SELECT productId FROM Product WHERE productCode="8jjj"),10,null);
INSERT INTO InvoiceProduct (invoiceId,productId,quantity,agreementId) VALUES ((SELECT invoiceId FROM Invoice WHERE invoiceDate="2019-09-09"),(SELECT productId FROM Product WHERE productCode="909j"),10,null);
