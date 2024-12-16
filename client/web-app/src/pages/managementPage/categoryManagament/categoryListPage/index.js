import React, {useEffect, useState} from 'react';
import "./style.scss";

import {MANAGEMENT_PAGE, BREADCRUMB} from "@Const";
import {ConfigProvider, Space, Table, Tag} from "antd";
import {toast} from "react-toastify";
import {useCookies} from "react-cookie";
import { DownOutlined } from '@ant-design/icons';
import {HiPlus} from "react-icons/hi2";
import AddCategoryDialog from "../components/dialogs/AddCategoryDialog/AddCategoryDialog";

const CategoryListPage = () => {
  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;

  const [categories, setCategories] = useState([]);
  const [loading, setloading] = useState(true);

  const [isAddingCategory, setIsAddingCategory] = useState(null);

  const getCategoryList = async () => {
    const apiUrl = "http://localhost:8000/api/product/get-all-categories";

    if (accessToken == null) {
      setloading(false);
      return;
    }

    try {
      const response = await fetch(apiUrl, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${accessToken}`
        },
      });

      let jsonResponse = await response.json();
      const dataResponse = jsonResponse.data;
      const messageResponse = jsonResponse.message;

      if (response.ok) {
        setCategories(dataResponse);
        console.log(dataResponse);

      } else {
        toast.error(messageResponse);
        console.log(messageResponse);
      }
    } catch (error) {
      console.log(error);
    } finally {
      setloading(false);
    }
  }

  useEffect(() => {
    getCategoryList().then(r => {});
  }, []);

  const columns = [
    {
      title: 'Tên danh mục',
      dataIndex: 'name',
    },
    {
      title: 'Trạng thái',
      dataIndex: 'age',
      // sorter: (a, b) => a.age - b.age,
    },
    // {
    //   title: 'Address',
    //   dataIndex: 'address',
    //   filters: [
    //     {
    //       text: 'London',
    //       value: 'London',
    //     },
    //     {
    //       text: 'New York',
    //       value: 'New York',
    //     },
    //   ],
    //   onFilter: (value, record) => record.address.indexOf(value) === 0,
    // },
    {
      title: 'Hành động',
      key: 'action',
      sorter: true,
      render: () => (

        <Space size="middle" style={{color:"blue"}}>
          <a>Edit</a>
          <a>Delete</a>
        </Space>
      ),
    },
  ];

  const data = Array.from({
    length: 20,
  }).map((_, i) => ({
    key: i,
    name: 'Danh mục',
    age: "Kích hoạt",
    address: `New York No. ${i} Lake Park`,
    description: `My name is John Brown, I am ${i}2 years old, living in New York No. ${i} Lake Park.`,
  }));

  const [yScroll, setYScroll] = useState(false);
  const [xScroll, setXScroll] = useState();
  const [ellipsis, setEllipsis] = useState(true);
  const [showHeader, setShowHeader] = useState(true);
  const [size, setSize] = useState('large');
  const [top, setTop] = useState('none');
  const [bottom, setBottom] = useState('bottomRight');

  const scroll = {};
  if (yScroll) {
    scroll.y = 240;
  }
  if (xScroll) {
    scroll.x = '100vw';
  }

  const tableColumns = columns.map((item) => ({
    ...item,
    ellipsis,
  }));

  if (xScroll === 'fixed') {
    tableColumns[0].fixed = true;
    tableColumns[tableColumns.length - 1].fixed = 'right';
  }

  const tableProps = {
    size,
    showHeader,
    scroll,
    loading
  };

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


        <div className="container">

          <div style={{margin: "0 50px 70px 38px"}}>
            <p className="category-title"
               style={{display: "flex", justifyContent: "space-between"}}>
              DANH MỤC SẢN PHẨM

              <button type="button" className="add-parent-category-btn"
                onClick={(e) => {
                  setIsAddingCategory(true);
                }}
              >
                <HiPlus style={{fontSize: "22px", padding: "0 0px 3px 0", marginRight: "4px"}}/>
                <span
                  style={{marginRight: "5px"}}>Thêm danh mục</span>
              </button>

            </p>
            <div className="list-table">

              <Table
                style={{borderRadius: "10px"}}
                {...tableProps}
                pagination={{
                  position: [top, bottom],
                }}
                columns={tableColumns}
                dataSource={data}
                scroll={scroll}
              />

            </div>
          </div>
        </div>

      </main>

      {isAddingCategory && (
        <div className="modal-overlay">
          <AddCategoryDialog onClose={() => {window.location.reload(); setIsAddingCategory(false)}}/>
        </div>
      )}

    </div>
  );
};

export default CategoryListPage;
