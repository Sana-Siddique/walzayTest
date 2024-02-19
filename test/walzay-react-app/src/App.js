import React, { useState } from 'react';
import './App.css';
import Orders from './services/Order';
import OrderSearchForm from './services/OrderSearchForm';

const App = () => {
  const [searchParams, setSearchParams] = useState({});

  const handleSearch = formData => {
    setSearchParams(formData);
  };

  return (
    <div>
      <h1>Giftlov Orders</h1>
      <OrderSearchForm onSubmit={handleSearch} />
      <Orders searchParams={searchParams} />
    </div>
  );
};

export default App;
