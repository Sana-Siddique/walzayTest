import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from './Orders.css';

const Orders = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await axios.get('http://localhost:3021/order', {
          params: {
            current: 1,
            rowCount: 100,
          },
          headers: {
            Accept: 'application/json'
          },
        });
        console.log('API Response:', response);
        const data = response.data;
        setOrders(data.orders);
      } catch (error) {
        console.error('Error fetching orders:', error);
      }
    };

    fetchOrders();
  }, []);

 return (
    <div>
      <h1>Orders</h1>
      <table className={styles.table}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Customer Name</th>
            <th>Reference No</th>
            <th>Line Items</th>
          </tr>
        </thead>
        <tbody>
          {orders.map(order => (
            <tr key={order.id}>
              <td>{order.id}</td>
              <td>{order.customerName}</td>
              <td>{order.referenceNo}</td>
              <td>
                <ul className={styles.list}>
                  {order.lineItems.map(item => (
                    <li key={item.lineNumber} className={styles.listItem}>
                      <p>Item ID: {item.cardItemId}</p>
                      <p>Item Name: {item.cardItemName}</p>
                      <p>Value: {item.value} {item.currency}</p>
                      {}
                    </li>
                  ))}
                </ul>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Orders;
