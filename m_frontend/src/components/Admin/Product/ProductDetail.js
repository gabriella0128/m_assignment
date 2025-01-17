import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert  from 'react-bootstrap/Alert';
import BrandSelect from '../../Reusable/BrandSelect'
import CategorySelect from '../../Reusable/CategorySelect';

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

    const handleCategorySelectChange = (selectedCategory) => {
      const { categoryIdx } = selectedCategory;      
        setFormData({
            ...formData,
            "categoryIdx": categoryIdx
        });
        setCategorySelectedIdx(categoryIdx);
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
    <div className="wrapper">
      <h5 className="mb-3">상품 상세 (수정, 삭제 가능)</h5>
      <Form onSubmit={handleSubmit}>
          {formErrors && <Alert variant="danger">{formErrors}</Alert>}
          {submitted && <Alert variant="success">Form submitted successfully!</Alert>}
        <div className='select-wrapper'>
        <Form.Group className="mb-3">
          <Form.Label>상품 번호</Form.Label>
          <Form.Control className='readonly' placeholder={formData.productIdx} name="productIdx" defaultValue={formData.productIdx} readOnly/>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>브랜드</Form.Label>
          <BrandSelect selectedIdx={brandSelectedIdx} onSelectChange={handleBrandSelectChange} />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>카테고리</Form.Label>
          <CategorySelect selectedIdx={categorySelectedIdx} onSelectChange={handleCategorySelectChange} />
        </Form.Group>
        </div>
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
        <div className='button-list gap-2'>
          <Button className="admin-button" variant="primary" type="submit">
            수정
          </Button>
          <Button className="admin-button" variant="primary" type="button" onClick={handleDelete}>
            삭제
          </Button>
          <Link to='/admin/product'><Button className="admin-button" variant="info">목록</Button></Link>
        </div>
      </Form>
    </div>
  );
};

export default ProductDetail;