import React, {useState} from 'react';
import './style.scss'

import {ScrollToTop} from '@Util/util';
import {da} from "date-fns/locale";
import {Link} from "react-router-dom";
import ProductItem from "./productItem/ProductItem";

const data = [
    {
        "categoryId": 15,
        "categoryName": "Phụ Kiện",
        "products": [
            {
                "productId": 109,
                "productName": "Tất Nam, Kháng Khuẩn, Khử Mùi ",
                "productPrice": 19000,
                "productDescription": "Tất Nam sử dụng chất liệu Cotton mềm mại, thấm hút mồ tốt, sản phẩm giữ cho đôi chân luôn thoáng mát nên rất phù hợp với những ai thường mang giày cả ngày. Sản phẩm được dệt bo tròn cổ đảm bảo không bị tụt trong suốt quá trình vận động, đi lại.",
                "productImages": [
                    {
                        "imageId": 545,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9dtS.jpg"
                    },
                    {
                        "imageId": 546,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ93o7.jpg"
                    },
                    {
                        "imageId": 547,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9KPe.jpg"
                    },
                    {
                        "imageId": 548,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9qKu.jpg"
                    },
                    {
                        "imageId": 549,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9Bcb.jpg"
                    }
                ],
            },
            {
                "productId": 110,
                "productName": "Tất Nam 1, Kháng Khuẩn, Khử Mùi ",
                "productPrice": 19000,
                "productDescription": "Tất Nam sử dụng chất liệu Cotton mềm mại, thấm hút mồ tốt, sản phẩm giữ cho đôi chân luôn thoáng mát nên rất phù hợp với những ai thường mang giày cả ngày. Sản phẩm được dệt bo tròn cổ đảm bảo không bị tụt trong suốt quá trình vận động, đi lại.",
                "productImages": [
                    {
                        "imageId": 545,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9dtS.jpg"
                    },
                    {
                        "imageId": 546,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ93o7.jpg"
                    },
                    {
                        "imageId": 547,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9KPe.jpg"
                    },
                    {
                        "imageId": 548,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9qKu.jpg"
                    },
                    {
                        "imageId": 549,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9Bcb.jpg"
                    }
                ],
            },
        ]
    },
    {
        "categoryId": 15,
        "categoryName": "Phụ Kiện",
        "products": [
            {
                "productId": 109,
                "productName": "Tất Nam, Kháng Khuẩn, Khử Mùi ",
                "productPrice": 19000,
                "productDescription": "Tất Nam sử dụng chất liệu Cotton mềm mại, thấm hút mồ tốt, sản phẩm giữ cho đôi chân luôn thoáng mát nên rất phù hợp với những ai thường mang giày cả ngày. Sản phẩm được dệt bo tròn cổ đảm bảo không bị tụt trong suốt quá trình vận động, đi lại.",
                "productImages": [
                    {
                        "imageId": 545,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9dtS.jpg"
                    },
                    {
                        "imageId": 546,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ93o7.jpg"
                    },
                    {
                        "imageId": 547,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9KPe.jpg"
                    },
                    {
                        "imageId": 548,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9qKu.jpg"
                    },
                    {
                        "imageId": 549,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9Bcb.jpg"
                    }
                ],
            },
            {
                "productId": 110,
                "productName": "Tất Nam 1, Kháng Khuẩn, Khử Mùi ",
                "productPrice": 19000,
                "productDescription": "Tất Nam sử dụng chất liệu Cotton mềm mại, thấm hút mồ tốt, sản phẩm giữ cho đôi chân luôn thoáng mát nên rất phù hợp với những ai thường mang giày cả ngày. Sản phẩm được dệt bo tròn cổ đảm bảo không bị tụt trong suốt quá trình vận động, đi lại.",
                "productImages": [
                    {
                        "imageId": 545,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9dtS.jpg"
                    },
                    {
                        "imageId": 546,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ93o7.jpg"
                    },
                    {
                        "imageId": 547,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9KPe.jpg"
                    },
                    {
                        "imageId": 548,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9qKu.jpg"
                    },
                    {
                        "imageId": 549,
                        "productId": 109,
                        "imagePath": "https://iili.io/JRQ9Bcb.jpg"
                    }
                ],
            }
        ]
    }
];

const MarketplacePage = () => {

    const NUMBER_PRODUCT_LIMIT = 35;

    return (
        <main id="main">
            <ScrollToTop/>
            <section className="home-content" style={{marginTop: "50px"}}>
                {
                    data.map((item, index) => (
                        <section className="collection">
                            <div className="collection-wrap">
                                <div className="title row">
                                    <p className="col-4">{item.categoryName}</p>
                                </div>
                                <div className="product-list">
                                    {
                                        item.products.slice(0, NUMBER_PRODUCT_LIMIT).map((product, index) => (
                                            <ProductItem key={index} product={product}/>))
                                    }
                                </div>

                                <div className="load-more-wrap text-center">
                                    <Link to={"/category?categoryId=" + item.categoryId}>
                                        <button className="btn btn-vm view-more-product btn-product-winter"
                                                id="view-more-product">
                                            Xem thêm <i className="fa-solid fa-spinner icon-loading"></i>
                                        </button>
                                    </Link>
                                </div>
                            </div>
                        </section>
                    ))
                }



            </section>

        </main>

    );
}

export default MarketplacePage;