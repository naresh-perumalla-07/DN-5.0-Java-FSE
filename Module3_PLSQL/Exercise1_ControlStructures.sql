-- ============================================================
-- Module 3: PL/SQL Programming
-- Exercise 1: Control Structures
-- ============================================================

-- First, create the required schema
CREATE TABLE Customers (
    CustomerID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance NUMBER,
    LastModified DATE
);

CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    LoanAmount NUMBER,
    InterestRate NUMBER,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
);

-- Insert sample data
INSERT INTO Customers VALUES (1, 'John Doe', TO_DATE('1960-05-15', 'YYYY-MM-DD'), 15000, SYSDATE);
INSERT INTO Customers VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 8000, SYSDATE);
INSERT INTO Customers VALUES (3, 'Robert Brown', TO_DATE('1955-03-10', 'YYYY-MM-DD'), 12000, SYSDATE);
INSERT INTO Customers VALUES (4, 'Emily Davis', TO_DATE('1965-11-22', 'YYYY-MM-DD'), 5000, SYSDATE);

INSERT INTO Loans VALUES (1, 1, 50000, 7.5, SYSDATE - 365, SYSDATE + 20);
INSERT INTO Loans VALUES (2, 2, 30000, 6.0, SYSDATE - 180, SYSDATE + 90);
INSERT INTO Loans VALUES (3, 3, 40000, 8.0, SYSDATE - 100, SYSDATE + 15);

COMMIT;


-- ============================================================
-- Scenario 1: Apply 1% discount to loan interest rates 
--             for customers above 60 years old
-- ============================================================
DECLARE
    v_age NUMBER;
BEGIN
    FOR rec IN (SELECT c.CustomerID, c.Name, c.DOB, l.LoanID, l.InterestRate
                FROM Customers c
                JOIN Loans l ON c.CustomerID = l.CustomerID)
    LOOP
        -- Calculate age
        v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, rec.DOB) / 12);

        IF v_age > 60 THEN
            -- Apply 1% discount to interest rate
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE LoanID = rec.LoanID;

            DBMS_OUTPUT.PUT_LINE('Discount applied for ' || rec.Name ||
                ' (Age: ' || v_age || '). New Rate: ' || (rec.InterestRate - 1) || '%');
        ELSE
            DBMS_OUTPUT.PUT_LINE('No discount for ' || rec.Name ||
                ' (Age: ' || v_age || ')');
        END IF;
    END LOOP;
    COMMIT;
END;
/


-- ============================================================
-- Scenario 2: Set IsVIP flag for customers with balance > $10,000
-- ============================================================

-- First add the IsVIP column
ALTER TABLE Customers ADD (IsVIP VARCHAR2(5) DEFAULT 'FALSE');

DECLARE
BEGIN
    FOR rec IN (SELECT CustomerID, Name, Balance FROM Customers)
    LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE(rec.Name || ' promoted to VIP (Balance: $' || rec.Balance || ')');
        ELSE
            DBMS_OUTPUT.PUT_LINE(rec.Name || ' not eligible for VIP (Balance: $' || rec.Balance || ')');
        END IF;
    END LOOP;
    COMMIT;
END;
/


-- ============================================================
-- Scenario 3: Send reminders for loans due within next 30 days
-- ============================================================
DECLARE
    CURSOR loan_cursor IS
        SELECT c.Name, c.CustomerID, l.LoanID, l.EndDate, l.LoanAmount
        FROM Customers c
        JOIN Loans l ON c.CustomerID = l.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;
BEGIN
    FOR rec IN loan_cursor
    LOOP
        DBMS_OUTPUT.PUT_LINE('REMINDER: Dear ' || rec.Name ||
            ', your Loan #' || rec.LoanID ||
            ' (Amount: $' || rec.LoanAmount ||
            ') is due on ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY') ||
            '. Please ensure timely repayment.');
    END LOOP;
END;
/
