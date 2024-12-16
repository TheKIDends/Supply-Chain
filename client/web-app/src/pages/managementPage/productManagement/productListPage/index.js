import React, {useEffect, useState} from "react";
import "./style.scss"

import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import {
    MESSAGE,
    MANAGEMENT_PAGE,
    BREADCRUMB
} from "@Const";

import {Space, Table, Tag} from "antd";
import Column from "antd/lib/table/Column";
import {toast} from "react-toastify";
import {get} from "axios";

const ProductListPage  = () => {
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);

    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const [loading, setLoading] = useState(true);


    const getProduct = async () => {

        const apiUrl = "http://localhost:8000/api/product/get-all-products";
        try {
            const response = await fetch(apiUrl, {
                method: "GET",
                headers: {
                    // "Authorization": `Bearer ${refreshToken}`,
                    "Content-Type": "application/json"
                },
            });
            if (response.ok) {
                const result = await response.json();

                const updatedProducts = result.map(product => ({
                    ...product,
                    tags: ['Chờ phê duyệt']
                }));
                setProducts(updatedProducts);
                setLoading(false);

            } else {
                toast.error(MESSAGE.GENERIC_ERROR)
                console.error(response);
            }
        } catch (error) {
            toast.error(MESSAGE.GENERIC_ERROR)
            console.error(error.message);
        }
    }

    useEffect(() => {
        getProduct().then(r => {});
    }, []);

    return (
        <div id="app">
            <main id="main">
                <div className="container profile-wrap">
                    <div className="breadcrumb-wrap">
                        <a href="/">{BREADCRUMB.HOME_PAGE}</a>
                        &gt; <span>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.LABEL}</span>
                        &gt; <span>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.SUB.PRODUCT_LIST}</span>
                    </div>
                </div>
                <div className="container">
                    <div className="list-table" style={{margin: "13px 50px 20px 38px"}}>
                        <Table loading={loading} dataSource={products}>
                            <Column title="Mã sản phẩm" dataIndex="productId" key="productId"/>
                            <Column title="Tên sản phẩm" dataIndex="productName" key="productName"/>
                            <Column title="Ngày tạo" dataIndex="dateCreated" key="dateCreated"/>
                            <Column title="Giấy phép" dataIndex="licenseID" key="licenseID"/>
                            <Column
                                title="Trạng thái"
                                dataIndex="tags"
                                key="tags"
                                render={(tags) => (
                                    <>
                                        {tags.map((tag) => {
                                            let color = 'geekblue';
                                            if (tag === 'Bị từ chối') {
                                                color = 'volcano';
                                            }
                                            if (tag === 'Đã phê duyệt') {
                                                color = 'green';
                                            }
                                            return (
                                                <Tag color={color} key={tag} style={{fontSize: "13px"}}>
                                                    {tag.toUpperCase()}
                                                </Tag>
                                            );
                                        })}
                                    </>
                                )}
                            />
                            <Column
                                title=""
                                key="action"
                                render={(_, record) => (
                                    <Space size="middle">
                                        <a>Chi tiết</a>
                                    </Space>
                                )}
                            />
                        </Table>
                    </div>
                </div>
            </main>

        </div>
);
}

export default ProductListPage;