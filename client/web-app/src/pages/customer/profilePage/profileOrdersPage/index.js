import React, {useEffect, useState} from 'react';
import './style.scss';

import {useCookies} from "react-cookie";
import {Link, useLocation} from "react-router-dom";

import queryString from "query-string";
import {ConfigProvider, Popconfirm, Tooltip} from "antd";

import {API, TOOLTIP, IMAGE_URL, POPCONFIRM, PROFILE_PAGE, TAB_LIST_ITEMS, TAB_LIST_TEXT} from "@Const";
import {formatter, convertDateTimeFormat, isSubstringIgnoreCaseAndAccents} from "@Util/util";
import LoadingPage from "../../../loading/loadingPage";

const ordersData = [
  {
    "orderID": 19,
    "orderDate": "2024-05-17T17:40:58.502+00:00",
    "totalAmount": 249000,
    "orderStatus": "Đã xác nhận",
    "userID": 4,
    "recipientName": "Bùi Minh Hoạt",
    "recipientPhone": "0912345678",
    "addressDetails": "144 Xuân Thuỷ, Cầu Giấy, Hà Nội",
    "orderDetails": [
      {
        "orderDetailID": 32,
        "orderID": 19,
        "productID": 63,
        "productName": "Áo Thun Dài Tay Nam, Mềm Mịn, Thoáng Khí",
        "imagePath": "https://iili.io/JR4LT5G.jpg",
        "sizeName": "M",
        "productPrice": 249000,
        "quantity": 1,
        "totalPrice": 249000
      }
    ],
    "fullName": "Bùi Minh Hoạt",
    "avatarPath": "https://iili.io/J5HNvHP.jpg"
  },
  {
    "orderID": 20,
    "orderDate": "2024-05-17T17:47:22.367+00:00",
    "totalAmount": 1187000,
    "orderStatus": "Chờ xác nhận",
    "userID": 4,
    "recipientName": "Bùi Minh Hoạt",
    "recipientPhone": "0912345678",
    "addressDetails": "144 Xuân Thuỷ, Cầu Giấy, Hà Nội",
    "orderDetails": [
      {
        "orderDetailID": 33,
        "orderID": 20,
        "productID": 70,
        "productName": "Áo Thun Dài Tay Nam, Mềm Mịn, Thoáng Khí",
        "imagePath": "https://iili.io/JR4Dqps.jpg",
        "sizeName": "M",
        "productPrice": 249000,
        "quantity": 1,
        "totalPrice": 249000
      },
      {
        "orderDetailID": 34,
        "orderID": 20,
        "productID": 91,
        "productName": "Quần Short Thể Thao Nam, Cạp Cúc, In Sườn",
        "imagePath": "https://iili.io/JRLZkCb.jpg",
        "sizeName": "XL",
        "productPrice": 469000,
        "quantity": 2,
        "totalPrice": 938000
      }
    ],
    "fullName": "Bùi Minh Hoạt",
    "avatarPath": "https://iili.io/J5HNvHP.jpg"
  },
  {
    "orderID": 21,
    "orderDate": "2024-05-17T17:48:20.760+00:00",
    "totalAmount": 799000,
    "orderStatus": "Hoàn thành",
    "userID": 4,
    "recipientName": "Bùi Minh Hoạt",
    "recipientPhone": "0912345678",
    "addressDetails": "144 Xuân Thuỷ, Cầu Giấy, Hà Nội",
    "orderDetails": [
      {
        "orderDetailID": 35,
        "orderID": 21,
        "productID": 46,
        "productName": "Áo Khoác GIó Nam, Cản Gió, Trượt Nước",
        "imagePath": "https://iili.io/JR4pqyF.jpg",
        "sizeName": "L",
        "productPrice": 799000,
        "quantity": 1,
        "totalPrice": 799000
      }
    ],
    "fullName": "Bùi Minh Hoạt",
    "avatarPath": "https://iili.io/J5HNvHP.jpg"
  },
  {
    "orderID": 22,
    "orderDate": "2024-05-17T17:48:49.382+00:00",
    "totalAmount": 2596000,
    "orderStatus": "Chờ xác nhận",
    "userID": 4,
    "recipientName": "Bùi Minh Hoạt",
    "recipientPhone": "0912345678",
    "addressDetails": "144 Xuân Thuỷ, Cầu Giấy, Hà Nội",
    "orderDetails": [
      {
        "orderDetailID": 36,
        "orderID": 22,
        "productID": 108,
        "productName": "Quần Kaki Dài Nam, Đứng Phom, Tôn Dáng",
        "imagePath": "https://iili.io/JRLylZ7.jpg",
        "sizeName": "2XL",
        "productPrice": 649000,
        "quantity": 4,
        "totalPrice": 2596000
      }
    ],
    "fullName": "Bùi Minh Hoạt",
    "avatarPath": "https://iili.io/J5HNvHP.jpg"
  },
  {
    "orderID": 23,
    "orderDate": "2024-05-17T17:57:34.241+00:00",
    "totalAmount": 3386000,
    "orderStatus": "Đang giao hàng",
    "userID": 4,
    "recipientName": "Bùi Minh Hoạt",
    "recipientPhone": "0912345678",
    "addressDetails": "144 Xuân Thuỷ, Cầu Giấy, Hà Nội",
    "orderDetails": [
      {
        "orderDetailID": 37,
        "orderID": 23,
        "productID": 108,
        "productName": "Quần Kaki Dài Nam, Đứng Phom, Tôn Dáng",
        "imagePath": "https://iili.io/JRLylZ7.jpg",
        "sizeName": "S",
        "productPrice": 649000,
        "quantity": 1,
        "totalPrice": 649000
      },
      {
        "orderDetailID": 38,
        "orderID": 23,
        "productID": 49,
        "productName": "Áo Khoác Bomber Nam, Cản Gió, Thiết Kế Trẻ Trung",
        "imagePath": "https://iili.io/JR4yBl2.jpg",
        "sizeName": "M",
        "productPrice": 939000,
        "quantity": 1,
        "totalPrice": 939000
      },
      {
        "orderDetailID": 39,
        "orderID": 23,
        "productID": 50,
        "productName": " Áo Khoác Bomber Nam, Lót Lông, Thiết Kế Trẻ Trung",
        "imagePath": "https://iili.io/JR4yMxa.jpg",
        "sizeName": "XL",
        "productPrice": 899000,
        "quantity": 2,
        "totalPrice": 1798000
      }
    ],
    "fullName": "Bùi Minh Hoạt",
    "avatarPath": "https://iili.io/J5HNvHP.jpg"
  }
];

