import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {useCookies} from "react-cookie";
import {useLogout} from "@Util/useLogout";
import {API, HEADER, MESSAGE} from "@Const";

const userInfoData = {
    "userID": 4,
    "fullName": "Nguyen Chau Khanh",
    "email": "khachhang@gmail.com",
    "hashedPassword": null,
    "phoneNumber": "09090909",
    "gender": "Nam",
    "dateBirthday": "1933-02-03",
    "avatarPath": "",
    "isAdmin": false
};

const ProfileMenu = ({openModal}) => {
    const accessToken = true;

    const logout = useLogout();

    const [userData, setUserData] = useState(userInfoData);
    const [isAdmin, setIsAdmin] = useState(true);
    const [userID, setUserID] = useState(4);
    const [profileMenuVisible, setProfileMenuVisible] = useState(false);

    return (
        <div className="user-drop h-100 position-relative d-flex align-items-center justify-content-end" id="user-drop"
             onMouseEnter={() => {setProfileMenuVisible(true)}}
             onMouseLeave={() => {setProfileMenuVisible(false)}}
        >
            { accessToken ?
                <>
                    <a href = {userID ? `/profile/orders?userID=${userID}` : ''}>
                        <div className="pointer-cursor">
                            <svg width="22" height="22" viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M10 0C4.48625 0 0 4.48625 0 10C0 15.5137 4.48625 20 10 20C15.5137 20 20 15.5137 20 10C20 4.48625 15.5137 0 10 0ZM6.25 8.75C6.25 6.68187 7.93187 5 10 5C12.0681 5 13.75 6.68187 13.75 8.75V10C13.75 12.0681 12.0681 13.75 10 13.75C7.93187 13.75 6.25 12.0681 6.25 10V8.75ZM10 18.75C8.17937 18.75 6.4875 18.19 5.08563 17.2344C6 16.2475 7.30188 15.625 8.75 15.625H11.25C12.6981 15.625 14 16.2475 14.9144 17.2344C13.5125 18.19 11.8206 18.75 10 18.75Z"
                                    fill="#4F525D"
                                ></path>
                            </svg>
                        </div>
                    </a>
                    <div className={`account_header position-absolute ${profileMenuVisible ? "show" : ""}`} style={{textDecoration: "none"}}>
                        <ul className="p-0 m-0">
                            <li>
                                <a href={`/profile/orders?userID=${userID}`} style={{ wordBreak: "break-word", textAlign: "left", whiteSpace: "normal"}}>
                                    {userData.fullName}
                                </a>
                            </li>

                            { isAdmin &&
                                <li>
                                    <a href="/management-page/categories-and-products">{ HEADER.PROFILE_MENU.DASHBOARD }</a>
                                </li>
                            }
                            <li>
                                <a href={`/profile/orders?userID=${userID}`}>{ HEADER.PROFILE_MENU.MY_ORDERS }</a>
                            </li>
                            <li>
                                <a href={`/profile/personal-information?userID=${userID}`}>{ HEADER.PROFILE_MENU.PERSONAL_INFO }</a>
                            </li>
                            <li>
                                <a href={`/profile/address?userID=${userID}`}>{ HEADER.PROFILE_MENU.ADDRESS_BOOK }</a>
                            </li>
                            <li>
                                <a href={`/profile/change-password?userID=${userID}`}>{ HEADER.PROFILE_MENU.CHANGE_PASSWORD }</a>
                            </li>
                            <li className="logout">
                                <a>{ HEADER.PROFILE_MENU.LOGOUT }</a>
                            </li>
                        </ul>
                    </div>
                </>
                :
                <a className="pointer-cursor" onClick={() => openModal('login')}>
                    <svg width="22" height="22" viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M10 0C4.48625 0 0 4.48625 0 10C0 15.5137 4.48625 20 10 20C15.5137 20 20 15.5137 20 10C20
                            4.48625 15.5137 0 10 0ZM6.25 8.75C6.25 6.68187 7.93187 5 10 5C12.0681 5 13.75 6.68187 13.75
                            8.75V10C13.75 12.0681 12.0681 13.75 10 13.75C7.93187 13.75 6.25 12.0681 6.25 10V8.75ZM10
                            18.75C8.17937 18.75 6.4875 18.19 5.08563 17.2344C6 16.2475 7.30188 15.625 8.75
                            15.625H11.25C12.6981 15.625 14 16.2475 14.9144 17.2344C13.5125 18.19 11.8206 18.75 10 18.75Z"
                            fill="#4F525D"
                        ></path>
                    </svg>
                </a>
            }
        </div>
    );
};

export default ProfileMenu;