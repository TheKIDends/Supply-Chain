import {Route, Routes} from "react-router-dom";
import {ROUTERS} from "./util/router";
import ProfileOrdersPage from "./profileOrdersPage";
import ProfilePersonalInformation from "./profilePersonalInformation";

const renderCustom = () => {
    const routers =  [
        {
            path: ROUTERS.USER.ORDERS_PAGE,
            component: <ProfileOrdersPage />
        },
        {
            path: ROUTERS.USER.PERSONAL_INFORMATION,
            component: <ProfilePersonalInformation />
        },
        // {
        //     path: ROUTERS.USER.ADDRESS,
        //     component: <ProfileAddress />
        // },
        // {
        //     path: ROUTERS.USER.NEW_ADDRESS,
        //     component: <ProfileNewAddress />
        // },
        // {
        //     path: ROUTERS.USER.EDIT_ADDRESS,
        //     component: <ProfileEditAddress />
        // },
        // {
        //     path: ROUTERS.USER.CHANGE_PASSWORD,
        //     component: <ProfileChangePassword />
        // },

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