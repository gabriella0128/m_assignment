import {Link} from 'react-router-dom';

const Home = (props) => {

    return(
        <div>
            <div>
                <Link to='/admin'><button>admin</button></Link>
            </div>
            <div>
                <Link to='/user'><button>user</button></Link>
            </div>
      </div> 
    )
}

export default Home;