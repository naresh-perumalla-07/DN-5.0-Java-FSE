import React from 'react';
import CohortDetails from './CohortDetails';
import CohortData from './Cohort';

function App() {
  return (
    <div style={{ padding: '20px' }}>
      <h1>Cohort Dashboard</h1>
      <div>
        {CohortData.map((cohort, index) => (
          <CohortDetails key={index} cohort={cohort} />
        ))}
      </div>
    </div>
  );
}

export default App;
