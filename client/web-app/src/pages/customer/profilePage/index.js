import React, {useState} from 'react';
import './style.scss'

import {ScrollToTop, ScrollToTopSmooth} from '@Util/util';
import {API, BREADCRUMB, SCROLLING} from "@Const";

import {useCookies} from "react-cookie";
import {useLocation} from "react-router-dom";
import queryString from "query-string";
import Menu from "./components/Menu/Menu";
import RouterCustom from "./router";

const ProfilePage = () => {
    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const location = useLocation();
    const queryParams = queryString.parse(location.search);
    const [userID, setUserID] = useState(queryParams.userID);

    return (
        <>
            <div id="app">
                {location.state?.scrolling === SCROLLING.SMOOTH ? <ScrollToTopSmooth/> : <ScrollToTop/>}
                <main id="main">
                    <div className="container profile-wrap">
                        <div className="breadcrumb-wrap">
                            <a href="/">{BREADCRUMB.HOME_PAGE}</a>
                            &gt; <span>{BREADCRUMB.USER_ACCOUNT}</span>
                        </div>

                        <div className="row content-wrap" style={{padding: "0 0 60px 0"}}>
                            <Menu/>
                            <RouterCustom/>
                        </div>
                    </div>
                </main>
            </div>
        </>
    );
}

export default ProfilePage;