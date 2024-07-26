import Form from 'react-bootstrap/Form';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BrandSelect = ({ selectedIdx, onSelectChange }) => {

    const [brands, setBrands] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selected, setSelected] = useState(selectedIdx);
  
    useEffect(() => {
      const fetchBrands = async () => {
        try {
          const response = await axios.get('/api/brand/list/select');
          setBrands(response.data.brandSelectList); 
        } catch (err) {
          setError(err.message);
        } finally {
          setLoading(false);
        }
      };
  
      fetchBrands();
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
      <Form.Select aria-label="Default select example" name="brandIdx" value={selected} onChange={handleChange}>
        <option value="">브랜드</option>
        {brands.map((brand) => (
          <option key={brand.brandIdx} value={brand.brandIdx}>
            {brand.brandName}
          </option>
        ))}
      </Form.Select>
    );
}

export default BrandSelect;