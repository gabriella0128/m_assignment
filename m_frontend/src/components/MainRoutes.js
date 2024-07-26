import React from "react";
import { Routes , Route } from 'react-router-dom';
import Home from "./Home/Home";
import AdminMain from './Admin/AdminMain';
import BrandMain from "./Admin/Brand/BrandMain";
import BrandCreate from "./Admin/Brand/BrandCreate";
import BrandDetail from "./Admin/Brand/BrandDetail";
import ProductMain from "./Admin/Product/ProductMain";
import ProductCreate from "./Admin/Product/ProductCreate";
import ProductDetail from "./Admin/Product/ProductDetail";
import UserMain from './User/UserMain';

const MainRoute = () => {
    return (
      <Routes>
        <Route path='/' element={<Home/>} />
        <Route path='/admin' element={<AdminMain/>} />
        <Route path="/admin/brand" element={<BrandMain/>} />
        <Route path="/admin/brand/create" element={<BrandCreate />} />
        <Route path="/admin/brand/detail/:brandIdx" element={<BrandDetail />} />
        <Route path="/admin/product" element={<ProductMain/>} />
        <Route path="/admin/product/create" element={<ProductCreate />} />
        <Route path="/admin/product/detail/:productIdx" element={<ProductDetail />} />
        <Route path='/user' element={<UserMain/>} />
      </Routes>
    );
}

export default MainRoute;