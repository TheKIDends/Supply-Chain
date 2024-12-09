import React, {useEffect, useState} from "react";
import "./style.scss"

import {useCookies} from "react-cookie";
import {useLocation, useNavigate} from "react-router-dom";

import {ROUTERS} from "../../util/router";
import {API, MESSAGE, PROFILE_PAGE, SCROLLING, IMAGE_URL} from "@Const";

import iconOrder from "../../images/order.svg";
import iconEdit from "../../images/edit.svg";
import iconAddress from "../../images/address.svg";
import iconUnlocked from "../../images/unlocked.svg";
import iconLogout from "../../images/logout.svg";

import {useLogout} from "@Util/useLogout";
import queryString from "query-string";
import {toast} from "react-toastify";

const userInfoData = {
  "avatarPath": "https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg",
};

const Menu = () => {
  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;

  const location = useLocation();
  const queryParams = queryString.parse(location.search);
  const [userID, setUserID] = useState(queryParams.userID);

  const logout = useLogout();
  const navigate = useNavigate();
  const [userData, setUserData] = useState(userInfoData);

  const [menuItemsProfile, setMenuItemsProfile] = useState([
    {
      icon: iconOrder,
      text: PROFILE_PAGE.MENU_ITEMS.ORDERS,
      link: "/profile" + ROUTERS.USER.ORDERS_PAGE + "?userID=" + userID,
    },
    {
      icon: iconEdit,
      text: PROFILE_PAGE.MENU_ITEMS.EDIT_PROFILE,
      link: "/profile" + ROUTERS.USER.PERSONAL_INFORMATION + "?userID=" + userID,
    },
    {
      icon: iconAddress,
      text: PROFILE_PAGE.MENU_ITEMS.ADDRESS_BOOK,
      link: "/profile" + ROUTERS.USER.ADDRESS + "?userID=" + userID,
    },
    {
      icon: iconUnlocked,
      text: PROFILE_PAGE.MENU_ITEMS.CHANGE_PASSWORD,
      link: "/profile" + ROUTERS.USER.CHANGE_PASSWORD + "?userID=" + userID,
    },
    {
      icon: iconLogout,
      text: PROFILE_PAGE.MENU_ITEMS.LOGOUT,
      link: "/",
    },
  ]);

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

  const renderMenu = (menuItemsProfile) => menuItemsProfile.map((menuItem, index) => (
      <li className="item-wrap" key={index}>
        <div className="img-wrap">
          <img src={menuItem.icon} alt={`icon ${menuItem.text}`} />
        </div>
        <div>
          <div className="text navigate-text pointer-cursor"
               onClick={() => {
                 if (menuItem.text !== PROFILE_PAGE.MENU_ITEMS.LOGOUT) {
                   navigate(menuItem.link, {
                     state: { scrolling: SCROLLING.SMOOTH },
                   });
                 }
               }}
          >
            {menuItem.text}
          </div>
        </div>
      </li>
  ));


  return (
      <div className="col-4 menu-wrap item-row">
        <div className="header-wrap">
          <div className="image-wrap">
            <img style={{width:"70px", height:"70px"}}
                src={userData.avatarPath ? userData.avatarPath : IMAGE_URL.DEFAULT_AVATAR_IMG}
                alt={''}
                id="action-upload"
            />

          </div>

          <div className="text-header" style={{margin: "0 0 0 18px"}}>

            <p style={{marginBottom: "5px"}}>{user && user.role}</p>
            <p style={{wordBreak: "break-word", textAlign: "left", whiteSpace: "normal", fontSize: "18px"}}
               className="name"
            >
              {user && user.fullName}
            </p>

          </div>
        </div>

        <div className="menu-nav-wrap">
          <ul>{renderMenu(menuItemsProfile)}</ul>
        </div>
      </div>
  );
};

export default Menu;
