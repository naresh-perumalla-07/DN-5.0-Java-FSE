import React, { Component } from 'react';

class CountPeople extends Component {
  constructor(props) {
    super(props);
    this.state = {
      entryCount: 0,
      exitCount: 0
    };
  }

  updateEntry = () => {
    this.setState(prev => ({ entryCount: prev.entryCount + 1 }));
  }

  updateExit = () => {
    this.setState(prev => ({ exitCount: prev.exitCount + 1 }));
  }

  render() {
    return (
      <div style={{ textAlign: 'center', marginTop: '50px' }}>
        <h1>Mall People Counter</h1>
        <h2>People Entered: {this.state.entryCount}</h2>
        <h2>People Exited: {this.state.exitCount}</h2>
        <button onClick={this.updateEntry} style={{ margin: '10px', padding: '10px 20px' }}>Login</button>
        <button onClick={this.updateExit} style={{ margin: '10px', padding: '10px 20px' }}>Exit</button>
      </div>
    );
  }
}

export default CountPeople;
