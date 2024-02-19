import React, { useState } from 'react';

const OrderSearchForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    current: '',
    rowCount: '',
    cardItemIds: '',
  });

  const handleChange = event => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

const handleSubmit = event => {
  event.preventDefault();
  console.log('Form submitted');
  onSubmit(formData);
};

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Current:
        <input type="text" name="current" value={formData.current} onChange={handleChange} />
      </label>
      <button type="submit">Search</button>
    </form>
  );
};

export default OrderSearchForm;
