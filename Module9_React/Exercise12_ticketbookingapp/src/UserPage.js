import React from 'react';

function UserPage() {
  return (
    <div>
      <h2>Welcome, User!</h2>
      <p>You can book your tickets below:</p>
      <ul>
        <li>Flight AI-101: Delhi → Mumbai - ₹5,500 <button>Book</button></li>
        <li>Flight AI-202: Bangalore → Chennai - ₹3,200 <button>Book</button></li>
        <li>Flight AI-303: Kolkata → Hyderabad - ₹4,800 <button>Book</button></li>
      </ul>
    </div>
  );
}

export default UserPage;
