import axios from 'axios';
import Table from 'react-bootstrap/Table';
import React, { useState, useEffect } from 'react';

const CheapestProductsPerCategory = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
   
    const fetchData = async () => {
      try {
        const response = await axios.get('/api/search/cheapestProductsPerCategory');
        setData(response.data.items);
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
        <div>
          <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>카테고리</th>
              <th>브랜드명</th>
              <th>상품명</th>
              <th>가격</th>
            </tr>
          </thead>
          <tbody>
            {data.map(item => (
              <tr key={item.productIdx}>
                <td>{item.categoryName}</td>
                <td>{item.brandName}</td>
                <td>{item.productName}</td>
                <td>{item.productPrice}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    )
}

export default CheapestProductsPerCategory;
