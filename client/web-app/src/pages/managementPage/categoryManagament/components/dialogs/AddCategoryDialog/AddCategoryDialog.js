import React, {useEffect, useState} from "react";
import "./style.scss"
import {MdOutlinePlaylistAdd} from "react-icons/md";
import {ADD_CATEGORY_DIALOG} from "@Const";
import {useCookies} from "react-cookie";
import {toast} from "react-toastify";

const AddCategoryDialog = ({onClose}) => {
  const [categoryName, setCategoryName] = useState('');

  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;

  const [user, setUser] = useState();

  const addCategory = async () => {
    const apiUrl = "http://localhost:8000/api/product/add-category";

    if (accessToken == null) {
      return;
    }
    if (categoryName == null || categoryName === '') {
      toast.warn("Vui lòng nhập tên danh mục");
      return;
    }

    const data = {
      categoryName
    };

    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
      });

      let jsonResponse = await response.json();
      const dataResponse = jsonResponse.data;
      const messageResponse = jsonResponse.message;

      if (response.ok) {
        onClose();
        toast.success(messageResponse);
      } else {
        console.log(messageResponse);
        toast.error(messageResponse);
      }
    } catch (error) {
      console.log(error);
    } finally {
    }
  };

  return (
      <div className="modal fade show" id="modal-auth" tabIndex="-1" aria-labelledby="exampleModalLabel"
           style={{ display: 'block', paddingLeft: '0px' }} aria-modal="true" role="dialog">
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content" style={{color:"#333333", padding:"30px", width:"550px"}}>
            <div style={{marginBottom:"13px"}}>
              <MdOutlinePlaylistAdd style={{fontSize:"27px", margin:"0 7px 5px 0"}} />
              <span style={{fontSize:"20px", fontWeight:"900"}}>{ADD_CATEGORY_DIALOG.ADD_CATEGORY}</span>
            </div>

            <div data-v-38ab3376="" className="text-overflow">
              <input className="input-add-category"
                     type="text"
                     value={categoryName}
                     placeholder={ADD_CATEGORY_DIALOG.CATEGORY_NAME_PLACEHOLDER}
                     onChange={(e) => setCategoryName(e.target.value)}/>
            </div>

            <div className="button-container" style={{marginTop:"40px"}}>
              <button type="button" className="add-category-dialog-btn" onClick={addCategory}>{ADD_CATEGORY_DIALOG.ADD_BTN}</button>
              <button type="button" className="add-category-dialog-btn add-category-dialog-btn-cancel" onClick={onClose}>{ADD_CATEGORY_DIALOG.CANCEL_BTN}</button>
            </div>
          </div>
        </div>
      </div>
  );
}

export default AddCategoryDialog;