const TabList = ({openTab, setOpenTab}) => {
  return (
    <div className="nav nav-tabs menu-tab" id="myTab" role="tablist">
      {
        TAB_LIST_ITEMS.map((tab, index) => (
          <button
            key={tab.text}
            className={`nav-link ${openTab === tab.text ? "active" : ""}`}
            style={{marginBottom:"0px"}}
            role="tab"
            tabIndex={(openTab === tab.text) ? 0 : -1}
            onClick={() => setOpenTab(tab.text)}
          >
            {tab.text}
          </button>
        ))
      }
    </div>
  );
}

const TabContent = ({openTab, setOpenTab}) => {
  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;

  const location = useLocation();
  const queryParams = queryString.parse(location.search);
  const [userID, setUserID] = useState(queryParams.userID);

  const [orderList, setOrderList] = useState([])
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setOrderList(ordersData);
    if (openTab !==  TAB_LIST_TEXT.ALL) {
      setOrderList(
        ordersData.filter((order) =>
          isSubstringIgnoreCaseAndAccents(openTab, order.orderStatus)
        )
      );
    }
  }, [openTab]);

  return (
    <div>
      {orderList && orderList.length ? (
        <>
          {orderList.map((order, index) => (
            <div key={index} className="order-item-wrap show-detail">
              <div className="header-wrap">
                <div className="code-wrap">
                  {PROFILE_PAGE.PROFILE_ORDERS_PAGE.ORDER_ID}{" "}
                  <span className="code">{order.orderID}</span>
                </div>
                <div className="status-wrap">

                  <Tooltip placement="bottom" title={TOOLTIP.ORDER_TIME} color={"#989aaa"}>
                    <p className="date">{convertDateTimeFormat(order.orderDate)}</p>
                  </Tooltip>


                  {order.orderStatus === TAB_LIST_TEXT.PENDING_CONFIRMATION &&
                    <div className="status status-un-paid"
                         style={{backgroundColor:"#ffe39d"}}
                    >{order.orderStatus}</div>
                  }
                  { order.orderStatus === TAB_LIST_TEXT.CONFIRMED &&
                    <div className="status status-un-paid"
                         style={{backgroundColor:"#b5efa3"}}
                    >{order.orderStatus}</div>
                  }
                  { order.orderStatus === TAB_LIST_TEXT.IN_TRANSIT &&
                    <div className="status status-un-paid"
                         style={{backgroundColor:"#baf6f8"}}
                    >{order.orderStatus}</div>
                  }
                  { order.orderStatus === TAB_LIST_TEXT.COMPLETED &&
                    <div className="status status-un-paid"
                         style={{backgroundColor:"#2fad0c"}}
                    ><span style={{color:"white"}}>{order.orderStatus}</span></div>
                  }
                  { order.orderStatus === TAB_LIST_TEXT.CANCELLED &&
                    <div className="status status-un-paid"
                         style={{backgroundColor:"#294780"}}
                    ><span style={{color:"white"}}>{order.orderStatus}</span></div>
                  }

                </div>
              </div>
              <div className="content-wrap">
                {order.orderDetails &&
                  order.orderDetails.map((orderDetail, index) => (
                    <div key={index} className="product-wrap">
                      <div className="img-wrap">
                        <img src={orderDetail.imagePath} alt={""} />
                      </div>
                      <div className="info-wrap">
                        <Link to={"/product?productID=" + orderDetail.productID}>
                          <div className="name" style={{color:"black"}}>{orderDetail.productName}</div>
                        </Link>

                        <div className="property-wrap">
                          <span>{PROFILE_PAGE.PROFILE_ORDERS_PAGE.QUANTITY} {orderDetail.quantity}</span>
                        </div>
                        <div className="money-wrap">
                          <span>{formatter(orderDetail.totalPrice)}</span>
                        </div>
                      </div>
                    </div>
                  ))}
              </div>
              <div className="total-wrap">
                <div className="total-money">
                  {PROFILE_PAGE.PROFILE_ORDERS_PAGE.TOTAL_AMOUNT}
                  <span className="money">&nbsp; {formatter(order.totalAmount)}</span>
                </div>

                {order.orderStatus === TAB_LIST_TEXT.PENDING_CONFIRMATION && (
                  <ConfigProvider
                    button={{
                      style: { width: 70, margin: 4 },
                    }}
                    theme={{
                      components: {
                        Button: {
                          colorPrimary: '#294780',
                          colorPrimaryHover: '#365ddc',
                          colorPrimaryActive: '#0a31b2',
                          primaryShadow: '0 2px 0 #e6edff',
                        },
                      },
                    }}
                  >
                    <Popconfirm
                      placement="top"
                      title={POPCONFIRM.CONFIRM_CANCEL_ORDER}
                      okText={<div>{POPCONFIRM.YES}</div>}
                      cancelText={<div>{POPCONFIRM.NO}</div>}
                    >
                      <button className="cancel-order" style={{color:"#BD0002"}}>{PROFILE_PAGE.PROFILE_ORDERS_PAGE.CANCEL_ORDER}</button>
                    </Popconfirm>
                  </ConfigProvider>
                )}
              </div>

              <div className="detail-wrap show-detail">
                <div className="content-detail-wrap">
                  <div className="info-order-wrap">
                    <div className="row item-info">
                      <div className="col-3 label-wrap">{PROFILE_PAGE.PROFILE_ORDERS_PAGE.PAYMENT_METHOD}</div>
                      <div className="col-9 text-wrap">{PROFILE_PAGE.PROFILE_ORDERS_PAGE.CASH_ON_DELIVERY}</div>
                    </div>
                    <div className="row item-info">
                      <div className="col-3 label-wrap">{PROFILE_PAGE.PROFILE_ORDERS_PAGE.SHIPPING_ADDRESS}</div>
                      <div className="col-9 text-wrap">
                        <div className="information">
                          <span className="name">{order.recipientName}</span>
                          <div className="break-item">|</div>
                          <span className="phone">{order.recipientPhone}</span>
                        </div>
                        <div>
                          <span>{order.addressDetails}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </>
      ) : (
        <div className={`tab-pane show`} role="tabpanel">
          <div className="empty-content">
            <img src={IMAGE_URL.EMPTY_PRODUCT_IMG} alt="no data" />
            <p>{PROFILE_PAGE.PROFILE_ORDERS_PAGE.NO_ORDERS}</p>
          </div>
        </div>
      )}
    </div>
  );
}

const ProfileOrdersPage = () => {
  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;

  const [openTab, setOpenTab] = useState(TAB_LIST_TEXT.ALL);

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

  return ( !isLoading ?
      <div className="col-8 content-children item-row">
        <div className="order-wrap">
          <TabList openTab={openTab} setOpenTab={setOpenTab} />

          <div className="order-list">
            <div className="tab-content clearfix" id="nav-tabContent">
              <TabContent openTab={openTab} setOpenTab={setOpenTab} />
            </div>
          </div>
        </div>
      </div>
      :
      <LoadingPage />
  );
}

export default ProfileOrdersPage;