import React, { useState } from 'react';
import axios from 'axios';
import {Link, useNavigate } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert  from 'react-bootstrap/Alert';


const BrandCreate = () => {
    const [formData, setFormData] = useState({
        brandName: '',
        brandDesc: '',

    });

    const [formErrors, setFormErrors] = useState('');
    const [submitted, setSubmitted] = useState(false);

    const navigate = useNavigate();

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
    
            axios.post('/api/brand/create', formData)
                .then(response => {
                    console.log(response);
                    navigate('/admin/brand')
                })
                .catch(error => setFormErrors(error));
        } else {
          setFormErrors('All fields are required.');
        }
    };
    
    return (
        <Form onSubmit={handleSubmit}>
            {formErrors && <Alert variant="danger">{formErrors}</Alert>}
            {submitted && <Alert variant="success">Form submitted successfully!</Alert>}
          <Form.Group className="mb-5">
            <Form.Label>브랜드 이름</Form.Label>
            <Form.Control type="text"  name="brandName" onChange={handleChange}/>
          </Form.Group>
          <Form.Group className="mb-5">
            <Form.Label>브랜드 설명</Form.Label>
            <Form.Control type="text" name="brandDesc" onChange={handleChange}/>
          </Form.Group>
          <Button variant="primary" type="submit">
            등록
          </Button>
          <Link to='/admin/brand'><Button variant="info">목록</Button></Link>
        </Form>
      );
}

export default BrandCreate;