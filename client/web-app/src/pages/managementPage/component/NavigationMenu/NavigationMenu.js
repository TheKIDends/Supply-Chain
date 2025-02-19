import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import './style.scss'

import {ROUTERS} from "../../util/router";
import {ConfigProvider, Menu} from 'antd';
import {MANAGEMENT_PAGE, SCROLLING, USER_ROLE} from "@Const";

import {FaRegUser} from "react-icons/fa";
import {RiShoppingCart2Line} from "react-icons/ri";
import {AiOutlineShop} from "react-icons/ai";
import {TbShoppingBag} from "react-icons/tb";
import {MdMenu} from "react-icons/md";

function getItem(label, key, icon, children, type) {
  return {
    key,
    icon,
    children,
    label,
    type
  };
}

const NavigationMenu = ({userRole}) => {
  const items = userRole === USER_ROLE.ADMIN ?
    [
      getItem(MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.LABEL, MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.KEY, <TbShoppingBag style={{fontSize:"20px", marginBottom:"1px"}}/>, [
        getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.SUB.PRODUCT_LIST}</span>, ROUTERS.BUSINESS.PRODUCT_LIST),
        getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.SUB.ADD_PRODUCT}</span>, ROUTERS.BUSINESS.ADD_PRODUCT),
      ]),
      getItem(MANAGEMENT_PAGE.SALES_MANAGEMENT.LABEL, MANAGEMENT_PAGE.SALES_MANAGEMENT.KEY, <RiShoppingCart2Line style={{fontSize:"20px", marginBottom:"1px"}}/>, [
        getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.SALES_MANAGEMENT.SUB.ORDER_LIST}</span>,  ROUTERS.BUSINESS.ORDER_LIST),
      ]),
      getItem(MANAGEMENT_PAGE.CATEGORY_MANAGEMENT.LABEL, MANAGEMENT_PAGE.CATEGORY_MANAGEMENT.KEY, <MdMenu style={{fontSize:"20px", marginBottom:"1px"}}/>, [
        getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.CATEGORY_MANAGEMENT.SUB.CATEGORY_LIST}</span>, ROUTERS.ADMIN.CATEGORY_LIST),
      ]),
      // getItem(MANAGEMENT_PAGE.ACCOUNT_MANAGEMENT.LABEL, MANAGEMENT_PAGE.ACCOUNT_MANAGEMENT.KEY, <FaRegUser style={{fontSize:"18px", marginBottom:"1px"}}/>, [
      //   getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.ACCOUNT_MANAGEMENT.SUB.ACCOUNT_LIST}</span>,  ROUTERS.ADMIN.ACCOUNT_LIST),
      //   getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.ACCOUNT_MANAGEMENT.SUB.ADD_ACCOUNT}</span>,  ROUTERS.ADMIN.ADD_ACCOUNT),
      // ]),


    ]
    : (userRole === USER_ROLE.BUSINESS ?
      [
        getItem(MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.LABEL, MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.KEY, <TbShoppingBag style={{fontSize:"20px", marginBottom:"1px"}}/>, [
          getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.SUB.PRODUCT_LIST}</span>, ROUTERS.BUSINESS.PRODUCT_LIST),
          getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.SUB.ADD_PRODUCT}</span>, ROUTERS.BUSINESS.ADD_PRODUCT),
        ]),
        getItem(MANAGEMENT_PAGE.SALES_MANAGEMENT.LABEL, MANAGEMENT_PAGE.SALES_MANAGEMENT.KEY, <RiShoppingCart2Line style={{fontSize:"20px", marginBottom:"1px"}}/>, [
          getItem(<span style={{margin: "0 7px 0 7px"}}>{MANAGEMENT_PAGE.SALES_MANAGEMENT.SUB.ORDER_LIST}</span>,  ROUTERS.BUSINESS.ORDER_LIST),
        ]),

      ]
      : (userRole === USER_ROLE.CARRIER &&
        [

        ]
      )
    )


  const location = useLocation();
  const navigate = useNavigate();

  const COMPONENT_COUNT = 3;

  const [selectedKeys, setSelectedKeys] = useState([]);
  const [openKeys, setOpenKeys] = useState([]);

  const findParentKey = (targetKey) => {
    const findItem = items.find(
      (item) => item.children && item.children.some((child) => child.key === targetKey)
    );
    return findItem ? findItem.key : null;
  };

  useEffect(() => {
    const parts = location.pathname.split('/');
    const pageName = parts.length === COMPONENT_COUNT ? parts[parts.length - 1] : "";

    if (findParentKey(pageName) === null) {
      setSelectedKeys([]);
      return;
    }
    setSelectedKeys([pageName]);

    setOpenKeys((prevOpenKeys) => {
      if (!prevOpenKeys.includes(pageName)) {
        return [...prevOpenKeys, findParentKey(pageName)];
      }
    });

  }, [location]);

  const onClick = (e) => {
    navigate('/management-page/' + e.key, {
      state: { scrolling: SCROLLING.SMOOTH },
    });
  }

  const onOpenChange = (e) => {
    setOpenKeys(e);
  }

  return (
    <ConfigProvider
      theme={{
        components: {
          Menu: {
            horizontalItemSelectedColor: '#294780',
            horizontalItemSelectedBg: '#294780',
            itemSelectedColor: '#294780',
            itemActiveBg: '#e9edfb',
            itemSelectedBg: '#dadfff',
          },
        },
      }}
    >
      <Menu
        onClick={(e) => onClick(e)}
        onOpenChange={(e) => onOpenChange(e)}
        style={{
          width: 325,
          marginTop:20,
          marginBottom:15,
          fontWeight:600,
        }}
        selectedKeys={selectedKeys}
        openKeys={openKeys}
        mode="inline"
        items={items}
      />
    </ConfigProvider>

  );
}

export default NavigationMenu;