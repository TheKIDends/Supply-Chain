import React, {useEffect, useState} from 'react';
import "./style.scss";

import {MANAGEMENT_PAGE, BREADCRUMB} from "@Const";

const CategoryListPage = () => {

    return (
        <div id="app">
            <main id="main">
                <div className="container profile-wrap">
                    <div className="breadcrumb-wrap">
                        <a href="/">{BREADCRUMB.HOME_PAGE}</a>
                        &gt; <span>{MANAGEMENT_PAGE.CATEGORY_MANAGEMENT.LABEL}</span>
                        &gt; <span>{MANAGEMENT_PAGE.CATEGORY_MANAGEMENT.SUB.CATEGORY_LIST}</span>
                    </div>
                </div>

                CategoryListPage

            </main>
        </div>
    );
};

export default CategoryListPage;
