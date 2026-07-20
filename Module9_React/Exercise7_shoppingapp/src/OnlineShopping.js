import React, { Component } from 'react';

class Cart {
  constructor(itemName, price) {
    this.itemName = itemName;
    this.price = price;
  }
}

class OnlineShopping extends Component {
  constructor(props) {
    super(props);
    this.items = [
      new Cart('Laptop', 75000),
      new Cart('Mobile Phone', 25000),
      new Cart('Headphones', 2500),
      new Cart('Keyboard', 1500),
      new Cart('Mouse', 800)
    ];
  }

  render() {
    return (
      <div>
        <h1>Online Shopping</h1>
        <table border="1" cellPadding="10">
          <thead>
            <tr>
              <th>Item Name</th>
              <th>Price (₹)</th>
            </tr>
          </thead>
          <tbody>
            {this.items.map((item, index) => (
              <tr key={index}>
                <td>{item.itemName}</td>
                <td>{item.price}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default OnlineShopping;
