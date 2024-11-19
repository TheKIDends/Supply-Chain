import {Route, Routes} from "react-router-dom";

import {ROUTERS} from "./util/router";

import MasterLayout from "./theme/masterLayout";
import HomePage from "./pages/user/customer/homePage";
import ManagementPage from "./pages/user/business/managementPage";
import ProfilePage from "./pages/user/customer/profilePage";
// import NotFoundPage from "./pages/error/notFoundPage";
// import DoNotHavePermissionPage from "./pages/error/doNotHavePermissionPage";

const userRouters =  [
    {
        path: ROUTERS.USER.HOME,
        component: <HomePage />
    },
    {
        path: ROUTERS.USER.PROFILE,
        component: <ProfilePage />
    },
];

const adminRouters =  [
    {
        path: ROUTERS.USER.MANAGEMENT,
        component: <ManagementPage />
    },
]

const renderUserCustom = () => {
    return (
        <MasterLayout>
            <Routes>
                {
                    userRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
                {/*<Route path='/admin/*' element={<DoNotHavePermissionPage />} />*/}
                {/*<Route path='*' element={<NotFoundPage />} />*/}
            </Routes>
        </MasterLayout>
    )
}

const renderAdminCustom = () => {
    return (
        <MasterLayout>
            <Routes>
                {
                    userRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
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

const RouterCustom = () => {
    return renderAdminCustom();
}

export default RouterCustom;