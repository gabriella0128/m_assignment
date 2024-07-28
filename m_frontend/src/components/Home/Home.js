import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

const Home = (props) => {

    return(
        <div className="wrapper">
            <div>
                <h1>Home</h1>
                <ul>
                    <li>admin : 브랜드,상품 CRUD 가능</li>
                    <li>user : 캍테고리별 최저가, 단일 브랜드 코디 최저가, 카테고리검색(최저가,최고가) 조회 가능</li>
                </ul>
            </div>
            <div className="button-list gap-4">
                <Link to='/admin'><Button className="admin-button" variant="info">admin</Button></Link>
                <Link to='/user'><Button className="admin-button" variant="info">user</Button></Link>
            </div> 
        </div>
    )
}

export default Home;