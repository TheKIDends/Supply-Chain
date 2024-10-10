import React, {useState} from 'react';
import './style.scss'
import {
    LaptopOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    NotificationOutlined,
    UserOutlined
} from '@ant-design/icons';
import {Breadcrumb, Button, Layout, Menu, theme} from 'antd';
import HeaderComponent from "../header/Header";
const { Header, Content, Sider } = Layout;

const items2 = [UserOutlined, LaptopOutlined, NotificationOutlined].map((icon, index) => {
    const key = String(index + 1);
    return {
        key: `sub${key}`,
        icon: React.createElement(icon),
        label: `subnav ${key}`,
        children: new Array(5).fill(null).map((_, j) => {
            const subKey = index * 5 + j + 1;
            return {
                key: subKey,
                label: `option${subKey}`,
            };
        }),
    };
});

const MasterLayout = ({children, ...props}) => {
    const [collapsed, setCollapsed] = useState(true);

    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    const siderStyle = {
        overflow: 'auto',
        position: 'fixed',
        insetInlineStart: 0,
        top: 70,
        bottom: 0,
        scrollbarWidth: 'thin',
        background: colorBgContainer,
        scrollbarColor: 'unset',
    };

    return (
        <Layout>
            <HeaderComponent />

            <Layout hasSider>
                <Sider
                    width={200}
                    style={siderStyle}
                    trigger={null} collapsible collapsed={collapsed}
                >
                    <Menu
                        mode="inline"
                        defaultSelectedKeys={['1']}
                        defaultOpenKeys={['sub1']}
                        style={{
                            height: '100%',
                            borderRight: 0,
                        }}
                        items={items2}
                    />
                </Sider>
                <Layout
                    style={{
                        overflowY: 'auto',
                        overflowX: 'hidden',
                        position: 'fixed',
                        top: 70,
                        right: 0,
                        bottom: 0,
                        padding: `24px 24px 24px 40px`,
                        width: collapsed ? `calc(100vw - 80px)` : `calc(100vw - 200px)`,

                    }}
                    className={collapsed ? 'collapsed' : 'expanded'}
                >
                    <Breadcrumb
                        items={[
                            {
                                title: 'Home',
                            },
                            {
                                title: 'List',
                            },
                            {
                                title: 'App',
                            },
                        ]}
                        style={{
                            // margin: '16px 0',
                        }}
                    />
                    <Button
                        type="text"
                        icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                        onClick={() => setCollapsed(!collapsed)}
                        style={{
                            fontSize: '16px',
                            width: 64,
                            height: 64,
                        }}
                    />

                    <div {...props}>
                        {children}
                    </div>

                    {/*<Content*/}
                    {/*    style={{*/}
                    {/*        padding: 24,*/}
                    {/*        margin: 0,*/}
                    {/*        minHeight: 280,*/}
                    {/*        background: colorBgContainer,*/}
                    {/*        borderRadius: borderRadiusLG,*/}
                    {/*    }}*/}
                    {/*>*/}
                    {/*    Content*/}
                    {/*</Content>*/}
                </Layout>
            </Layout>
        </Layout>
    );
};
export default MasterLayout;