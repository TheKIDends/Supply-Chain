import React, {useState} from 'react';
import "./style.scss";

import {API, DIALOGS, MESSAGE, REGISTER} from "@Const";
import {toast} from "react-toastify";

const RegisterDialog = ({ onClose, onSwitch }) => {
  const [fullName, setFullName] = useState("");
  const [role, setRole] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      toast.error(MESSAGE.CONFIRMATION_PASSWORD_MISMATCH);
      return;
    }

    const data = {
      fullName,
      phoneNumber,
      password,
      role,
    };

    const apiUrl = "http://localhost:8000/api/user/register";
    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json"
        },
      });

      let jsonResponse = await response.json();
      const dataResponse = jsonResponse.data;
      const messageResponse = jsonResponse.message;

      if (response.ok) {
        toast.success(messageResponse);
        handleSwitchToOtherDialog(DIALOGS.LOGIN);
      } else {
        // Đăng ký thất bại
        toast.error(messageResponse);
      }
    } catch (error) {
      toast.error(MESSAGE.GENERIC_ERROR);
      console.error(error.message);
    }
  };

  const handleButtonCloseClick = () => {
    onClose();
  };

  const handleSwitchToOtherDialog = (dialogName) => {
    onSwitch(dialogName);
  };

  return (
      <div className="modal fade show" id="modal-register"
           tabIndex="-1" aria-labelledby="exampleModalLabel" aria-modal="true"
           role="dialog" style={{ display: 'block', paddingLeft: '0px' }}
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content" style={{borderRadius:"5px"}}>
            <div className="modal-body">
              <div className="title-header-wrap" style={{marginTop:"5px"}}>
                <span className="title">{REGISTER.REGISTER}</span>
                <button type="button" className="btn-close pointer-cursor" data-bs-dismiss="modal" aria-label="Close" onClick={handleButtonCloseClick}></button>
              </div>
              <div className="form-wrap">
                <form onSubmit={handleSubmit} method="POST" className="form" id="form-register">
                  <div className="input-wrap mt-0">
                    <label className="title">{REGISTER.FULL_NAME}</label>
                    <input id="name-register" name="name" type="text" placeholder={REGISTER.FULL_NAME_PLACEHOLDER}
                           required
                           onChange={(e) => setFullName(e.target.value)}
                    />
                  </div>

                  <div className="input-wrap">
                    <label className="title">{REGISTER.ROLE}</label>
                    <select
                        id="role-register"
                        name="role"
                        required
                        value={role}
                        onChange={(e) => setRole(e.target.value)}
                    >
                      <option value="" disabled>{REGISTER.ROLE_PLACEHOLDER}</option>
                      <option value="Doanh nghiệp">Doanh nghiệp</option>
                      <option value="Đơn vị vận chuyển">Đơn vị vận chuyển</option>
                      <option value="Khách hàng">Khách hàng</option>
                    </select>
                  </div>

                  <div className="input-wrap">
                    <label className="title">{REGISTER.PHONE_NUMBER}</label>
                    <input id="phone-register" name="username" type="text"
                           placeholder={REGISTER.PHONE_NUMBER_PLACEHOLDER}
                           required
                           value={phoneNumber}
                           onChange={(e) => {
                             if (!isNaN(e.target.value)) setPhoneNumber(e.target.value)
                           }}
                    />
                  </div>

                  <div className="input-wrap input-password-wrap">
                    <label className="title">{REGISTER.PASSWORD}</label>
                    <input id="password-register" name="password" className="input-password" type="password"
                           minLength={6}
                           placeholder={REGISTER.PASSWORD_PLACEHOLDER} aria-autocomplete="list" required
                           onChange={(e) => setPassword(e.target.value)}
                    />
                  </div>

                  <div className="input-wrap input-password-wrap">
                    <label className="title">{REGISTER.CONFIRM_PASSWORD}</label>
                    <input id="password-register-confirm" name="password" className="input-password" type="password"
                           minLength={6}
                           placeholder={REGISTER.CONFIRM_PASSWORD_PLACEHOLDER} aria-autocomplete="list" required
                           onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                  </div>

                  <div className="btn-wrap" style={{marginTop: "50px"}}>
                    <button type="submit" className="btn btn-primary btn-login">
                      {REGISTER.REGISTER_BUTTON}
                    </button>
                  </div>
                </form>
              </div>

              <div className="register-wrap" style={{margin: "50px 0 20px 0"}}>
          <span className="title">
            {REGISTER.ALREADY_HAVE_ACCOUNT_QUESTION}
            <span className="btn-open-modal-login"
                  onClick={() => handleSwitchToOtherDialog(DIALOGS.LOGIN)}> {REGISTER.LOGIN_HERE}</span>
          </span>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
};

export default RegisterDialog;
