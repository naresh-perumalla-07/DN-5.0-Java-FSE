import React, { Component } from 'react';

class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      email: '',
      password: '',
      errors: {}
    };
  }

  validate = () => {
    const errors = {};
    const { name, email, password } = this.state;

    if (name.length < 5) {
      errors.name = 'Name should have at least 5 characters';
    }
    if (!email.includes('@') || !email.includes('.')) {
      errors.email = 'Email should contain @ and .';
    }
    if (password.length < 8) {
      errors.password = 'Password should have at least 8 characters';
    }

    return errors;
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const errors = this.validate();
    this.setState({ errors });

    if (Object.keys(errors).length === 0) {
      alert('Registration Successful!');
      this.setState({ name: '', email: '', password: '', errors: {} });
    }
  }

  render() {
    const { name, email, password, errors } = this.state;
    const errorStyle = { color: 'red', fontSize: '12px' };

    return (
      <div style={{ padding: '20px', maxWidth: '400px', margin: 'auto' }}>
        <h1>Mail Registration</h1>
        <form onSubmit={this.handleSubmit}>
          <div style={{ marginBottom: '15px' }}>
            <label>Name:</label><br />
            <input type="text" name="name" value={name} onChange={this.handleChange} style={{ width: '100%', padding: '8px' }} />
            {errors.name && <span style={errorStyle}>{errors.name}</span>}
          </div>

          <div style={{ marginBottom: '15px' }}>
            <label>Email:</label><br />
            <input type="text" name="email" value={email} onChange={this.handleChange} style={{ width: '100%', padding: '8px' }} />
            {errors.email && <span style={errorStyle}>{errors.email}</span>}
          </div>

          <div style={{ marginBottom: '15px' }}>
            <label>Password:</label><br />
            <input type="password" name="password" value={password} onChange={this.handleChange} style={{ width: '100%', padding: '8px' }} />
            {errors.password && <span style={errorStyle}>{errors.password}</span>}
          </div>

          <button type="submit" style={{ padding: '10px 20px' }}>Register</button>
        </form>
      </div>
    );
  }
}

export default Register;
