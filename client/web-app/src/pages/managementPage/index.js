import React, {useEffect, useState} from 'react';
import './style.scss'
import RouterCustom from "./router";
import NavigationMenu from "./component/NavigationMenu/NavigationMenu";
import {ScrollToTop, ScrollToTopSmooth} from "@Util/util";
import {SCROLLING} from "@Const";
import {useLocation} from "react-router-dom";
import {useCookies} from "react-cookie";
import LoadingPage from "../loading/loadingPage";

const ManagementPage = () => {
  const location = useLocation();

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

  return isLoading ? <LoadingPage /> :
    <div style={{display: "flex", justifyContent: "flex-start"}}>
      {location.state?.scrolling === SCROLLING.SMOOTH ? <ScrollToTopSmooth/> : <ScrollToTop/>}
      <NavigationMenu userRole={user && user.role}/>
      <div style={{width: "100%", minHeight: "630px", paddingLeft: "30px", backgroundColor: "#f5f5f5", overflowY: "auto"}}>
        <RouterCustom/>
      </div>
    </div>
}

export default ManagementPage;