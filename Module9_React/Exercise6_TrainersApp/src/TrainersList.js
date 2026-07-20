import React from 'react';
import { Link } from 'react-router-dom';
import TrainersMock from './TrainersMock';

function TrainersList() {
  return (
    <div>
      <h2>Trainers List</h2>
      <ul>
        {TrainersMock.map(trainer => (
          <li key={trainer.trainerId}>
            <Link to={`/trainer/${trainer.trainerId}`}>{trainer.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TrainersList;
