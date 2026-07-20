import React from 'react';

function IndianPlayers() {
  const allPlayers = ['Virat', 'Rohit', 'KL Rahul', 'Pant', 'Hardik', 'Jadeja', 'Bumrah', 'Shami', 'Chahal', 'Iyer', 'Dhawan'];

  const [first, second, ...rest] = allPlayers;
  const oddTeam = allPlayers.filter((_, i) => i % 2 === 0);
  const evenTeam = allPlayers.filter((_, i) => i % 2 !== 0);

  const t20Players = ['Virat', 'Rohit', 'Pant', 'Hardik', 'Bumrah'];
  const ranjiPlayers = ['Prithvi Shaw', 'Sarfaraz Khan', 'Devdutt Padikkal'];
  const mergedPlayers = [...t20Players, ...ranjiPlayers];

  return (
    <div>
      <h2>Destructuring</h2>
      <p>First: {first}, Second: {second}</p>
      <p>Rest: {rest.join(', ')}</p>

      <h2>Odd Position Team</h2>
      <ul>{oddTeam.map((p, i) => <li key={i}>{p}</li>)}</ul>

      <h2>Even Position Team</h2>
      <ul>{evenTeam.map((p, i) => <li key={i}>{p}</li>)}</ul>

      <h2>Merged T20 + Ranji Players</h2>
      <ul>{mergedPlayers.map((p, i) => <li key={i}>{p}</li>)}</ul>
    </div>
  );
}

export default IndianPlayers;
