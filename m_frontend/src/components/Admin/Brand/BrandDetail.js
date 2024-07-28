import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert  from 'react-bootstrap/Alert';

const BrandDetail = () => {
    const { brandIdx } = useParams();

    const [formData, setFormData] = useState({
        brandIdx: '',
        brandName: '',
        brandDesc: '',

    });

    const [formErrors, setFormErrors] = useState('');
    const [submitted, setSubmitted] = useState(false);
    
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
   
        const fetchData = async () => {
        try {
            const response = await axios.get('/api/brand/detail?brandIdx='+ brandIdx);
            setFormData({
                brandIdx: response.data.brandDetail.brandIdx,
                brandName: response.data.brandDetail.brandName,
                brandDesc : response.data.brandDetail.brandDesc
            });
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

        axios.patch('/api/brand/update', formData)
            .then(response => {
                console.log(response);
            })
            .catch(error => setFormErrors(error));
    } else {
      setFormErrors('All fields are required.');
    }
  };

  const handleDelete = (e) => {
    axios.delete('/api/brand/delete?brandIdx=' + formData.brandIdx)
        .then(response => {
            console.log(response);
            navigate('/admin/brand')
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
      <h5 className="mb-3">브랜드 상세 (수정, 삭제 가능)</h5>
      <Form onSubmit={handleSubmit}>
          {formErrors && <Alert variant="danger">{formErrors}</Alert>}
          {submitted && <Alert variant="success">Form submitted successfully!</Alert>}
        <Form.Group className="mb-3">
          <Form.Label>브랜드 번호</Form.Label>
          <Form.Control className="readonly" placeholder={formData.brandIdx} name="brandIdx" defaultValue={formData.brandIdx} readOnly/>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>브랜드 이름</Form.Label>
          <Form.Control type="text"  name="brandName" defaultValue={formData.brandName} onChange={handleChange}/>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>브랜드 설명</Form.Label>
          <Form.Control type="text" name="brandDesc" defaultValue={formData.brandDesc} onChange={handleChange}/>
        </Form.Group>
        <div className='button-list gap-2'>
          <Button className="admin-button" variant="primary" type="submit">
            수정
          </Button>
          <Button className="admin-button" variant="primary" type="button" onClick={handleDelete}>
            삭제
          </Button>
          <Link to='/admin/brand'><Button className="admin-button" variant="info">목록</Button></Link>
        </div>  
      </Form>
    </div>
  );
};

export default BrandDetail;