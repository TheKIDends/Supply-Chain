import React, {memo, useState} from 'react';
import './style.scss'
import {FOOTER, IMAGE_URL} from "@Const";

const Footer = () => {

    return (
        <footer>
            <div className="footer">
                <div className="container ps-0 pe-0">
                    <div className="footer-content">
                        <div className="row">
                            <div className="col-4 pl-20" style={{width: "45%"}}>
                                <div className="d-flex align-items-center logo">
                                    <a href="/">
                                        <img src={IMAGE_URL.LOGO_SUPPLY_CHAIN} style={{height: "35px", width:"200px"}}
                                             alt="Logo"
                                             loading="lazy"/>
                                    </a>
                                </div>

                                <h4 className="title-menu position-relative mt-30" style={{fontSize: "16px"}}>
                                    {FOOTER.TITLE_1}
                                </h4>
                                <div
                                    style={{borderBottom: "3px solid #5334db", margin: "10px 0 20px 0", width: "30%"}}/>

                                <ul className="list-menu">
                                    <li className="fone d-flex" style={{paddingRight: "35px"}}>
                                      <span style={{fontWeight: "500"}}>
                                        {FOOTER.DESCRIPTION_CONTENT}
                                      </span>
                                    </li>
                                </ul>
                            </div>


                        </div>
                    </div>
                </div>
                <div className="copyright w-100 text-center">
                    {FOOTER.DEVELOPED_BY}
                </div>
            </div>
        </footer>
    );
}

export default memo(Footer);