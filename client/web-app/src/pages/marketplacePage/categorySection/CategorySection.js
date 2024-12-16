import React, {useContext, useEffect, useState} from "react";
import './style.scss';
import {useCookies} from "react-cookie";
import { Link } from "react-router-dom";
import {toast} from "react-toastify";
import {API, HOME_PAGE, MESSAGE, MARKETPLACE_PAGE} from "@Const";
import {da} from "date-fns/locale";

const categoriesData =
    [
        {
            "categoryID": 2,
            "categoryName": "Thực phẩm",
            "parentCategoryID": 1,
            "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRd75uteFCsyLDsVTabrLue_m_RGCeS29sOeSF-tgXIVsLzff7ifwdpzuopj5fm35O4H8E&usqp=CAU",
            "products": null,
            "subCategories": null
        },
        {
            "categoryID": 3,
            "categoryName": "Trang Phục",
            "parentCategoryID": 1,
            "imagePath": "https://iili.io/J5HXFEB.webp",
            "products": null,
            "subCategories": null
        },
        {
            "categoryID": 4,
            "categoryName": "Y tế",
            "parentCategoryID": 1,
            "imagePath": "https://e1.pngegg.com/pngimages/479/347/png-clipart-icones-de-voyage-trousses-de-premiers-soins-soins-de-sante-medecine-urgence-medicale-medecin-trousse-de-premiers-soins-d-urgence-reanimation-cardiopulmonaire-thumbnail.png",
            "products": null,
            "subCategories": null
        },
        {
            "categoryID": 2,
            "categoryName": "Vật liệu",
            "parentCategoryID": 1,
            "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQywL1rhkhC0fIgdEGYIrkY11wL2TxmP-dLeA&s",
            "products": null,
            "subCategories": null
        },

      {
        "categoryID": 2,
        "categoryName": "Thực phẩm",
        "parentCategoryID": 1,
        "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRd75uteFCsyLDsVTabrLue_m_RGCeS29sOeSF-tgXIVsLzff7ifwdpzuopj5fm35O4H8E&usqp=CAU",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 3,
        "categoryName": "Trang Phục",
        "parentCategoryID": 1,
        "imagePath": "https://iili.io/J5HXFEB.webp",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 4,
        "categoryName": "Y tế",
        "parentCategoryID": 1,
        "imagePath": "https://e1.pngegg.com/pngimages/479/347/png-clipart-icones-de-voyage-trousses-de-premiers-soins-soins-de-sante-medecine-urgence-medicale-medecin-trousse-de-premiers-soins-d-urgence-reanimation-cardiopulmonaire-thumbnail.png",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 2,
        "categoryName": "Vật liệu",
        "parentCategoryID": 1,
        "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQywL1rhkhC0fIgdEGYIrkY11wL2TxmP-dLeA&s",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 2,
        "categoryName": "Thực phẩm",
        "parentCategoryID": 1,
        "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRd75uteFCsyLDsVTabrLue_m_RGCeS29sOeSF-tgXIVsLzff7ifwdpzuopj5fm35O4H8E&usqp=CAU",
        "products": null,
        "subCategories": null
      },

      {
        "categoryID": 3,
        "categoryName": "Trang Phục",
        "parentCategoryID": 1,
        "imagePath": "https://iili.io/J5HXFEB.webp",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 4,
        "categoryName": "Y tế",
        "parentCategoryID": 1,
        "imagePath": "https://e1.pngegg.com/pngimages/479/347/png-clipart-icones-de-voyage-trousses-de-premiers-soins-soins-de-sante-medecine-urgence-medicale-medecin-trousse-de-premiers-soins-d-urgence-reanimation-cardiopulmonaire-thumbnail.png",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 2,
        "categoryName": "Vật liệu",
        "parentCategoryID": 1,
        "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQywL1rhkhC0fIgdEGYIrkY11wL2TxmP-dLeA&s",
        "products": null,
        "subCategories": null
      },
      {
        "categoryID": 2,
        "categoryName": "Thực phẩm",
        "parentCategoryID": 1,
        "imagePath": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRd75uteFCsyLDsVTabrLue_m_RGCeS29sOeSF-tgXIVsLzff7ifwdpzuopj5fm35O4H8E&usqp=CAU",
        "products": null,
        "subCategories": null
      },


    ];

const renderProductCategories = (productCategories) => {
    const SEARCH_LINK = '/market/category?categoryID='

    return productCategories.map((category, index) => (
        <div className="owl-item active" key={index} style={{ width: '119px' }}>
            <Link to={SEARCH_LINK + category.categoryID}>
                <div className="category-box">
                    <div className="image-wrap position-relative w-100">
                        <div className="image-wrap__img position-absolute w-100">

                            <img
                                style={{
                                    borderRadius: '50%',
                                    width: '400px',
                                    height: '400-x',
                                    objectFit: 'cover',
                                }}
                                lazy-src={category.imagePath}
                                alt={`Icon danh mục SP 400 x 400 px_${category.categoryName}`}
                                loading="lazy"
                                src={category.imagePath}
                            />

                        </div>
                    </div>
                    <div className="text text-center">
                        <p>{category.categoryName}</p>
                    </div>
                </div>
            </Link>
        </div>
    ));
};

const CategorySection = () => {
    const [currentSlide, setCurrentSlide] = useState(0);
    const maxCategoriesPerPage = 11;
    const [categoryItem, setCategoryItem] = useState(categoriesData)

    const [cookies] = useCookies(['access_token']);
    const accessToken = cookies.access_token;

    const handlePrevClick = () => {
        setCurrentSlide(Math.max(0, currentSlide - maxCategoriesPerPage));
    };

    const handleNextClick = () => {
        setCurrentSlide(Math.min(Math.max(0, categoryItem.length - maxCategoriesPerPage), currentSlide + maxCategoriesPerPage));
    };

    return (
        <section className="category">
            <div className="category-wrap">
                <div className="title">
                    <p className="text-center mb-0">{MARKETPLACE_PAGE.PRODUCT_CATEGORIES_TITLE}</p>
                </div>
                <div className="content owl-carousel owl-theme owl-loaded owl-drag" id="content-category">
                    <div className="owl-stage-outer">
                        <div className="owl-stage"
                             style={{ transform: `translate3d(-${currentSlide * 119}px, 0px, 0px)`, transition: 'all 0.3s ease 0s', width: '50000px' }}>
                            {renderProductCategories(categoryItem)}
                        </div>
                    </div>
                    <div className="owl-nav">
                        <button
                            type="button"
                            role="presentation"
                            className={`owl-prev ${currentSlide === 0 ? 'hide' : ''}`}
                            onClick={handlePrevClick}>
                            <span aria-label="Previous">‹</span>
                        </button>
                        <button
                            type="button"
                            role="presentation"
                            className={`owl-next ${categoryItem.length < maxCategoriesPerPage || currentSlide === categoryItem.length - maxCategoriesPerPage ? 'hide' : ''}`}
                            onClick={handleNextClick}>
                            <span aria-label="Next">›</span>
                        </button>
                    </div>
                </div>
            </div>
        </section>
    );
};
export default CategorySection;
