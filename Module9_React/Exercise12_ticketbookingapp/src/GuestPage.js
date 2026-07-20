import React from 'react';

function GuestPage() {
  return (
    <div>
      <h2>Welcome, Guest!</h2>
      <p>Browse available flights below:</p>
      <ul>
        <li>Flight AI-101: Delhi → Mumbai - ₹5,500</li>
        <li>Flight AI-202: Bangalore → Chennai - ₹3,200</li>
        <li>Flight AI-303: Kolkata → Hyderabad - ₹4,800</li>
      </ul>
      <p><em>Please login to book tickets.</em></p>
    </div>
  );
}

export default GuestPage;
