import {Route, Routes} from "react-router-dom";
import {ROUTERS} from "./util/router";
import AddProductPage from "./productManagement/addProductPage";
import ProductListPage from "./productManagement/productListPage";

const renderCustom = () => {
    const routers =  [
        {
            path: ROUTERS.USER.ADD_PRODUCT,
            component: <AddProductPage />
        },
        {
            path: ROUTERS.USER.PRODUCT_LIST,
            component: <ProductListPage />
        },
    ]

    return (
        <Routes>
            {
                routers.map((item, key) => (
                    <Route key={key} path={item.path} element={item.component} />
                ))
            }
            {/*<Route path='*' element={<NotFoundPage />} />*/}
        </Routes>
    )
}

const RouterCustom = () => {
    return renderCustom();
}

export default RouterCustom;