import axios from 'axios';
import Table from 'react-bootstrap/Table';
import React, { useState, useEffect } from 'react';

const CheapestProductsPerCategory = () => {
    const [data, setData] = useState({
      'dataFetchResult' : false,
      'itemList' : [],
      'totalPrice' : 0
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
   
    const fetchData = async () => {
      try {
        const response = await axios.get('/api/search/cheapestProductsPerCategory');
        if(response.data.result === true){
          setData({
            'dataFetchResult' : true,
            'itemList' : response.data.items,
            'totalPrice' : response.data.totalPrice
          });
        }
        
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
          { data.dataFetchResult ? 
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
              {data.itemList.map(item => (
                <tr key={item.productIdx}>
                  <td>{item.categoryName}</td>
                  <td>{item.brandName}</td>
                  <td>{item.productName}</td>
                  <td>{item.productPrice}</td>
                </tr>
              ))}
            </tbody>
          </Table>
          <p className='color-blue'>총액 : { data.totalPrice } 원</p>
          </div>
          :
          <div>
            <p>결과없음</p>
          </div>
          }
      </div>
    )
}

export default CheapestProductsPerCategory;
