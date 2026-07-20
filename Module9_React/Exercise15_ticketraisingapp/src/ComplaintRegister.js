import React, { Component } from 'react';

class ComplaintRegister extends Component {
  constructor(props) {
    super(props);
    this.state = {
      employeeName: '',
      complaint: ''
    };
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const refNumber = 'REF-' + Math.floor(Math.random() * 100000);
    alert(`Complaint registered successfully!\nEmployee: ${this.state.employeeName}\nReference Number: ${refNumber}`);
    this.setState({ employeeName: '', complaint: '' });
  }

  render() {
    return (
      <div style={{ padding: '20px', maxWidth: '500px', margin: 'auto' }}>
        <h1>Raise a Complaint</h1>
        <form onSubmit={this.handleSubmit}>
          <div style={{ marginBottom: '15px' }}>
            <label>Employee Name:</label><br />
            <input
              type="text"
              name="employeeName"
              value={this.state.employeeName}
              onChange={this.handleChange}
              style={{ width: '100%', padding: '8px' }}
            />
          </div>
          <div style={{ marginBottom: '15px' }}>
            <label>Complaint:</label><br />
            <textarea
              name="complaint"
              value={this.state.complaint}
              onChange={this.handleChange}
              rows="5"
              style={{ width: '100%', padding: '8px' }}
            />
          </div>
          <button type="submit" style={{ padding: '10px 20px' }}>Submit Complaint</button>
        </form>
      </div>
    );
  }
}

export default ComplaintRegister;
