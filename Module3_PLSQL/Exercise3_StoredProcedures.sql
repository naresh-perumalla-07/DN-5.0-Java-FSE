-- ============================================================
-- Module 3: PL/SQL Programming
-- Exercise 3: Stored Procedures
-- ============================================================

-- ============================================================
-- Scenario 1: Process Monthly Interest for all Savings Accounts
-- Applies 1% interest rate to all savings accounts
-- ============================================================
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
BEGIN
    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01),
        LastModified = SYSDATE
    WHERE AccountType = 'Savings';

    DBMS_OUTPUT.PUT_LINE('Monthly interest applied to ' || SQL%ROWCOUNT || ' savings accounts.');
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error processing interest: ' || SQLERRM);
        ROLLBACK;
END ProcessMonthlyInterest;
/

-- Test: EXEC ProcessMonthlyInterest;


-- ============================================================
-- Scenario 2: Update Employee Bonus by Department
-- Adds a bonus percentage to salaries in a given department
-- ============================================================
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department IN VARCHAR2,
    p_bonus_pct  IN NUMBER
) IS
    v_count NUMBER := 0;
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * p_bonus_pct / 100),
        HireDate = HireDate  -- Keep original hire date
    WHERE Department = p_department;

    v_count := SQL%ROWCOUNT;

    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Bonus of ' || p_bonus_pct || '% applied to ' ||
            v_count || ' employees in ' || p_department || ' department.');
    END IF;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error updating bonus: ' || SQLERRM);
        ROLLBACK;
END UpdateEmployeeBonus;
/

-- Test: EXEC UpdateEmployeeBonus('IT', 10);


-- ============================================================
-- Scenario 3: Transfer Funds between Accounts
-- Checks sufficient balance before transferring
-- ============================================================
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) IS
    v_from_balance NUMBER;
    insufficient_funds EXCEPTION;
BEGIN
    -- Check source account balance
    SELECT Balance INTO v_from_balance
    FROM Accounts
    WHERE AccountID = p_from_account;

    -- Verify sufficient funds
    IF v_from_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;

    -- Debit from source account
    UPDATE Accounts
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_from_account;

    -- Credit to destination account
    UPDATE Accounts
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_to_account;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer of $' || p_amount ||
        ' from Account #' || p_from_account ||
        ' to Account #' || p_to_account || ' completed successfully.');

EXCEPTION
    WHEN insufficient_funds THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Insufficient funds in Account #' ||
            p_from_account || '. Available: $' || v_from_balance ||
            ', Requested: $' || p_amount);
        ROLLBACK;
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Account not found.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
        ROLLBACK;
END TransferFunds;
/

-- Test: EXEC TransferFunds(1, 2, 500);
-- Test (insufficient funds): EXEC TransferFunds(1, 2, 999999);


-- Insert sample accounts and employees for testing
INSERT INTO Accounts VALUES (1, 1, 'Savings', 10000, SYSDATE);
INSERT INTO Accounts VALUES (2, 2, 'Checking', 5000, SYSDATE);
INSERT INTO Accounts VALUES (3, 3, 'Savings', 15000, SYSDATE);

INSERT INTO Employees VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));
INSERT INTO Employees VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));
INSERT INTO Employees VALUES (3, 'Carol White', 'Developer', 55000, 'IT', TO_DATE('2019-01-10', 'YYYY-MM-DD'));

COMMIT;
