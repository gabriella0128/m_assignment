import Form from 'react-bootstrap/Form';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CategorySelect = ({ selectedIdx, onSelectChange }) => {
    const [categories, setCategoreis] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selected, setSelected] = useState(selectedIdx);
  
    useEffect(() => {
      const fetchCategories = async () => {
        try {
          const response = await axios.get('/api/category/list/select');
          setCategoreis(response.data.categorySelectList); 
        } catch (err) {
          setError(err.message);
        } finally {
          setLoading(false);
        }
      };
  
      fetchCategories();
    }, []);

    useEffect(() => {
        if (selectedIdx !== undefined) {
            setSelected(selectedIdx);
        }
    }, [selectedIdx]);

    const handleChange = (e) => {
        const newSelectedIdx = e.target.value;
        setSelected(newSelectedIdx);
        if (onSelectChange) {
          onSelectChange(newSelectedIdx);
        }
    };
  
    if (loading) {
      return <div>Loading...</div>;
    }
  
    if (error) {
      return <div>Error: {error}</div>;
    }
  
    return (
      <Form.Select aria-label="Default select example" name="categoryIdx" value={selected} onChange={handleChange}>
        <option value="">카테고리</option>
        {categories.map((category) => (
          <option key={category.categoryIdx} value={category.categoryIdx}>
            {category.categoryName}
          </option>
        ))}
      </Form.Select>
    );
}

export default CategorySelect;