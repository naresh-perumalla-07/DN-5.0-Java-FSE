import React from 'react';

function App() {
  const office = {
    name: 'Tech Park Office',
    rent: 55000,
    address: '123, MG Road, Bangalore'
  };

  const officeSpaces = [
    { id: 1, name: 'Prestige Tower', rent: 75000, address: 'Whitefield, Bangalore' },
    { id: 2, name: 'Infinity Park', rent: 45000, address: 'Hinjewadi, Pune' },
    { id: 3, name: 'DLF Cyber City', rent: 90000, address: 'Gurgaon, Delhi NCR' },
    { id: 4, name: 'Olympia Tech Park', rent: 58000, address: 'Guindy, Chennai' }
  ];

  const rentStyle = (rent) => ({
    color: rent < 60000 ? 'red' : 'green',
    fontWeight: 'bold'
  });

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>Office Space Rental</h1>

      <div style={{ border: '1px solid #ccc', padding: '15px', marginBottom: '20px' }}>
        <h2>{office.name}</h2>
        <p>Address: {office.address}</p>
        <p style={rentStyle(office.rent)}>Rent: ₹{office.rent}/month</p>
      </div>

      <h2>Available Office Spaces</h2>
      {officeSpaces.map(space => (
        <div key={space.id} style={{ border: '1px solid #ddd', padding: '10px', margin: '10px 0' }}>
          <h3>{space.name}</h3>
          <p>Address: {space.address}</p>
          <p style={rentStyle(space.rent)}>Rent: ₹{space.rent}/month</p>
        </div>
      ))}
    </div>
  );
}

export default App;
