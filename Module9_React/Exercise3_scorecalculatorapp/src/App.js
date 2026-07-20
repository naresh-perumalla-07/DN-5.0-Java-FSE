import React from 'react';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div>
      <CalculateScore name="John" school="ABC School" total={450} goal={500} />
      <CalculateScore name="Jane" school="XYZ Academy" total={380} goal={500} />
    </div>
  );
}

export default App;
