import React, {useContext, useEffect, useState} from "react";
import {Cookies, useCookies} from "react-cookie";
import { Link } from "react-router-dom";
import './style.scss';

import {CartContext} from "../masterLayout";

import LoginDialog from "@Components/dialogs/LoginDialog/LoginDialog";
import RegisterDialog from "@Components/dialogs/RegisterDialog/RegisterDialog";
import ForgotPasswordDialog from "@Components/dialogs/ForgotPasswordDialog/ForgotPasswordDialog";

import {API, USER_ROLE, DIALOGS, HEADER, IMAGE_URL, MESSAGE} from "@Const";
import ProfileMenu from "./components/ProfileMenu/ProfileMenu";
import {toast} from "react-toastify";
import {da} from "date-fns/locale";

const Header = () => {
    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const [isLoading, setIsLoading] = useState(false);
    const [menuItems, setMenuItems] = useState([{}]);
    const [user, setUser] = useState();

    const cartContext = useContext(CartContext);

    const [openDialog, setOpenDialog] = useState(null);

    const openModal = (dialogName) => {
        setOpenDialog(dialogName);
    };

    const closeModal = () => {
        setOpenDialog(null);
    };

    const handleDialogSwitch = (dialogName) => {
        openModal(dialogName);
    };

    const handleDialogClose = () => {
        closeModal();
    };

    const fetchData = async () => {
        const apiUrl = "http://localhost:8000/api/user/get-user-by-token";
        if (accessToken == null) {
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
        }
    };

    useEffect(() => {
        fetchData().then(r => {});
    }, []);

    return ( !isLoading &&
        <header id="header">
            <div className="header position-fixed">
                <div className="wrap-container">
                    <div className="container ps-0 pe-0">
                        <div className="row wrap-content m-0 position-relative">

                            <div className="col-9 content-left d-flex align-items-center ps-0 pe-0"
                                 style={{height: "75px"}}>
                                <div className="logo-box">
                                    <a href="/">
                                        <img className="logo" src={IMAGE_URL.LOGO_SUPPLY_CHAIN} style={{height: "35px"}}
                                             alt="Logo"/>
                                    </a>
                                </div>

                                <a href="/"
                                   className="text-decoration-none menu-header p-0 d-flex align-items-center
                                                justify-content-center position-relative h-100 underline-effect"
                                   style={{marginRight: "20px", width: "130px"}}
                                >
                                    <div
                                        className="menu-header-text d-flex align-items-center text-center position-relative"
                                    >
                                        {HEADER.HEADER_ITEM.HOMEPAGE}
                                    </div>
                                </a>

                                <a href="/market"
                                   className="text-decoration-none menu-header p-0 d-flex align-items-center
                                                justify-content-center position-relative h-100 underline-effect"
                                   style={{marginRight: "20px", width: "130px"}}
                                >
                                    <div
                                        className="menu-header-text d-flex align-items-center text-center position-relative"
                                    >
                                        {HEADER.HEADER_ITEM.PRODUCT}
                                    </div>
                                </a>

                                {   user && (user.role == USER_ROLE.BUSINESS ||user.role == USER_ROLE. ADMIN) &&
                                    <a href="/management-page/product-list"
                                       className="text-decoration-none menu-header p-0 d-flex align-items-center
                                                justify-content-center position-relative h-100 underline-effect"
                                       style={{marginRight: "20px", width: "130px"}}
                                    >
                                        <div
                                            className="menu-header-text d-flex align-items-center text-center position-relative"
                                        >
                                            {HEADER.HEADER_ITEM.MANAGE}
                                        </div>
                                    </a>
                                }


                            </div>
                            <div
                                className="col-3 content-right d-flex justify-content-end align-items-center ps-0 pe-0">
                                <div className="header-tool h-100">
                                    <div className="d-flex justify-content-end align-items-center h-100">
                                        <div className="cart-drop position-relative d-flex justify-content-end">
                                            <Link to="/cart">
                                                <svg width="22" height="22" viewBox="0 0 22 22" fill="none">
                                                <path
                                                        d="M16.25 0H3.75C2.375 0 1.25 1.125 1.25 2.5V19.375C1.25 19.75
                                                        1.5 20 1.875 20H18.125C18.5 20 18.75 19.75 18.75 19.375V2.5C18.75
                                                        1.125 17.625 0 16.25 0ZM10 12.5C7.25 12.5 5 10.25 5 7.5C5 7.125
                                                        5.25 6.875 5.625 6.875C6 6.875 6.25 7.125 6.25 7.5C6.25 9.5625
                                                        7.9375 11.25 10 11.25C12.0625 11.25 13.75 9.5625 13.75 7.5C13.75
                                                        7.125 14 6.875 14.375 6.875C14.75 6.875 15 7.125 15 7.5C15 10.25
                                                        12.75 12.5 10 12.5ZM16.25 3.75H3.75C3.0625 3.75 2.5 3.1875 2.5
                                                        2.5C2.5 1.8125 3.0625 1.25 3.75 1.25H16.25C16.9375 1.25 17.5
                                                        1.8125 17.5 2.5C17.5 3.1875 16.9375 3.75 16.25 3.75Z"
                                                        fill="#4F525D"
                                                    ></path>
                                                </svg>
                                                <span
                                                    className="count_item count_item_pr hidden-count position-absolute
                                                    text-center d-flex align-items-center justify-content-center"
                                                >
                                                    {cartContext.amountInCart}
                                                </span>
                                            </Link>
                                        </div>

                                        <ProfileMenu openModal={openModal}/>

                                        <div className="btn-open-modal-change-password"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {openDialog === DIALOGS.LOGIN && (
                <div className="modal-overlay">
                    <LoginDialog onClose={handleDialogClose} onSwitch={handleDialogSwitch} />
                </div>
            )}

            {openDialog === DIALOGS.REGISTER && (
                <div className="modal-overlay">
                    <RegisterDialog onClose={handleDialogClose}  onSwitch={handleDialogSwitch} />
                </div>
            )}

            {openDialog === DIALOGS.FORGOT_PASSWORD && (
                <div className="modal-overlay">
                    <ForgotPasswordDialog onClose={handleDialogClose}  onSwitch={handleDialogSwitch} />
                </div>
            )}
        </header>
    );
}

export default Header;
