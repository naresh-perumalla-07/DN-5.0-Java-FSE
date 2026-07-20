import React, { useContext } from 'react';
import ThemeContext from './ThemeContext';

function EmployeeCard({ employee }) {
  const theme = useContext(ThemeContext);
  const btnClass = theme === 'dark' ? 'btn-dark' : 'btn-light';

  return (
    <div style={{ border: '1px solid #ccc', padding: '10px', margin: '10px', display: 'inline-block', width: '200px' }}>
      <h3>{employee.name}</h3>
      <p>{employee.designation}</p>
      <button className={btnClass} style={{
        padding: '5px 15px',
        backgroundColor: theme === 'dark' ? '#333' : '#eee',
        color: theme === 'dark' ? '#fff' : '#333',
        border: 'none',
        cursor: 'pointer'
      }}>
        View Details
      </button>
    </div>
  );
}

export default EmployeeCard;
