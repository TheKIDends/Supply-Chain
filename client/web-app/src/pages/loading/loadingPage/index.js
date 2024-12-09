import React, {useState} from 'react';
import { LoadingOutlined } from '@ant-design/icons';
import { Flex, Spin } from 'antd';
import './style.scss'

const LoadingPage = () => {

    return (
        <>
            <div className="d-flex justify-content-center align-items-center" style={{width: "100%", height: "100%"}}>
                <Spin indicator={<LoadingOutlined style={{ fontSize: 48 }} spin />} />
            </div>
        </>
    );
}

export default LoadingPage;