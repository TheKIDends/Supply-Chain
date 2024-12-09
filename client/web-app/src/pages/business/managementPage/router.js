import {Route, Routes} from "react-router-dom";
import {ROUTERS} from "./util/router";
import AddProductPage from "./productManagement/addProductPage";
import ProductListPage from "./productManagement/productListPage";
import OrderListPage from "./salesManagement/OrderListPage";

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
        {
            path: ROUTERS.USER.ORDER_LIST,
            component: <OrderListPage />
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