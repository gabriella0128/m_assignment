import React, { useState } from 'react';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert  from 'react-bootstrap/Alert';
import Table from 'react-bootstrap/Table';
import CategorySelect from '../../Reusable/CategorySelect';

const LowestAndHighestPricesForACategory = () => {
    const [formData, setFormData] = useState({
        "categoryName" : ""
    });

    const [categorySelectedIdx, setCategorySelectedIdx] = useState();

    const [searchResult, setSearchResult] = useState({
      "searchResultFetch" : false,
      "lowestPriceItem" : {},
      "highestPriceItem" : {}
    })

    const [formErrors, setFormErrors] = useState('');

    const handleCategorySelectChange = (selectedCategory) => {
      const { categoryIdx, categoryName } = selectedCategory;      
        setFormData({
            ...formData,
            "categoryName": categoryName
        });
        setCategorySelectedIdx(categoryIdx);
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
    
        if (validateForm()) {
            setFormErrors('');
    
            axios.get('/api/search/lowestAndHighestPricesForACategory?categoryName=' + formData.categoryName)
                .then(response => {
                    console.log(response);
                      if(response.data.result === true){
                        setSearchResult({
                          "searchResultFetch" : true,
                          "lowestPriceItem" : response.data.lowestPriceItem,
                          "highestPriceItem" : response.data.highestPriceItem
                        })
                    }else{
                      setSearchResult({
                        "searchResultFetch" : false,
                        "lowestPriceItem" : {},
                        "highestPriceItem" : {}
                      })
                    }  
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
            <Form.Group className="mb-5">
                <CategorySelect selectedIdx={categorySelectedIdx} onSelectChange={handleCategorySelectChange}/>
            </Form.Group>
            <Button variant="primary" type="submit">
                검색
            </Button>
            </Form>
            </div>
            <div>
              {searchResult.searchResultFetch ?
                <div>
                  <div>
                    <p>최저가</p>
                    <Table striped bordered hover size="sm">
                      <thead>
                        <tr>
                          <th>브랜드명</th>
                          <th>상품명</th>
                          <th>가격</th>
                        </tr>
                      </thead>
                      <tbody>
                          <tr key={searchResult.lowestPriceItem.productIdx}>
                            <td>{searchResult.lowestPriceItem.brandName}</td>
                            <td>{searchResult.lowestPriceItem.productName}</td>
                            <td>{searchResult.lowestPriceItem.productPrice}</td>
                          </tr>
                      </tbody>
                    </Table>
                  </div>
                  <div>
                    <p>최고가</p>
                    <Table striped bordered hover size="sm">
                      <thead>
                        <tr>
                          <th>브랜드명</th>
                          <th>상품명</th>
                          <th>가격</th>
                        </tr>
                      </thead>
                      <tbody>
                          <tr key={searchResult.highestPriceItem.productIdx}>
                            <td>{searchResult.highestPriceItem.brandName}</td>
                            <td>{searchResult.highestPriceItem.productName}</td>
                            <td>{searchResult.highestPriceItem.productPrice}</td>
                          </tr>
                      </tbody>
                    </Table>
                  </div>
                </div> :
                <div>
                  <p>검색결과 없음</p>
                </div>
                }
            </div>
        </div>
      );
}

export default LowestAndHighestPricesForACategory;