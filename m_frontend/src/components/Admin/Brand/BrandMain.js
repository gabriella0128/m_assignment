import React, { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';

const BrandMain = (props) =>{

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
   
    const fetchData = async () => {
      try {
        const response = await axios.get('/api/brand/list');
        setData(response.data.brandList);
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
        <h5>- 브랜드 관리</h5>
        <div className="d-flex justify-content-end mb-3">
          <Link to='/admin/brand/create'><Button variant="primary">브랜드 등록</Button></Link>
        </div>
        <div>
          <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>#</th>
              <th>브랜드명</th>
              <th>브랜드 설명</th>
            </tr>
          </thead>
          <tbody>
            {data.map(item => (
              <tr key={item.brandIdx}>
                <td>{item.brandIdx}</td>
                <td><Link to={`/admin/brand/detail/${item.brandIdx}`}>{item.brandName}</Link></td>
                <td>{item.brandDesc}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
      <div class="d-flex justify-content-center">
          <Link to='/admin'><Button variant="info">Admin 메인</Button></Link>
      </div>
    </div>
    )
}

export default BrandMain;