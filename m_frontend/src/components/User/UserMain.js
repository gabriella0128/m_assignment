import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import CheapestProductsPerCategory from './Search/CheapestProductsPerCategory';
import CheapestCodiBrand from './Search/CheapestCodiBrand';
import LowestAndHighestPricesForACategory from './Search/LowestAndHighestPricesForACategory';

const UserMain = (props) => {
    return (
        <div className="wrapper">
            <div>
                <Tabs
                defaultActiveKey="cheapestProductsPerCategory"
                className="mb-3"
                >
                <Tab eventKey="cheapestProductsPerCategory" title="카테고리별 최저가">
                    <CheapestProductsPerCategory/>
                </Tab>
                <Tab eventKey="cheapestCodiBrand" title="단일 브랜드 코디 최저가">
                    <CheapestCodiBrand/>
                </Tab>
                <Tab eventKey="lowestAndHighestPricesForACategory" title="카테고리 검색(최저가, 최고가)">
                    <LowestAndHighestPricesForACategory/>
                </Tab>
                </Tabs>
            </div>
            <div className="button-list gap-2">
                <Link to='/'><Button className="admin-button" variant='primary'>HOME</Button></Link>
            </div>
        </div>
      );
}

export default UserMain;