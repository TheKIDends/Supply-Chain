import React, {useEffect, useState} from 'react';
import "./style.scss";
import {useCookies} from "react-cookie";
import {Link, useNavigate} from "react-router-dom";
import {toast} from "react-toastify";


import EditOrderStatusDialog from "./dialogs/EditOrderStatusDialog/EditOrderStatusDialog";
import {convertDateTimeFormat, isSubstringIgnoreCaseAndAccents, formatter} from '@Util/util';

import {TbListSearch} from "react-icons/tb";
import {BiSolidEdit} from "react-icons/bi";

import { DatePicker } from 'antd';
import {ConfigProvider, Select} from "antd";
import locale from 'antd/locale/vi_VN';
import dayjs from 'dayjs';
import advancedFormat from 'dayjs/plugin/advancedFormat'
import customParseFormat from 'dayjs/plugin/customParseFormat'
import localeData from 'dayjs/plugin/localeData'
import weekday from 'dayjs/plugin/weekday'
import weekOfYear from 'dayjs/plugin/weekOfYear'
import weekYear from 'dayjs/plugin/weekYear'
import viLocale from 'dayjs/locale/vi';
import {
    API, BREADCRUMB,
    DATE_PICKER,
    IMAGE_URL,
    MESSAGE,
    ORDER_LIST_PAGE, SEARCH,
    TAB_LIST_ITEMS,
    TAB_LIST_TEXT,
    MANAGEMENT_PAGE
} from "@Const";


const { RangePicker } = DatePicker;

const ordersData = [
    {
        "orderID": 19,
        "orderDate": "2024-05-17T17:40:58.502+00:00",
        "totalAmount": 249000,
        "orderStatus": "Đã xác nhận",
        "userID": 4,
        "recipientName": "Nguyễn Châu Khanh",
        "recipientPhone": "0912345678",
        "addressDetails": "144 Xuân Thuỷ, Cầu Giấy, Hà Nội",
        "orderDetails": [
            {
                "orderDetailID": 32,
                "orderID": 19,
                "productID": 63,
                "productName": "Táo gala [1Kg]",
                "imagePath": "https://login.medlatec.vn//ImagePath/images/20221028/20221028_qua-tao-3.png",
                "sizeName": "M",
                "productPrice": 249000,
                "quantity": 1,
                "totalPrice": 249000
            }
        ],
        "fullName": "Nguyễn Châu Khanh",
        "avatarPath": "https://w7.pngwing.com/pngs/577/307/png-transparent-human-with-circle-logo-national-cyber-security-alliance-organization-drupal-association-information-internet-icon-s-customers-free-miscellaneous-company-logo.png"
    },
    {
        "orderID": 20,
        "orderDate": "2024-05-17T17:47:22.367+00:00",
        "totalAmount": 1187000,
        "orderStatus": "Chờ xác nhận",
        "userID": 4,
        "recipientName": "Nguyễn Châu Khanh",
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
        "fullName": "Nguyễn Châu Khanh",
        "avatarPath": "https://w7.pngwing.com/pngs/577/307/png-transparent-human-with-circle-logo-national-cyber-security-alliance-organization-drupal-association-information-internet-icon-s-customers-free-miscellaneous-company-logo.png"
    },
    {
        "orderID": 21,
        "orderDate": "2024-05-17T17:48:20.760+00:00",
        "totalAmount": 799000,
        "orderStatus": "Hoàn thành",
        "userID": 4,
        "recipientName": "Nguyễn Châu Khanh",
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
        "fullName": "Nguyễn Châu Khanh",
        "avatarPath": "https://w7.pngwing.com/pngs/577/307/png-transparent-human-with-circle-logo-national-cyber-security-alliance-organization-drupal-association-information-internet-icon-s-customers-free-miscellaneous-company-logo.png"
    },
    {
        "orderID": 22,
        "orderDate": "2024-05-17T17:48:49.382+00:00",
        "totalAmount": 2596000,
        "orderStatus": "Chờ xác nhận",
        "userID": 4,
        "recipientName": "Nguyễn Châu Khanh",
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
        "fullName": "Nguyễn Châu Khanh",
        "avatarPath": "https://w7.pngwing.com/pngs/577/307/png-transparent-human-with-circle-logo-national-cyber-security-alliance-organization-drupal-association-information-internet-icon-s-customers-free-miscellaneous-company-logo.png"
    },
    {
        "orderID": 23,
        "orderDate": "2024-05-17T17:57:34.241+00:00",
        "totalAmount": 3386000,
        "orderStatus": "Đang giao hàng",
        "userID": 4,
        "recipientName": "Nguyễn Châu Khanh",
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
        "fullName": "Nguyễn Châu Khanh",
        "avatarPath": "https://w7.pngwing.com/pngs/577/307/png-transparent-human-with-circle-logo-national-cyber-security-alliance-organization-drupal-association-information-internet-icon-s-customers-free-miscellaneous-company-logo.png"
    }
];

