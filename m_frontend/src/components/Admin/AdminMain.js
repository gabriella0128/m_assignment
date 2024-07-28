import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

const AdminMain = (props) =>{
    return(
        <div className="wrapper">
            <div>
                <h2>Admin</h2>
                <ul>
                    <li>브랜드 관리 : 브랜드 CRUD</li>
                    <li>상품 관리 : 상품 CRUD</li>
                    <li>HOME : 뒤로가기 (첫페이지)</li>
                </ul>
            </div>            
        <div className="button-list gap-2">
                <Link to='/admin/brand'><Button className="admin-button" variant="info">브랜드 관리</Button></Link>
                <Link to='/admin/product'><Button className="admin-button" variant='info'>상품 관리</Button></Link>
                <Link to='/'><Button className="admin-button" variant='primary'>HOME</Button></Link>
        </div>
        </div>
    )
}

export default AdminMain;