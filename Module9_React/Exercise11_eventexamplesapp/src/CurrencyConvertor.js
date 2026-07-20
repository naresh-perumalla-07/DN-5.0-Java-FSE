import React, { Component } from 'react';

class CurrencyConvertor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rupees: '',
      euro: ''
    };
  }

  handleChange = (e) => {
    this.setState({ rupees: e.target.value });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const euroValue = (parseFloat(this.state.rupees) / 89.5).toFixed(2);
    this.setState({ euro: euroValue });
  }

  render() {
    return (
      <div style={{ padding: '20px', border: '1px solid #ccc', margin: '20px' }}>
        <h2>Currency Convertor (INR to EUR)</h2>
        <form onSubmit={this.handleSubmit}>
          <label>Indian Rupees: </label>
          <input type="number" value={this.state.rupees} onChange={this.handleChange} />
          <button type="submit" style={{ marginLeft: '10px' }}>Convert</button>
        </form>
        {this.state.euro && <h3>Euro: €{this.state.euro}</h3>}
      </div>
    );
  }
}

export default CurrencyConvertor;
