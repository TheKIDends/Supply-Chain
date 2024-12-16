import React, {useState} from "react";
import "./style.scss"
import {MdOutlineEditNote} from "react-icons/md";
import {EDIT_CATEGORY_DIALOG} from "@Const";

const EditCategoryDialog = ({categoryID, categoryName, onClose}) => {
  const [inputValue, setInputValue] = useState(categoryName);

  return (
      <div className="modal fade show" id="modal-auth" tabIndex="-1" aria-labelledby="exampleModalLabel"
           style={{ display: 'block', paddingLeft: '0px' }} aria-modal="true" role="dialog">
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content" style={{color:"#333333", padding:"30px", width:"550px"}}>
            <div style={{marginBottom:"13px"}}>
              <MdOutlineEditNote style={{fontSize:"30px", margin:"0 7px 5px 0"}} />
              <span style={{fontSize:"20px", fontWeight:"900"}}>{EDIT_CATEGORY_DIALOG.EDIT_CATEGORY}</span>
            </div>

            <div data-v-38ab3376="" className="text-overflow">
              <input className="input-add-category"
                     type="text"
                     value={inputValue}
                     placeholder={EDIT_CATEGORY_DIALOG.CATEGORY_NAME_PLACEHOLDER}
                     onChange={(e) => setInputValue(e.target.value)}/>
            </div>

            <div className="button-container" style={{marginTop:"40px"}}>
              <button type="button" className="add-category-dialog-btn">{EDIT_CATEGORY_DIALOG.SAVE_BTN}</button>
              <button type="button" className="add-category-dialog-btn add-category-dialog-btn-cancel" onClick={onClose}>{EDIT_CATEGORY_DIALOG.CANCEL_BTN}</button>
            </div>
          </div>
        </div>
      </div>
  );
}

export default EditCategoryDialog;