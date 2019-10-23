#1 (selects all from Person except personId and addressId and all from address except addressId)
SELECT p.personId, p.personCode, p.firstName, p.lastName, a.street, a.city, a.state, a.zipcode, a.country 
	FROM Person p JOIN Address a ON p.addressId = a.addressId;
    
#2 (on the specific Person "John Smith")
INSERT INTO Email(personId, emailAddress) VALUES (
	(SELECT personId FROM Person WHERE firstName = 'John' AND lastName = 'Smith'), 'jsmith32@hotmail.com');

###3 (


#4 (removes SaleAgreement record of agreement code "77gg")
DELETE FROM SaleAgreement WHERE productId = (SELECT productId FROM Product WHERE productCode = '77gg');

#5 (gets all products from invoice 1)
SELECT * FROM Product WHERE productId IN (SELECT productId FROM InvoiceProduct WHERE invoiceId = 1);

#6 (gets all invoices from customer "Stonebridge")
SELECT * FROM Invoice WHERE customerId = (SELECT customerId FROM Customer WHERE name = 'Stonebridge');

#7 (adds product with code "909j" and quantity 3 to invoice 1)
INSERT INTO InvoiceProduct(invoiceId, productId, quantity, agreementId) VALUES (
	1, (SELECT productId FROM Product WHERE productCode = '909j'), 3, null);

#8 (adds all monthlyCosts and deposits for LeaseAgreements)
SELECT SUM(monthlyCost) + SUM(deposit) AS totalForAllLease FROM LeaseAgreement;

###9 (adds all totalCosts for SaleAgreements)
SELECT SUM(totalCost) FROM SaleAgreement;

#10 (counts number of agreements sold on date "2015-03-08")
SELECT ((SELECT Count(dateTime) FROM SaleAgreement WHERE dateTime = '2015-03-08') 
	+ (SELECT Count(startDate) FROM LeaseAgreement WHERE startDate = '2015-03-08')) AS numSold;
    
#11 (selects realtors name and number of invoices each realtor has)
SELECT p.firstName, p.lastName, Count(i.personId) AS numOfInvoices FROM Invoice i JOIN Person p 
	ON i.personId = p.personId GROUP BY personId;

#12 (counts number of invoices for SaleAgreement 77gg)
SELECT Count(*) AS numInvoices FROM InvoiceProduct WHERE agreementId = 
	(SELECT saleAgreementId FROM SaleAgreement WHERE productId = 
    (SELECT productId FROM Product WHERE productCode = '77gg'));

###13 (finds total revenue of all agreements from date "2015-03-08"; COALESCE prevents null values from disrupting SUM)
SELECT (SELECT COALESCE(SUM(totalCost), 0) FROM SaleAgreement WHERE dateTime = '2015-03-08') +
	(SELECT COALESCE(SUM(monthlyCost) + SUM(deposit), 0) FROM LeaseAgreement WHERE startDate = '2015-03-08') AS totalRevenue;

#14
SELECT (SELECT SUM(quantity) FROM InvoiceProduct i JOIN Product p 
	ON i.productId = p.productId AND p.type = 'P') AS parkingPassUnitsSold,
	(SELECT SUM(quantity) FROM InvoiceProduct i JOIN Product p
    ON i.productId = p.productId AND p.type = 'A') AS amenityUnitsSold;

#15 (selects invoiceIds with multiple of same agreement and returns number of same agreement)
SELECT invoiceId, Count(agreementId) AS numOfSameAgreement FROM InvoiceProduct 
	GROUP BY agreementId HAVING Count(agreementId) > 1;

#16
SELECT * FROM Invoice i WHERE personId = (SELECT personId FROM Customer WHERE customerId = i.customerId);