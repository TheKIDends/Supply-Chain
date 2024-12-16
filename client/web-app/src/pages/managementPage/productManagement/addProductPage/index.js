import React, {useState} from "react";
import "./style.scss"

import ProductDetails from "./ProductDetails/ProductDetails";
import {toast} from "react-toastify";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {useCookies} from "react-cookie";
import ConfirmDialog from "@Components/dialogs/ConfirmDialog/ConfirmDialog";
import {ADD_PRODUCT_PAGE, API, BREADCRUMB, CONFIRM_DIALOG, MESSAGE, SCROLLING, MANAGEMENT_PAGE} from "@Const";

const AddProductPage = () => {
  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;
  const refreshToken = cookies.refresh_token;

  const navigate = useNavigate();

  const location = useLocation();

  const [productImages, setProductImages] = useState([]);

  const [isShowConfirmDialog, setIsShowConfirmDialog] = useState(false);

  const [informationProduct, setInformationProduct] = useState({
    productName: "",
    productPrice: "",
    category: "",
    details: "",
  });

  const addProduct = async (e) => {
    e.preventDefault();

    if (productImages.length === 0) {
      toast.warn(MESSAGE.MISSING_PRODUCT_IMAGE);
      return;
    }
    if (informationProduct.productName === "") {
      toast.warn(MESSAGE.MISSING_PRODUCT_NAME);
      return;
    }
    if (informationProduct.productPrice === "") {
      toast.warn(MESSAGE.MISSING_PRODUCT_PRICE);
      return;
    }
    if (informationProduct.category === "") {
      toast.warn(MESSAGE.MISSING_PRODUCT_CATEGORY);
      return;
    }
    if (informationProduct.details === "") {
      toast.warn(MESSAGE.MISSING_PRODUCT_DESCRIPTION);
      return;
    }

    const data1 = {
      "productName":"Bột cao lanh [1Kg]",
      "productPrice":"200000",
      "categoryId":"categoryId",
      "creatorId":"2918e6aa-8493-49ac-89f8-3fbf537cd9a2",
      "details":"Bột cao lanh"
    };

    const apiUrl = "http://localhost:8000/api/product/add-product";
    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        body: JSON.stringify(data1),
        headers: {
          // "Authorization": `Bearer ${accessToken}`,
          "Content-Type": "application/json"
        },
      });
      if (response.ok) {
        toast.success(MESSAGE.ADD_PRODUCT_SUCCESS);
        navigate(`/management-page/product-list`, {
          state: { scrolling: SCROLLING.SMOOTH },
        });

      } else {
        toast.error(MESSAGE.GENERIC_ERROR);
      }
    } catch (error) {
      console.error(error.message);
    }
  }

  return (
    <div id="app">
      <main id="main">
        <div className="container profile-wrap">
          <div className="breadcrumb-wrap">
            <a href="/">{BREADCRUMB.HOME_PAGE}</a>
            &gt; <span>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.LABEL}</span>
            &gt; <span>{MANAGEMENT_PAGE.PRODUCT_MANAGEMENT.SUB.ADD_PRODUCT}</span>
          </div>
        </div>

        <div className="container pe-0 ps-0" style={{marginTop: "10px"}}>
          <ProductDetails informationProduct={informationProduct}
                          setInformationProduct={setInformationProduct}
                          productImages={productImages}
                          setProductImages={setProductImages}
          />

          <div data-v-03749d40="" className="product-edit__container">
            <div data-v-03749d40="" className="product-edit">
              <section style={{marginBottom: "50px"}}>
                <div className="button-container" style={{textAlign: "right"}}>

                  <button type="button" className="product-details-btn product-details-btn-danger"
                          onClick={() => {
                            setIsShowConfirmDialog(true)
                          }}
                  >
                    {ADD_PRODUCT_PAGE.REFRESH_BTN}
                  </button>

                  <button type="button" className="product-details-btn" onClick={addProduct}>
                    {ADD_PRODUCT_PAGE.SAVE_BTN}
                  </button>
                </div>
              </section>
            </div>
          </div>
        </div>

        {isShowConfirmDialog && (
          <div className="modal-overlay">
            <ConfirmDialog
              title={<span style={{color: "#294780"}}>{CONFIRM_DIALOG.WARNING_TITLE}</span>}
              subTitle={
                <>
                  {CONFIRM_DIALOG.CONFIRM_REFRESH_DATA}
                </>
              }
              titleBtnAccept={CONFIRM_DIALOG.TITLE_BTN_ACCEPT}
              titleBtnCancel={CONFIRM_DIALOG.TITLE_BTN_CANCEL}
              onAccept={() => {
                setInformationProduct({
                  productName: "",
                  productPrice: "",
                  details: "",
                  category: "",
                });
                setProductImages([]);
                setIsShowConfirmDialog(false);
              }}
              onCancel={() => {
                setIsShowConfirmDialog(false)
              }}/>
          </div>
        )}

      </main>
    </div>
  );
}

export default AddProductPage;