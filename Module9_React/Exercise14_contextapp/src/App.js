import React, { Component } from 'react';
import ThemeContext from './ThemeContext';
import EmployeesList from './EmployeesList';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      theme: 'light',
      employees: [
        { name: 'Alice Johnson', designation: 'Software Engineer' },
        { name: 'Bob Smith', designation: 'Team Lead' },
        { name: 'Carol Williams', designation: 'QA Analyst' }
      ]
    };
  }

  toggleTheme = () => {
    this.setState(prev => ({
      theme: prev.theme === 'light' ? 'dark' : 'light'
    }));
  }

  render() {
    return (
      <ThemeContext.Provider value={this.state.theme}>
        <div style={{
          padding: '20px',
          backgroundColor: this.state.theme === 'dark' ? '#222' : '#fff',
          color: this.state.theme === 'dark' ? '#fff' : '#000',
          minHeight: '100vh'
        }}>
          <h1>Employee Management</h1>
          <button onClick={this.toggleTheme} style={{ padding: '8px 16px', marginBottom: '20px' }}>
            Toggle Theme (Current: {this.state.theme})
          </button>
          <EmployeesList employees={this.state.employees} />
        </div>
      </ThemeContext.Provider>
    );
  }
}

export default App;
