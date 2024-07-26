import React, { useState } from 'react';
import axios from 'axios';
import {Link, useNavigate } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert  from 'react-bootstrap/Alert';
import BrandSelect from '../../Reusable/BrandSelect'
import CategorySelect from '../../Reusable/CategorySelect';


const ProductCreate = () => {
    const [brandSelectedIdx, setBrandSelectedIdx] = useState();
    const [categorySelectedIdx, setCategorySelectedIdx] = useState();

    const [formData, setFormData] = useState({
      brandIdx: '',
      categoryIdx : '',
      productName: '',
      productPrice : 0,
      productDesc : ''

  });

    const handleBrandSelectChange = (newBrandSelectedIdx) => {
        setFormData({
            ...formData,
            "brandIdx": newBrandSelectedIdx
        });
        setBrandSelectedIdx(newBrandSelectedIdx);
    };

    const handleCategorySelectChange = (selectedCategory) => {
      const { categoryIdx } = selectedCategory;      
        setFormData({
            ...formData,
            "categoryIdx": categoryIdx
        });
        setCategorySelectedIdx(categoryIdx);
    };


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
        console.log(formData)
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

        axios.post('/api/product/create', formData)
            .then(response => {
                console.log(response);
                navigate("/admin/product")
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
      <Form.Group className="mb-3">
        <BrandSelect selectedIdx={brandSelectedIdx} onSelectChange={handleBrandSelectChange} />
      </Form.Group>
      <Form.Group className="mb-3">
        <CategorySelect selectedIdx={categorySelectedIdx} onSelectChange={handleCategorySelectChange} />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>상품명</Form.Label>
        <Form.Control type="text"  name="productName" onChange={handleChange}/>
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>가격</Form.Label>
        <Form.Control type="number"  name="productPrice" onChange={handleChange}/>
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>상품 설명</Form.Label>
        <Form.Control type="text" name="productDesc" onChange={handleChange}/>
      </Form.Group>
      <Button variant="primary" type="submit">
        등록
      </Button>
      <Link to='/admin/product'><Button variant="info">목록</Button></Link>
    </Form>
  );
}

export default ProductCreate;