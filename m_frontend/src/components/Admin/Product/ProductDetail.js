import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert  from 'react-bootstrap/Alert';
import BrandSelect from './reusable/BrandSelect';
import CategorySelect from './reusable/CategorySelect';

const ProductDetail = () => {
    const { productIdx } = useParams();

    const [brandSelectedIdx, setBrandSelectedIdx] = useState(1);
    const [categorySelectedIdx, setCategorySelectedIdx] = useState(1);

    const handleBrandSelectChange = (newBrandSelectedIdx) => {
        setFormData({
            ...formData,
            "brandIdx": newBrandSelectedIdx
        });
        setBrandSelectedIdx(newBrandSelectedIdx);
    };

    const handleCategorySelectChange = (newCategorySelectedIdx) => {
        setFormData({
            ...formData,
            "categoryIdx": newCategorySelectedIdx
        });
        setCategorySelectedIdx(newCategorySelectedIdx);
    };

    const [formData, setFormData] = useState({
        productIdx: '',
        brandIdx: '',
        categoryIdx : '',
        productName: '',
        productPrice : 0,
        productDesc : ''

    });

    const [formErrors, setFormErrors] = useState('');
    const [submitted, setSubmitted] = useState(false);
    
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
   
        const fetchData = async () => {
        try {
            const response = await axios.get('/api/product/detail?productIdx='+ productIdx);
            setFormData({
                productIdx: response.data.productDetail.productIdx,
                brandIdx: response.data.productDetail.brandIdx,
                categoryIdx: response.data.productDetail.categoryIdx,
                productName: response.data.productDetail.productName,
                productPrice: response.data.productDetail.productPrice,
                productDesc : response.data.productDetail.productDesc
            });
            setBrandSelectedIdx(response.data.productDetail.brandIdx)
            setCategorySelectedIdx(response.data.productDetail.categoryIdx)
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
        };

        fetchData();
  }, []);

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

        axios.patch('/api/product/update', formData)
            .then(response => {
                console.log(response);
            })
            .catch(error => setFormErrors(error));
    } else {
      setFormErrors('All fields are required.');
    }
  };

  const handleDelete = (e) => {
    axios.delete('/api/product/delete?productIdx=' + formData.productIdx)
        .then(response => {
            console.log(response);
            navigate('/admin/product')
        })
        .catch(error => setFormErrors(error));
    
  };


  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }
  return (
    <Form onSubmit={handleSubmit}>
        {formErrors && <Alert variant="danger">{formErrors}</Alert>}
        {submitted && <Alert variant="success">Form submitted successfully!</Alert>}
      <Form.Group className="mb-3">
        <Form.Label>상품 번호</Form.Label>
        <Form.Control placeholder={formData.productIdx} name="productIdx" defaultValue={formData.productIdx} readOnly/>
      </Form.Group>
      <Form.Group className="mb-3">
        <BrandSelect selectedIdx={brandSelectedIdx} onSelectChange={handleBrandSelectChange} />
      </Form.Group>
      <Form.Group className="mb-3">
        <CategorySelect selectedIdx={categorySelectedIdx} onSelectChange={handleCategorySelectChange} />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>상품명</Form.Label>
        <Form.Control type="text"  name="productName" defaultValue={formData.productName} onChange={handleChange}/>
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>가격</Form.Label>
        <Form.Control type="number"  name="productPrice" defaultValue={formData.productPrice} onChange={handleChange}/>
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>상품 설명</Form.Label>
        <Form.Control type="text" name="productDesc" defaultValue={formData.productDesc} onChange={handleChange}/>
      </Form.Group>
      <Button variant="primary" type="submit">
        수정
      </Button>
      <Button variant="primary" type="button" onClick={handleDelete}>
        삭제
      </Button>
      <Link to='/admin/product'><Button variant="info">목록</Button></Link>
    </Form>
  );
};

export default ProductDetail;