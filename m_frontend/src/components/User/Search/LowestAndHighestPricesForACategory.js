import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

const LowestAndHighestPricesForACategory = () => {
    const [formData, setFormData] = useState({
        categoryName : ""

    });

    const [formErrors, setFormErrors] = useState('');
    const [submitted, setSubmitted] = useState(false);

    const handleChange = (e) => { 
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value
        });
      };
    
      const validateForm = () => {
        for (let key in formData) {
          if (formData[key] === '') {
            return false;
          }
        }
        return true;
      };
    
    const handleSubmit = (e) => {
        e.preventDefault();
    
        setSubmitted(false);
    
        if (validateForm()) {
            setFormErrors('');
            setSubmitted(true);
    
            axios.get('/api/search/lowestAndHighestPricesForACategory?categoryName=', formData.categoryName)
                .then(response => {
                    console.log(response);
                })
                .catch(error => setFormErrors(error));
        } else {
          setFormErrors('All fields are required.');
        }
    };
    
    return (
        <div>
            <div>
            <Form onSubmit={handleSubmit}>
                {formErrors && <Alert variant="danger">{formErrors}</Alert>}
                {submitted && <Alert variant="success">Form submitted successfully!</Alert>}
            <Form.Group className="mb-5">
                <Form.Label>카테고리명</Form.Label>
                <Form.Control type="text"  name="categoryName" onChange={handleChange}/>
            </Form.Group>
            <Button variant="primary" type="submit">
                검색
            </Button>
            </Form>
            </div>
        </div>
      );
}

export default LowestAndHighestPricesForACategory;