import React, {useState} from 'react';
import './style.scss'

import {ScrollToTop} from '@Util/util';
import {Link} from "react-router-dom";
import ProductItem from "./productItem/ProductItem";
import {Col, Row} from "antd";
import {BUTTON} from "@Const";
import CategorySection from "./categorySection/CategorySection";

const data = [
    {
        "categoryId": 15,
        "categoryName": "Quần áo",
        "products": [
            {
                "productID": 66,
                "productName": "Áo Thun Dài Tay Nam, Mềm Mịn, Thấm Hút Hiệu Quả",
                "productPrice": 349000,
                "productDescription": "Áo Thun Dài Tay Nam, Mềm Mịn, Thấm Hút Hiệu Quả nổi bật với chất liệu Viscose từ sợi lụa nhân tạo có cấu trúc tương tự Cotton nhưng được kết hợp với Feezing Nylonj, Spandex giúp tăng độ mềm mại, bền đẹp và đàn hồi của áo.",
                "productImages": [
                    {
                        "imageID": 306,
                        "productID": 66,
                        "imagePath": "https://iili.io/JR4ZHAJ.jpg"
                    },
                    {
                        "imageID": 307,
                        "productID": 66,
                        "imagePath": "https://iili.io/JR6FrTN.jpg"
                    },
                    {
                        "imageID": 308,
                        "productID": 66,
                        "imagePath": "https://iili.io/JR6F4jI.jpg"
                    }
                ],
            },
            {
                "productID": 67,
                "productName": "Áo Thun Dài Tay Nam, In Chữ Combination",
                "productPrice": 319000,
                "productDescription": "Áo Thun Dài Tay Nam, in Chữ Combination sở hữu kiểu dáng Slim fit vừa vặn, tôn dáng với khả năng giữ ấm cơ thể cực tốt. Áo thun dài tay có đa dạng màu sắc, chủ yếu là các màu basic, dễ mặc và dễ phối. Thiết kế trẻ trung với điểm nhấn là hình in thêu chữ Combination hiện đại.\r\n\r\nChất liệu Viscose hay còn được gọi là sợi lụa nhân tạo, là một trong những dòng chất liệu cao cấp của. Viscose được tổng hợp từ chất xơ của sợi Cellulose làm từ bột ỗ như cây sồi, cây thông, bạch đàn tre... giúp cấu trúc của loại vải này gần như tương tự với Cotton. Tuy nhiên, đặc tính nổi trợi hơn nằm ở độ mềm mịn, thân thiện với làn da cũng như môi trường sống xung quanh. ",
                "productImages": [
                    {
                        "imageID": 309,
                        "productID": 67,
                        "imagePath": "https://iili.io/JR4ZRPS.jpg"
                    },
                    {
                        "imageID": 310,
                        "productID": 67,
                        "imagePath": "https://iili.io/JR6KKaj.jpg"
                    },
                    {
                        "imageID": 311,
                        "productID": 67,
                        "imagePath": "https://iili.io/JR6Kf8x.jpg"
                    },
                    {
                        "imageID": 312,
                        "productID": 67,
                        "imagePath": "https://iili.io/JR6KCuV.jpg"
                    },
                    {
                        "imageID": 313,
                        "productID": 67,
                        "imagePath": "https://iili.io/JR6KnwB.jpg"
                    },
                    {
                        "imageID": 314,
                        "productID": 67,
                        "imagePath": "https://iili.io/JR6Kzn1.jpg"
                    }
                ],
            },
            {
                "productID": 68,
                "productName": "Áo Thun Dài Tay Nam, Mềm Mịn, Thấm Hút",
                "productPrice": 239000,
                "productDescription": "ÁO THUN THIẾT KẾ CONFIDENCE \r\n\r\nÁo thun dài tay thiết kế in ép nhiệt bền bỉ, không rạn vỡ khi giặt ủi. Hình in mang phong cách trẻ trung, tạo điểm nhấn nổi bật.\r\n\r\nChất liệu chủ đạo từ Cotton mang lại cảm giác vải mềm mại, co giãn và đàn hồi tốt, cho bạn trải nghiệm thoải mái tối đa khi mặc.",
                "productImages": [
                    {
                        "imageID": 315,
                        "productID": 68,
                        "imagePath": "https://iili.io/JR4tYsp.jpg"
                    },
                    {
                        "imageID": 316,
                        "productID": 68,
                        "imagePath": "https://iili.io/JR6KY9R.jpg"
                    },
                    {
                        "imageID": 317,
                        "productID": 68,
                        "imagePath": "https://iili.io/JR6Kaup.jpg"
                    },
                    {
                        "imageID": 318,
                        "productID": 68,
                        "imagePath": "https://iili.io/JR6KcwN.jpg"
                    },
                    {
                        "imageID": 319,
                        "productID": 68,
                        "imagePath": "https://iili.io/JR6KltI.jpg"
                    },
                    {
                        "imageID": 320,
                        "productID": 68,
                        "imagePath": "https://iili.io/JR6K1nt.jpg"
                    }
                ],
            },
            {"productID": 95,
                "productName": "Quần Short Thể Thao Nam, Thiết Kế Cạp Cúc Xẻ Gấu Thời Trang",
                "productPrice": 499000,
                "productDescription": "Quần Short Thể Thao Nam, Thiết Kế Cạp Cúc Xẻ Gấu Thời Trang sở hữu phom dáng Slim fit ôm vừa vặn, tôn dáng, mang đến diện mạo trẻ trung cho nam giới. Điểm nhấn của mẫu quần này nằm ở thiết kế cạp cúc xẻ gấu thời trang, năng động, thích hợp với hoạt động di chuyển, tập luyện... nhưng vẫn luôn lịch sự, tạo cảm giác thoải mái trong mọi hoạt động. Đặc biệt, lỗ thoáng khi ở 2 bên đùi giúp mang tới trải nghiệm thoáng mát tối đa khi mặc, không bị bí bách hay bết dính mồ hôi. Thiết kế túi vát 2 bên kết hợp với khóa kéo tiện dụng, thích hợp để đựng điện thoại, ví, chìa khóa mà không lo bị rơi... \r\n\r\nQuần short thể thao được làm từ chất liệu Polyamide với bề mặt vải mềm mượt, mát, mau khô, đặc biệt thoáng khí, không bết dính mồ hôi. Việc bổ sung thêm thành phần Spandex giúp tăng độ co giãn, đàn hồi, để bạn nam thoải mái trong mọi hoạt động mà không lo nhăn nhàu, giữ form quần khỏe khoắn, bền đẹp. ",
                "productImages": [
                    {
                        "imageID": 461,
                        "productID": 95,
                        "imagePath": "https://iili.io/JRLtQO7.jpg"
                    },
                    {
                        "imageID": 462,
                        "productID": 95,
                        "imagePath": "https://iili.io/JRLtZb9.jpg"
                    },
                    {
                        "imageID": 463,
                        "productID": 95,
                        "imagePath": "https://iili.io/JRLtDxe.jpg"
                    },
                    {
                        "imageID": 464,
                        "productID": 95,
                        "imagePath": "https://iili.io/JRLtmib.jpg"
                    },
                    {
                        "imageID": 465,
                        "productID": 95,
                        "imagePath": "https://iili.io/JRLtyfj.jpg"
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
    },

];

const MarketplacePage = () => {
    const NUMBER_PRODUCT_LIMIT = 20;

    return (
        <main id="main">
            <ScrollToTop/>
            <div className="container" style={{marginTop: "100px"}}>
                <CategorySection />
                <div>
                    {
                        data.map((item, index) => (
                            <section key={index} className="collection">
                                <div className="collection-wrap">
                                    <div className="title row">
                                        <p className="col-4">{item.categoryName}</p>
                                    </div>
                                    <div className="product-list">

                                        <Row gutter={[16, 24]}>
                                            {
                                                item.products &&
                                                item.products.slice(0, NUMBER_PRODUCT_LIMIT).map((product, index) => (
                                                    <Col key={index} className="gutter-row" span={6}>
                                                        <div style={{padding:"8px 0"}}>
                                                            <ProductItem product={product}/>
                                                        </div>
                                                    </Col>

                                                ))
                                            }


                                        </Row>

                                    </div>

                                    <div className="load-more-wrap text-center">
                                        <Link to={"/market/category?categoryId=" + item.categoryId}>
                                            <button className="btn btn-vm view-more-product btn-product-winter">
                                                {BUTTON.SEE_MORE}
                                            </button>
                                        </Link>
                                    </div>
                                </div>
                            </section>
                        ))
                    }
                </div>
            </div>
        </main>

    );
}

export default MarketplacePage;