import {Route, Routes} from "react-router-dom";

import {ROUTERS} from "./util/router";
import {useEffect, useState} from "react";
import {useCookies} from "react-cookie";
import {USER_ROLE} from "./util/const";

import LoadingPage from "./pages/loading/loadingPage";
import HomePage from "./pages/customer/homePage";
import ProfilePage from "./pages/customer/profilePage";
import MarketplacePage from "./pages/customer/marketplacePage";
import ManagementPage from "./pages/business/managementPage";
import MasterLayout from "./theme/masterLayout";
// import NotFoundPage from "./pages/error/notFoundPage";
// import DoNotHavePermissionPage from "./pages/error/doNotHavePermissionPage";

const adminRouters = [
    {
        path: ROUTERS.ADMIN.HOME,
        component: <HomePage />
    },
    {
        path: ROUTERS.ADMIN.PROFILE,
        component: <ProfilePage />
    },
    {
        path: ROUTERS.ADMIN.MARKET,
        component: <MarketplacePage />
    },
    {
        path: ROUTERS.ADMIN.MANAGEMENT,
        component: <ManagementPage />
    },
];

const customerRouters = [
    {
        path: ROUTERS.CUSTOMER.HOME,
        component: <HomePage />
    },
    {
        path: ROUTERS.CUSTOMER.PROFILE,
        component: <ProfilePage />
    },
    {
        path: ROUTERS.CUSTOMER.MARKET,
        component: <MarketplacePage />
    },
];

const businessRouters = [
    {
        path: ROUTERS.BUSINESS.HOME,
        component: <HomePage />
    },
    {
        path: ROUTERS.BUSINESS.PROFILE,
        component: <ProfilePage />
    },
    {
        path: ROUTERS.BUSINESS.MARKET,
        component: <MarketplacePage />
    },
    {
        path: ROUTERS.ADMIN.MANAGEMENT,
        component: <ManagementPage />
    },
];

const carrierRouters = [
    {
        path: ROUTERS.BUSINESS.HOME,
        component: <HomePage />
    },
    {
        path: ROUTERS.BUSINESS.PROFILE,
        component: <ProfilePage />
    },
    {
        path: ROUTERS.BUSINESS.MARKET,
        component: <MarketplacePage />
    },
    {
        path: ROUTERS.ADMIN.MANAGEMENT,
        component: <ManagementPage />
    },
];

const renderAdminCustom = () => {
    return (
        <MasterLayout>
            <Routes>
                {
                    adminRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }

                {/*<Route path='*' element={<NotFoundPage />} />*/}
            </Routes>
        </MasterLayout>
    )
}

const renderCustomerCustom = () => {
    return (
        <MasterLayout>
            <Routes>
                {
                    customerRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
                {/*<Route path='/admin/*' element={<DoNotHavePermissionPage />} />*/}
                {/*<Route path='*' element={<NotFoundPage />} />*/}
            </Routes>
        </MasterLayout>
    )
}

const renderBusinessCustom = () => {
    return (
        <MasterLayout>
            <Routes>
                {
                    businessRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
                {/*<Route path='/admin/*' element={<DoNotHavePermissionPage />} />*/}
                {/*<Route path='*' element={<NotFoundPage />} />*/}
            </Routes>
        </MasterLayout>
    )
}

const renderCarrierCustom = () => {
    return (
        <MasterLayout>
            <Routes>
                {
                    carrierRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
                {/*<Route path='/admin/*' element={<DoNotHavePermissionPage />} />*/}
                {/*<Route path='*' element={<NotFoundPage />} />*/}
            </Routes>
        </MasterLayout>
    )
}

const RouterCustom = () => {
    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const [isLoading, setIsLoading] = useState(true);
    const [user, setUser] = useState();

    const fetchData = async () => {
        const apiUrl = "http://localhost:8000/api/user/get-user-by-token";
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

    return !isLoading ? ((user.role === USER_ROLE.ADMIN && renderAdminCustom()) ||
                         (user.role === USER_ROLE.CUSTOMER && renderCustomerCustom()) ||
                         (user.role === USER_ROLE.BUSINESS && renderBusinessCustom()) ||
                         (user.role === USER_ROLE.CARRIER && renderCarrierCustom()))
            : <LoadingPage />;
}

export default RouterCustom;