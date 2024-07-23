import React from "react";
import { Routes , Route } from 'react-router-dom';
import Home from "./Home/Home";
import AdminMain from './Admin/AdminMain';
import UserMain from './User/UserMain';

const MainRoute = () => {
    return (
      <Routes>
        <Route path='/' element={<Home/>}></Route>
        <Route path='/admin' element={<AdminMain/>}></Route>
        <Route path='/user' element={<UserMain/>}></Route>
      </Routes>
    );
}

export default MainRoute;