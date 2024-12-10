import {Route, Routes} from "react-router-dom";
import {ROUTERS} from "./util/router";
import AddProductPage from "./productManagement/addProductPage";
import ProductListPage from "./productManagement/productListPage";
import OrderListPage from "./salesManagement/OrderListPage";
import NotFoundPage from "../error/notFoundPage";
import {useCookies} from "react-cookie";
import {useEffect, useState} from "react";
import {USER_ROLE} from "../../util/const";
import LoadingPage from "../loading/loadingPage";
import CategoryListPage from "./categoryManagament/categoryListPage";

const renderAdminCustom = () => {
    const routers =  [

        {
            path: ROUTERS.ADMIN.CATEGORY_LIST,
            component: <CategoryListPage />
        },
    ]

    return (
        <Routes>
            {
                routers.map((item, key) => (
                    <Route key={key} path={item.path} element={item.component} />
                ))
            }
            <Route path='*' element={<NotFoundPage />} />
        </Routes>
    )
}

const renderBusinessCustom = () => {
    const routers =  [
        {
            path: ROUTERS.ADMIN.CATEGORY_LIST,
            component: <AddProductPage />
        },
    ]

    return (
      <Routes>
          {
              routers.map((item, key) => (
                <Route key={key} path={item.path} element={item.component} />
              ))
          }
          <Route path='*' element={<NotFoundPage />} />
      </Routes>
    )
}

const renderCarrierCustom = () => {
    const routers =  [
        {
            path: ROUTERS.ADMIN.CATEGORY_LIST,
            component: <AddProductPage />
        },
    ]

    return (
      <Routes>
          {
              routers.map((item, key) => (
                <Route key={key} path={item.path} element={item.component} />
              ))
          }
          <Route path='*' element={<NotFoundPage />} />
      </Routes>
    )
}

const RouterCustom = () => {
    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const [isLoading, setIsLoading] = useState(true);
    const [user, setUser] = useState();

    const fetchData = async () => {
        const apiUrl = "http://localhost:8000/api/user/get-user-by-token";
        if (accessToken == null) {
            setIsLoading(false);
            return;
        }

        try {
            const response = await fetch(apiUrl, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${accessToken}`
                },
            });

            let jsonResponse = await response.json();
            const dataResponse = jsonResponse.data;
            const messageResponse = jsonResponse.message;

            if (response.ok) {
                setUser(dataResponse);
            } else {
                console.log(messageResponse);
            }
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchData().then(r => {});
    }, []);

    return !isLoading ? ((user && user.role === USER_ROLE.ADMIN && renderAdminCustom()) ||
                         (user && user.role === USER_ROLE.CARRIER && renderCarrierCustom()) ||
                         (user && user.role === USER_ROLE.BUSINESS && renderBusinessCustom()))
                      : <LoadingPage />;
}

export default RouterCustom;