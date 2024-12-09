import React, { useEffect, useRef, useState} from "react";
import "./style.scss"

import {toast} from "react-toastify";
import {generateUniqueId} from '@Util/util';
import {MESSAGE, PRODUCT_DETAILS} from "@Const";
import {TbListSearch} from "react-icons/tb";
import {ConfigProvider, Select} from "antd";

const ProductDetails = ({ informationProduct, setInformationProduct, productImages, setProductImages}) => {
  const inputRef = useRef(null);

  const handleDeleteImage = (index) => {
    const newProductImages = [...productImages];
    newProductImages.splice(index, 1);
    setProductImages(newProductImages);
  };

  const handleImageChange = (e) => {
    const newFiles = Array.from(e.target.files);
    let totalFiles = [...productImages, ...newFiles];

    if (totalFiles.length > PRODUCT_DETAILS.MAX_PRODUCT_IMAGES) {
      totalFiles = totalFiles.slice(0, PRODUCT_DETAILS.MAX_PRODUCT_IMAGES);
      toast.warn(MESSAGE.MAXIMUM_UPLOAD_LIMIT_PRODUCT);
    }
    setProductImages(totalFiles);
    inputRef.current.value = null;
  };

  const handleInputProductName = (e) => {
    let value = e.target.value;
    value = value.substring(0, PRODUCT_DETAILS.MAX_LENGTH_PRODUCT_NAME);

    const newInformationProduct = {
      ...informationProduct,
      productName: value,
    }
    setInformationProduct(newInformationProduct);
  }

  const handleInputProductPrice = (e) => {
    const value = e.target.value;
    if (isNaN(value)) {
      return;
    }

    const newInformationProduct = {
      ...informationProduct,
      productPrice: value,
    }
    setInformationProduct(newInformationProduct);
  }

  const handleInputCategory = (value) => {
    const newInformationProduct = {
      ...informationProduct,
      category: value,
    }
    setInformationProduct(newInformationProduct);
  }

  const handleInputProductDetails = (e) => {
    let value = e.target.value;
    value = value.substring(0, PRODUCT_DETAILS.MAX_LENGTH_PRODUCT_DESCRIPTION);

    const newInformationProduct = {
      ...informationProduct,
      details: value,
    }
    setInformationProduct(newInformationProduct);
  }

  return (
      <div data-v-03749d40="" className="product-edit__container">
        <div data-v-03749d40="" className="product-edit">
          <section data-v-03749d40="" className="product-edit__section">
            <div data-v-2250a4e1="" data-v-54a51dd8="" data-v-03749d40=""
                 className="product-detail-panel product-basic-info"
            >

              <div style={{
                color: "#294780",
                fontSize: "23px",
                fontWeight: "700",
                lineHeight: "25px",
                margin: "10px 0 35px 0"
              }}>
                <div data-v-2250a4e1="" className="header__wrap">
                  <div data-v-54a51dd8="" data-v-2250a4e1="" className="title">
                    {PRODUCT_DETAILS.PRODUCT_INFORMATION}
                  </div>
                </div>
              </div>

              <div className="container" style={{minWidth: "1000px"}}>

                <div className="edit-row">
                  <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-label edit-title">
                    <span style={{
                      fontSize: "16px",
                      fontWeight: "500",
                      lineHeight: "22px"
                    }}>{PRODUCT_DETAILS.PRODUCT_IMAGE}</span>
                  </div>
                  <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-main image-offset">
                    <div data-v-54a51dd8="" data-v-2250a4e1="" style={{lineHeight: "40px"}}>
                      <div data-v-36db20dc="" data-v-54a51dd8="" className="mandatory" data-v-2250a4e1="">
                        <span data-v-36db20dc="" className="mandatory-icon">*</span></div>
                      <span data-v-54a51dd8="" data-v-2250a4e1="">{PRODUCT_DETAILS.SQUARE_IMAGE}</span>
                    </div>
                    <div data-v-05032044="" data-v-54a51dd8="" className="edit-main supply-chain-image-manager"
                         data-v-2250a4e1=""
                         data-product-edit-field-unique-id="images">
                      <div style={{display: 'flex'}}>

                        <div style={{display: 'flex'}}>
                          {productImages.map((file, index) => (
                              <div key={index} className="image-box">
                                <img className="image-itembox" key={index} src={URL.createObjectURL(file)}
                                     alt={`Image ${index}`}/>
                                <div data-v-05032044="" data-v-1190c12e=""
                                     className="supply-chain-image-manager__tools">
                                      <span data-v-05032044="" data-v-1190c12e=""
                                            className="supply-chain-image-manager__icon supply-chain-image-manager__icon--delete"
                                            onClick={() => handleDeleteImage(index)}>
                                          <i data-v-05032044="" className="supply-chain-icon" data-v-1190c12e="">
                                             <svg viewBox="0 0 16 16">
                                                <g>
                                                   <path d="M14.516 3.016h-4v-1a.998.998 0 00-.703-.955.99.99 0
                                                   00-.297-.045h-3a.998.998 0 00-.955.703.99.99 0 00-.045.297v1h-4a.5.5
                                                   0 100 1h1v10a.998.998 0 00.703.955.99.99 0 00.297.045h9a.998.998 0
                                                   00.955-.703.99.99 0 00.045-.297v-10h1a.5.5 0 100-1zm-8-1h3v1h-3v-1zm6
                                                   12h-9v-10h9v10z"></path>

                                                   <path d="M5.516 12.016a.5.5 0 00.5-.5v-4a.5.5 0 10-1 0v4a.5.5 0
                                                   00.5.5zM8.016 12.016a.5.5 0 00.5-.5v-5a.5.5 0 10-1 0v5a.5.5 0
                                                   00.5.5zM10.516 12.016a.5.5 0 00.5-.5v-4a.5.5 0 10-1 0v4a.5.5
                                                   0 00.5.5z"></path>
                                                </g>
                                             </svg>
                                          </i>
                                       </span>
                                </div>
                              </div>
                          ))}
                        </div>

                        <div data-v-05032044="" className="supply-chain-image-manager__itembox"
                             style={{width: "80px", maxWidth: "80px", height: "80px", maxHeight: "80px"}}>

                          <div data-v-05032044="" className="supply-chain-image-manager__content" onClick={() => {
                            inputRef.current.click();
                          }}>
                            <div data-v-05032044="" className="supply-chain-image-manager__upload">
                              <div data-v-4ff6c453="" data-v-05032044=""
                                   className="supply-chain-file-upload" accept="image/*">
                                <div data-v-4ff6c453="" className="supply-chain-upload">

                                  <div className="supply-chain-upload-wrapper supply-chain-upload-dragger">

                                    <input type="file"
                                           name="file"
                                           ref={inputRef}
                                           accept="image/*"
                                           multiple="multiple"
                                           className="supply-chain-upload__input"
                                           onChange={handleImageChange}
                                    />

                                    <div data-v-05032044="" className="supply-chain-image-manager__upload__content">
                                      <div data-v-05032044=""
                                           className="supply-chain-image-manager__upload__content__icon">
                                        <i data-v-05032044="" className="supply-chain-icon">
                                          <svg viewBox="0 0 23 21" xmlns="http://www.w3.org/2000/svg">
                                            <path
                                                d="M18.5 0A1.5 1.5 0 0120 1.5V12c-.49-.07-1.01-.07-1.5 0V1.5H2v12.65l3.395-3.408a.75.75 0 01.958-.087l.104.087L7.89 12.18l3.687-5.21a.75.75 0 01.96-.086l.103.087 3.391 3.405c.81.813.433 2.28-.398 3.07A5.235 5.235 0 0014.053 18H2a1.5 1.5 0 01-1.5-1.5v-15A1.5 1.5 0 012 0h16.5z"></path>
                                            <path
                                                d="M6.5 4.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM18.5 14.25a.75.75 0 011.5 0v2.25h2.25a.75.75 0 010 1.5H20v2.25a.75.75 0 01-1.5 0V18h-2.25a.75.75 0 010-1.5h2.25v-2.25z"></path>
                                          </svg>
                                        </i>
                                      </div>
                                      <div data-v-05032044=""
                                           className="supply-chain-image-manager__upload__content__text">
                                        {PRODUCT_DETAILS.ADD_IMAGE} ({productImages.length}/{PRODUCT_DETAILS.MAX_PRODUCT_IMAGES})
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>
                </div>

                <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-row">
                  <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-label edit-title"
                       data-education-trigger-key="name">
                    <div data-v-36db20dc="" data-v-54a51dd8="" className="mandatory" data-v-2250a4e1=""><span
                        data-v-36db20dc="" className="mandatory-icon">*</span></div>
                    <span style={{
                      fontSize: "16px",
                      fontWeight: "500",
                      lineHeight: "22px"
                    }}>{PRODUCT_DETAILS.PRODUCT_NAME}</span>
                  </div>
                  <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-main">
                    <div data-v-1190c12e="" data-v-54a51dd8="" className="popover-wrap" data-v-2250a4e1="">
                      <div data-v-f872a002="" data-v-1c124603="" data-v-54a51dd8=""
                           className="custom-len-calc-input product-edit-form-item" size="large"
                           data-education-trigger-key="name" data-v-1190c12e=""
                           data-product-edit-field-unique-id="name">
                        <div data-v-f872a002="" className="product-edit-form-item-content">
                          <div data-v-1c124603="" className="supply-chain-input" data-v-f872a002="">
                            <div className="supply-chain-input__inner supply-chain-input__inner--large">
                              <input type="text" placeholder={PRODUCT_DETAILS.ENTER_PLACEHOLDER}
                                     size="large" resize="none" rows="2"
                                     minrows="2" maxLength="Infinity" restrictiontype="input" max="Infinity"
                                     min="-Infinity" className="supply-chain-input__input"
                                     value={informationProduct.productName}
                                     onChange={handleInputProductName}
                              />
                              <div className="supply-chain-input__suffix">
                                <span className="supply-chain-input__suffix-split"></span>
                                {informationProduct.productName.length + '/' + PRODUCT_DETAILS.MAX_LENGTH_PRODUCT_NAME}
                              </div>
                            </div>

                          </div>

                        </div>
                      </div>
                    </div>

                  </div>
                </div>

                <div className="edit-row">
                  <div className="edit-row-left edit-label" data-education-trigger-key="price">
                    <div className="mandatory"><span className="mandatory-icon">*</span></div>
                    <span
                        style={{fontSize: "16px", fontWeight: "500", lineHeight: "22px"}}>{PRODUCT_DETAILS.PRICE}</span>
                  </div>
                  <div className="degrade-wrap edit-row-right-full">
                    <div className="basic-price" data-product-edit-field-unique-id="price">
                      <div className="product-edit-form-item" data-education-trigger-key="price">
                        <div className="product-edit-form-item-content">
                          <div className="popover-wrap">
                            <div className="supply-chain-input price-input product-edit-input" size="large"
                                 prefix-label="₫" is-round="true">
                              <div className="supply-chain-input__inner supply-chain-input__inner--large">
                                <div className="supply-chain-input__prefix">
                                  ₫<span className="supply-chain-input__prefix-split"></span>
                                </div>
                                <input type="text" placeholder={PRODUCT_DETAILS.ENTER_PLACEHOLDER}
                                       size="large" resize="vertical" rows="2"
                                       minrows="2" restrictiontype="value"
                                       max="Infinity" min="-Infinity" isround="true"
                                       className="supply-chain-input__input"
                                       value={informationProduct.productPrice}
                                       onChange={handleInputProductPrice}
                                />
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>


                <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-row is-last-edit-row">
                  <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-label edit-row-left"
                       data-education-trigger-key="category">
                    <div data-v-36db20dc="" data-v-54a51dd8="" className="mandatory" data-v-2250a4e1=""><span
                        data-v-36db20dc="" className="mandatory-icon">*</span></div>
                    <span style={{
                      fontSize: "16px",
                      fontWeight: "500",
                      lineHeight: "22px"
                    }}>{PRODUCT_DETAILS.CATEGORY}</span>
                  </div>

                  <div className="degrade-wrap edit-row-right-full">
                    <div className="basic-price" data-product-edit-field-unique-id="price">
                      <div className="product-edit-form-item" data-education-trigger-key="price">
                        <div className="product-edit-form-item-content">
                          <div className="popover-wrap">
                            <div className="supply-chain-input price-input product-edit-input" size="large"
                                 prefix-label="₫" is-round="true">
                              <div className="supply-chain-input__inner supply-chain-input__inner--large"
                                   style={{padding: 0}}
                              >

                                <ConfigProvider
                                    theme={{
                                      components: {
                                        Select: {
                                          fontSizeLG: "12",
                                          controlItemBgActive: '#e6f0ff',
                                        },

                                      },
                                    }}
                                >
                                  <Select
                                      style={{width: "100%"}}
                                      bordered={false}
                                      size={"large"}
                                      options={[
                                        {
                                          value: "Thực phẩm",
                                          label: "Thực phẩm"
                                        },
                                        {
                                          value: "Đồ điện tử",
                                          label: "Đồ điện tử"
                                        },
                                        {
                                          value: "Y tế",
                                          label: "Y tế"
                                        },
                                        {
                                          value: "Quần áo",
                                          label: "Quần áo"
                                        },
                                      ]}
                                      onChange={(value) => {
                                        handleInputCategory(value)
                                      }}
                                  />
                                </ConfigProvider>


                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>


                <div data-v-54a51dd8="" data-v-2250a4e1="" className="edit-row description-wrap">
                  <div data-v-2250a4e1="" className="edit-label edit-title">
                    <div data-v-36db20dc="" data-v-54a51dd8="" className="mandatory" data-v-2250a4e1="">
                      <span data-v-36db20dc="" className="mandatory-icon">*</span>
                    </div>
                    <span style={{
                      fontSize: "16px",
                      fontWeight: "500",
                      lineHeight: "22px"
                    }}>{PRODUCT_DETAILS.PRODUCT_DESCRIPTION}</span>
                  </div>
                  <div className="edit-main">
                    <div className="product-description">

                      <div data-v-79ee9b0a="" data-ls-upload-cmpt=""
                           className="product-edit-form-item-content">
                        <div data-v-105cd290="" className="supply-chain-input__area" data-ls-upload-cmpt=""
                             data-v-79ee9b0a="">
                              <textarea type="textarea" resize="none" rows="2" minrows="9"
                                        maxrows="26" autosize="true" maxLength="Infinity"
                                        restrictiontype="input"
                                        className="supply-chain-input__inner supply-chain-input__inner--normal"
                                        style={{resize: "none", minHeight: "209.6px", height: "209.6px"}}
                                        value={informationProduct.details}
                                        onChange={handleInputProductDetails}
                              ></textarea>
                        </div>
                        <div className="text-area-label" style={{fontSize: "14px", color: "#999999"}}>
                          <span className="text-area-label-pre">{informationProduct.details.length}</span>
                          <span>{'/' + PRODUCT_DETAILS.MAX_LENGTH_PRODUCT_DESCRIPTION}</span>
                        </div>
                      </div>

                    </div>

                  </div>
                </div>
              </div>

            </div>
          </section>
        </div>

      </div>
  );
}

export default ProductDetails;