const initialDates = [dayjs("2024-05-17T19:45:43.173Z"), dayjs("2024-05-17T19:45:43.173Z")];

const TabList = ({openTab, setOpenTab}) => {
    return (
        <div className="nav nav-tabs menu-tab" id="myTab" role="tablist"
             style={{boxShadow: "0 1px 4px 0 rgba(0, 0, 0, 0.102)", borderRadius:"3px"}}
        >
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

const TabContent = ({openTab, setOpenTab, orderList, reloadOrderListPage}) => {
    const [editingOrderStatus, setEditingOrderStatus] = useState(null);

    const navigate = useNavigate();

    const handleAcceptEditOrderStatus = () => {
        reloadOrderListPage();
        setEditingOrderStatus(null);
    }

    return (
        <>
            { orderList && orderList.filter((order) => openTab === TAB_LIST_TEXT.ALL || order.orderStatus === openTab).length ?
                <>
                    { orderList.map((order, index) => (
                        <div key = {index}>
                            { (openTab === TAB_LIST_TEXT.ALL || order.orderStatus === openTab) &&
                                <div key = {index}
                                     className="order-item-wrap show-detail"
                                     style={{boxShadow: "0 1px 4px 0 rgba(0, 0, 0, 0.102)", borderRadius:"3px"}}
                                >
                                    <div className="header-wrap" style={{padding:"10px 17px 10px 17px"}}>
                                        <div className="code-wrap" style={{display:"flex", alignItems:"center"}}>
                                            <span>{ORDER_LIST_PAGE.ORDER_ID} <span className="code">{order.orderID}</span> </span>
                                        </div>
                                        {/*<div className="avatar-hover pointer-cursor"*/}
                                        {/*     style={{display:"flex", alignItems:"center"}}*/}
                                        {/*>*/}
                                        {/*    <Link to={`/profile/orders?userID=${order.userID}`}>{order.fullName}</Link>*/}
                                        {/*    <div style={{marginLeft:"10px", border:"1px solid #F5F5F5", borderRadius:"100%"}}>*/}
                                        {/*        <img*/}
                                        {/*            className="img-avatar"*/}
                                        {/*            src={order.avatarPath ? order.avatarPath : IMAGE_URL.DEFAULT_AVATAR_IMG}*/}
                                        {/*            alt=""*/}
                                        {/*            onClick={() => {*/}
                                        {/*                navigate(`/profile/orders?userID=${order.userID}`)*/}
                                        {/*            }}*/}
                                        {/*        />*/}
                                        {/*    </div>*/}
                                        {/*</div>*/}
                                    </div>

                                    <div className="content-wrap">
                                        { order.orderDetails &&
                                            order.orderDetails.map((orderDetail, index) => (
                                                <div key = {index} className="product-wrap">
                                                    <div className="img-wrap">
                                                        <img
                                                            src={orderDetail.imagePath}
                                                            alt={orderDetail.productName}/>
                                                    </div>
                                                    <div className="info-wrap">
                                                        <div>
                                                            <div className="name">{orderDetail.productName}</div>
                                                        </div>
                                                        <div className="property-wrap">
                                                            {/*<span>{ORDER_LIST_PAGE.SIZE} {orderDetail.sizeName}</span>*/}
                                                        </div>
                                                        <div className="property-wrap">
                                                            <span>{ORDER_LIST_PAGE.QUANTITY} {orderDetail.quantity}</span>
                                                        </div>
                                                        <div className="money-wrap">
                                                            <span>{formatter(orderDetail.totalPrice)}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            ))
                                        }

                                    </div>
                                    <div className="total-wrap">
                                        <div className="total-money">
                                            {ORDER_LIST_PAGE.TOTAL_AMOUNT}
                                            <span className="money">&nbsp; {formatter(order.totalAmount)}</span>
                                        </div>

                                        <div className="header-wrap" style={{borderBottom:"0", padding:"7px 0 7px 0"}}>
                                            <div className="status-wrap">
                                                <p className="date">{convertDateTimeFormat(order.orderDate)}</p>

                                                <div style={{display:"flex", alignItems:"center"}}>

                                                    { order.orderStatus === TAB_LIST_TEXT.PENDING_CONFIRMATION &&
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


                                                    <BiSolidEdit style={{fontSize:"21px", color:"#7B7D85", marginLeft:"7px", cursor:"pointer"}}
                                                                 onClick={() => {
                                                                     setEditingOrderStatus({
                                                                         orderID: order.orderID,
                                                                         orderStatus: order.orderStatus,
                                                                     });
                                                                 }}
                                                    />
                                                </div>

                                            </div>
                                        </div>

                                    </div>
                                    <div className="detail-wrap show-detail">
                                        <div className="content-detail-wrap">
                                            <div className="info-order-wrap">
                                                <div className="row item-info">
                                                    <div className="col-3 label-wrap">{ORDER_LIST_PAGE.PAYMENT_METHOD_LABEL}</div>
                                                    <div className="col-9 text-wrap">{ORDER_LIST_PAGE.PAYMENT_METHOD}</div>
                                                </div>
                                                <div className="row item-info">
                                                    <div className="col-3 label-wrap">{ORDER_LIST_PAGE.SHIPPING_ADDRESS_LABEL}</div>
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
                            }
                        </div>
                    ))}
                    { editingOrderStatus && (
                        <div className="modal-overlay">
                            <EditOrderStatusDialog orderID={editingOrderStatus.orderID}
                                                   orderStatus={editingOrderStatus.orderStatus}
                                                   onAccept={handleAcceptEditOrderStatus}
                                                   onClose={() => {setEditingOrderStatus(null)}}/>
                        </div>
                    )}
                </>
                :
                <div className={`tab-pane show`} role="tabpanel" style={{boxShadow: "0 1px 4px 0 rgba(0, 0, 0, 0.102)", borderRadius:"3px"}}>
                    <div className="empty-content">
                        <img src={IMAGE_URL.EMPTY_PRODUCT_IMG} alt="no data"/>
                        <p>{ORDER_LIST_PAGE.NO_ORDERS}</p>
                    </div>
                </div>
            }
        </>
    );
}

const OrderListPage = () => {
    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const [isStart, setIsStart] = useState(true);

    const [orderList, setOrderList] = useState([])
    const [openTab, setOpenTab] = useState(TAB_LIST_TEXT.ALL);
    const [selectedSearch, setSelectedSearch] = useState(SEARCH.ORDER.VALUE.ORDER_DATE);
    const [phoneNumberValue, setPhoneNumberValue] = useState("");
    const [orderIDValue, setOrderIDValue] = useState("");

    const [dates, setDates] = useState(null);
    const [value, setValue] = useState(null);

    const disabledDate = (current) => {
        if (!dates) {
            return false;
        }
        const tooLate = dates[0] && current.diff(dates[0], 'days') >= DATE_PICKER.MAX_DAY_DISTANCE;
        const tooEarly = dates[1] && dates[1].diff(current, 'days') >= DATE_PICKER.MAX_DAY_DISTANCE;
        return !!tooEarly || !!tooLate;
    };

    const onOpenChange = (open) => {
        if (open) {
            setDates([null, null]);
        } else {
            setDates(null);
        }
    }

    const handleBtnSearchClick = () => {
        switch (selectedSearch) {
            case SEARCH.ORDER.VALUE.ORDER_DATE:
                setOrderList(ordersData);
                setOpenTab(TAB_LIST_TEXT.ALL);
                break;
            case SEARCH.ORDER.VALUE.PHONE_NUMBER:
                setOrderList(
                    ordersData.filter((order) =>
                        isSubstringIgnoreCaseAndAccents(phoneNumberValue, order.recipientPhone)
                    )
                );
                setOpenTab(TAB_LIST_TEXT.ALL);
                break;
            case SEARCH.ORDER.VALUE.ORDER_ID:
                setOrderList(
                    ordersData.filter((order) => orderIDValue == order.orderID)
                );
                setOpenTab(TAB_LIST_TEXT.ALL);
                break;
        }
    }

    const reloadOrderListPage = async () => {
        setIsStart(true);
        setOpenTab(TAB_LIST_TEXT.ALL);
        setSelectedSearch(SEARCH.ORDER.VALUE.ORDER_DATE);
        setPhoneNumberValue("");
        setOrderIDValue("");

        setValue(initialDates);
    }

    useEffect(() => {
        dayjs.extend(customParseFormat)
        dayjs.extend(advancedFormat)
        dayjs.extend(weekday)
        dayjs.extend(localeData)
        dayjs.extend(weekOfYear)
        dayjs.extend(weekYear)
        dayjs.locale(viLocale);

        setValue(initialDates);
    }, []);

    useEffect(() => {
        if (value && isStart) {
            setIsStart(false);
            setOrderList(ordersData);
            setOpenTab(TAB_LIST_TEXT.ALL);
        }
    }, [value]);

    return (
        <div id="app">
            <main id="main">
                <div className="container profile-wrap">
                    <div className="breadcrumb-wrap">
                        <a href="/">{BREADCRUMB.HOME_PAGE}</a>
                        &gt; <span>{MANAGEMENT_PAGE.SALES_MANAGEMENT.LABEL}</span>
                        &gt; <span>{MANAGEMENT_PAGE.SALES_MANAGEMENT.SUB.ORDER_LIST}</span>
                    </div>
                </div>

                <div style={{padding:"0 47px 0 47px", width:"100%"}}>
                    <div style={{boxShadow: "0px 1px 4px 0 rgba(0, 0, 0, 0.102)", marginBottom:"10px",
                        borderRadius:"3px", padding:"0", backgroundColor:"#fff", height:"75px"}}
                    >
                        <div style={{display:"flex", alignItems:"center", justifyContent:"space-between", height:"100%", paddingLeft:"35px"}}>
                            <div style={{display:"flex", color:"#333333", fontSize:"18px", fontWeight:"800", marginTop:"7px", alignItems:"center"}}>
                                <TbListSearch style={{padding:"0 0 2px", fontSize:"28px", marginRight:"10px"}}/>
                                <span>{ORDER_LIST_PAGE.SEARCH_BY}</span>
                                <ConfigProvider
                                    theme={{
                                        components: {
                                            Select: {
                                                controlItemBgActive: '#e5e9ff',
                                            },
                                        },
                                    }}
                                >
                                    <Select
                                        defaultValue={SEARCH.ORDER.VALUE.ORDER_DATE}
                                        style={{ width: 230 }}
                                        bordered={false}
                                        size={"large"}
                                        options={[
                                            { value: SEARCH.ORDER.VALUE.ORDER_DATE, label: SEARCH.ORDER.LABEL.ORDER_DATE },
                                            { value: SEARCH.ORDER.VALUE.PHONE_NUMBER, label: SEARCH.ORDER.LABEL.PHONE_NUMBER },
                                            { value: SEARCH.ORDER.VALUE.ORDER_ID, label: SEARCH.ORDER.LABEL.ORDER_ID },
                                        ]}
                                        onChange={(value) => {
                                            setSelectedSearch(value);
                                            setValue(initialDates);
                                            setPhoneNumberValue("");
                                            setOrderIDValue("");
                                            setOrderList(ordersData);
                                            setOpenTab(TAB_LIST_TEXT.ALL);
                                        }}
                                    />
                                </ConfigProvider>

                            </div>

                            <div style={{display:"flex", alignItems:"center", justifyContent:"space-between", marginRight:"35px"}}>
                                <div style={{display:"flex", alignItems:"center", height:"35px", width:"400px"}}>
                                    { selectedSearch === SEARCH.ORDER.VALUE.ORDER_DATE &&
                                        <ConfigProvider
                                            locale={locale}
                                            theme={{
                                                components: {
                                                    DatePicker: {
                                                        hoverBorderColor: '#B7B7B7',
                                                        activeBorderColor: '#d98c8c',
                                                        colorPrimary: '#c94a4a',
                                                        colorPrimaryBorder: '#d98c8c',
                                                        controlItemBgActive: '#e5e9ff',
                                                        activeShadow: 'none',
                                                        colorBorder: '#E5E5E5',
                                                        borderRadius:'3px',
                                                        fontSize:'14',
                                                        fontSizeLG:'14',
                                                        colorTextPlaceholder:'#B7B7B7',
                                                    },
                                                    Button: {
                                                        colorPrimary: '#294780',
                                                        colorPrimaryHover: '#dc3636',
                                                        colorPrimaryActive: '#b20a0a',
                                                        primaryShadow: '0 2px 0 #e5e9ff',
                                                    },
                                                },
                                            }}
                                        >
                                            <RangePicker
                                                value={dates || value}
                                                format="DD-MM-YYYY"
                                                size="large"

                                                style={{ width: "100%", height:"100%", pointerEvents: "none", cursor: "not-allowed" }}
                                                disabledDate={disabledDate}
                                                onCalendarChange={(val) => {
                                                    setDates(val);
                                                }}
                                                onChange={(val) => {
                                                    setValue(initialDates);
                                                }}
                                                onOpenChange={onOpenChange}
                                                changeOnBlur
                                            />
                                        </ConfigProvider>
                                    }
                                    { selectedSearch === SEARCH.ORDER.VALUE.PHONE_NUMBER &&
                                        <div style={{padding:"0", width:"100%", height: "35px", display:"flex", alignItems:"center"}}
                                             className="supply-chain-input__inner "
                                        >
                                            <input
                                                type="text" placeholder={ORDER_LIST_PAGE.PHONE_NUMBER_PLACEHOLDER}
                                                style={{ padding: "0 12px 0 12px", borderRadius: "3px" }}
                                                className="supply-chain-input__input"
                                                value={phoneNumberValue}
                                                onChange={(e) => {
                                                    if (!isNaN(e.target.value)) setPhoneNumberValue(e.target.value);
                                                }}
                                            />
                                        </div>
                                    }
                                    { selectedSearch === SEARCH.ORDER.VALUE.ORDER_ID &&
                                        <div style={{padding:"0", width:"100%", height: "35px", display:"flex", alignItems:"center"}}
                                             className="supply-chain-input__inner "
                                        >
                                            <input
                                                type="text" placeholder={ORDER_LIST_PAGE.ORDER_ID_PLACEHOLDER}
                                                style={{ padding: "0 12px 0 12px", borderRadius: "3px" }}
                                                className="supply-chain-input__input"
                                                value={orderIDValue}
                                                onChange={(e) => {
                                                    if (!isNaN(e.target.value)) setOrderIDValue(e.target.value);
                                                }}
                                            />
                                        </div>
                                    }
                                </div>
                                <button type="button" className="search-btn" onClick={handleBtnSearchClick}>{ORDER_LIST_PAGE.SEARCH_BTN}</button>
                            </div>

                        </div>
                    </div>
                </div>

                <div className="col-8 content-children item-row"
                     style={{padding:"0 47px 0 47px", width:"100%"}}
                >
                    <div className="order-wrap">
                        <TabList openTab={openTab} setOpenTab={setOpenTab} />
                        <div className="order-list">
                            <div className="tab-content clearfix" id="nav-tabContent">
                                <TabContent openTab={openTab}
                                            setOpenTab={setOpenTab}
                                            orderList={orderList}
                                            reloadOrderListPage={reloadOrderListPage}
                                />
                            </div>
                        </div>
                    </div>
                </div>

            </main>
        </div>
    );
};

export default OrderListPage;
