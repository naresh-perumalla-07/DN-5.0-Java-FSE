import React from 'react';

function ListofPlayers() {
  const players = [
    { name: 'Virat Kohli', score: 82 },
    { name: 'Rohit Sharma', score: 75 },
    { name: 'KL Rahul', score: 60 },
    { name: 'Shikhar Dhawan', score: 55 },
    { name: 'Rishabh Pant', score: 90 },
    { name: 'Hardik Pandya', score: 45 },
    { name: 'Ravindra Jadeja', score: 68 },
    { name: 'Jasprit Bumrah', score: 30 },
    { name: 'Mohammed Shami', score: 25 },
    { name: 'Yuzvendra Chahal', score: 40 },
    { name: 'Shreyas Iyer', score: 72 }
  ];

  const playerList = players.map((player, index) => (
    <li key={index}>{player.name} - Score: {player.score}</li>
  ));

  const belowSeventy = players.filter(player => player.score < 70);

  return (
    <div>
      <h2>All Players</h2>
      <ul>{playerList}</ul>
      <h2>Players with Score Below 70</h2>
      <ul>
        {belowSeventy.map((player, index) => (
          <li key={index}>{player.name} - Score: {player.score}</li>
        ))}
      </ul>
    </div>
  );
}

export default ListofPlayers;
