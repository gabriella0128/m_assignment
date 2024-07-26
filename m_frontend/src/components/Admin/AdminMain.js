import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

const AdminMain = (props) =>{
    return(
        <div className="d-grid gap-2">
                <Link to='/admin/brand'><Button variant="info">브랜드 관리</Button></Link>
                <Link to='/admin/product'><Button variant='info'>상품 관리</Button></Link>
                <Link to='/'><Button variant='primary'>HOME</Button></Link>
        </div>
    )
}

export default AdminMain;