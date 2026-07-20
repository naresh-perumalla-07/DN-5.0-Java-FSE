import React, { Component } from 'react';

class EventExamples extends Component {
  constructor(props) {
    super(props);
    this.state = { counter: 0 };
  }

  increment = () => {
    this.setState(prev => ({ counter: prev.counter + 1 }));
  }

  decrement = () => {
    this.setState(prev => ({ counter: prev.counter - 1 }));
  }

  sayHello = () => {
    alert('Hello! The counter was incremented.');
  }

  handleMultiple = () => {
    this.increment();
    this.sayHello();
  }

  sayWelcome = (message) => {
    alert(message);
  }

  onPress = (e) => {
    alert('I was clicked - Synthetic event type: ' + e.type);
  }

  render() {
    return (
      <div style={{ padding: '20px' }}>
        <h1>Event Examples</h1>

        <h2>Counter: {this.state.counter}</h2>
        <button onClick={this.handleMultiple}>Increment</button>
        <button onClick={this.decrement}>Decrement</button>

        <hr />
        <button onClick={() => this.sayWelcome('Welcome to React Events!')}>Say Welcome</button>

        <hr />
        <button onClick={this.onPress}>Synthetic Event</button>
      </div>
    );
  }
}

export default EventExamples;
