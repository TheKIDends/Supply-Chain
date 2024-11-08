import React, {memo} from 'react';
import './style.scss'
import { Layout, Menu} from 'antd';
const { Header } = Layout;

const items1 = ['1', '2', '3'].map((key) => ({
    key,
    label: `nav ${key}`,
}));


const HeaderComponent = () => {

    return (
        <Header
            style={{
                position: 'fixed',
                display: 'flex',
                alignItems: 'center',
                height: '70px',
                left: 0,
                right: 0,
            }}
        >
            <div className="demo-logo" />
            <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={['2']}
                items={items1}
                style={{
                    flex: 1,
                    minWidth: 0,
                }}
            />
        </Header>
    );
};
export default memo(HeaderComponent);