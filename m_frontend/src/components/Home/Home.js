import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

const Home = (props) => {

    return(
        <div className="d-grid gap-2">
                <Link to='/admin'><Button variant="info">admin</Button></Link>
                <Link to='/user'><Button variant="info">user</Button></Link>
        </div> 
    )
}

export default Home;