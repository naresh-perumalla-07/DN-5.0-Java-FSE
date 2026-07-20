import React from 'react';
import EmployeeCard from './EmployeeCard';

function EmployeesList({ employees }) {
  return (
    <div>
      <h2>Employees</h2>
      {employees.map((emp, index) => (
        <EmployeeCard key={index} employee={emp} />
      ))}
    </div>
  );
}

export default EmployeesList;
