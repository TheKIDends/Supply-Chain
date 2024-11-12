import React, {memo, useState} from 'react';
import './style.scss'
import {Breadcrumb} from "antd";

const Footer = () => {
    const [count, setCount] = useState(0);

    function incrementCount() {
        return count + 1;
    }

    function handleClick() {
        // setCount((a) => { return a + 1});
        // setCount((a) => { return a + 1});
        // setCount((a) => { return a + 1});
        setCount(incrementCount);
        setCount(incrementCount);
        setCount(incrementCount);
    }

    return (
        <>
            {/*<Breadcrumb*/}
            {/*    items={[*/}
            {/*        {*/}
            {/*            title: 'Home',*/}
            {/*        },*/}
            {/*        {*/}
            {/*            title: 'List',*/}
            {/*        },*/}
            {/*        {*/}
            {/*            title: 'App',*/}
            {/*        },*/}
            {/*    ]}*/}
            {/*    style={{*/}
            {/*        // margin: '16px 0',*/}
            {/*    }}*/}
            {/*/>*/}
            HomePage
            {/*<button onClick={handleClick} style={{width:"100px"}}>{count}</button>*/}
        </>
    );
}

export default memo(Footer);