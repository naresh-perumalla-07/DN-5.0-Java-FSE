import React, { Component } from 'react';
import GuestPage from './GuestPage';
import UserPage from './UserPage';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { isLoggedIn: false };
  }

  handleLogin = () => {
    this.setState({ isLoggedIn: true });
  }

  handleLogout = () => {
    this.setState({ isLoggedIn: false });
  }

  render() {
    const { isLoggedIn } = this.state;

    return (
      <div style={{ padding: '20px' }}>
        <h1>Ticket Booking App</h1>
        {isLoggedIn ? (
          <div>
            <UserPage />
            <button onClick={this.handleLogout}>Logout</button>
          </div>
        ) : (
          <div>
            <GuestPage />
            <button onClick={this.handleLogin}>Login</button>
          </div>
        )}
      </div>
    );
  }
}

export default App;
