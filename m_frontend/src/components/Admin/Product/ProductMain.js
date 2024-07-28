import React, { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';

const ProductMain = (props) =>{

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
   
    const fetchData = async () => {
      try {
        const response = await axios.get('/api/product/list');
        setData(response.data.productList);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }


  return(
      <div className="wrapper">
         <h2>Admin</h2>
         <h5>- 상품 관리</h5>
        <div class="d-flex justify-content-end mb-4">
          <Link to='/admin/product/create'><Button variant="primary">상품 등록</Button></Link>
        </div>
        <div>
          <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>#</th>
              <th>상품명</th>
              <th>가격</th>
              <th>브랜드</th>
              <th>카테고리</th>
            </tr>
          </thead>
          <tbody>
            {data.map(item => (
              <tr key={item.productIdx}>
                <td>{item.productIdx}</td>
                <td><Link to={`/admin/product/detail/${item.productIdx}`}>{item.productName}</Link></td>
                <td>{item.productPrice}</td>
                <td>{item.brandName}</td>
                <td>{item.categoryName}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
      <div className="d-flex justify-content-center">
          <Link to='/admin'><Button className="admin-button" variant="info">Admin 메인</Button></Link>
      </div>
    </div>
    )
}

export default ProductMain;