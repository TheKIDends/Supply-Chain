import React, {useState} from 'react';
import "./style.scss";
import { Cookies } from 'react-cookie';
import {API, DIALOGS, ERROR, LOGIN} from "@Const";
import {toast} from "react-toastify";
import {MESSAGE} from "../../../util/const";

const LoginDialog = ({ onClose, onSwitch }) => {

  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");

  const handleButtonCloseClick = () => {
    onClose();
  };

  const handleSwitchToOtherDialog = (dialogName) => {
    onSwitch(dialogName);
  };


  const handleLoginFormSubmit = async (e) => {
    e.preventDefault();

    const data = {
      phoneNumber,
      password
    };

    const apiUrl = "http://192.168.0.106:8000/api/user/login";
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
        let accessToken = dataResponse.accessToken;
        let refreshToken = dataResponse.refreshToken;

        if (accessToken == null || refreshToken == null) {
          toast.error(messageResponse);
          return;
        }

        const cookies = new Cookies();
        if (!cookies['access_token']) {
          cookies.set('access_token', accessToken, { path: '/' });
        }

        if (!cookies['refresh_token']) {
          cookies.set('refresh_token', refreshToken, { path: '/' });
        }

        window.location.reload();
      } else {

        toast.error(messageResponse);
      }
    } catch (error) {
      toast.error(MESSAGE.GENERIC_ERROR);
      console.log(error);
    } finally {
    }
  };

  return (
      <div className="modal fade show" id="modal-auth" tabIndex="-1" aria-labelledby="exampleModalLabel"
           style={{ display: 'block', paddingLeft: '0px' }} aria-modal="true" role="dialog">
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content" style={{borderRadius:"5px"}}>
            <div className="modal-body">
              <div className="title-header-wrap"  style={{marginTop:"5px"}}>
                <span className="title">{LOGIN.LOGIN}</span>
                <button type="button" className="btn-close pointer-cursor" data-bs-dismiss="modal" aria-label="Close" onClick={handleButtonCloseClick}></button>
              </div>
              <div className="form-wrap">
                <form method="POST" className="form" id="form-login" onSubmit={handleLoginFormSubmit}>
                  <input type="hidden" name="_token" value="kd38LX3442ZoaFGkcWgeVWKJ0xwLrIk5YxQOdqzJ" />
                  <div className="input-wrap">
                    <label className="title">{LOGIN.PHONE}</label>
                    <input id="phonenumber-login" name="phonenumber" type="text" placeholder={LOGIN.PHONE_PLACEHOLDER}
                           onChange={(e) => setPhoneNumber(e.target.value)}
                    />
                  </div>
                  <span className="text-danger error-text email-error"></span>
                  <div className="input-wrap input-password-wrap">
                    <label className="title">{LOGIN.PASSWORD}</label>
                    <input id="password-login" name="password" className="input-password" type="password"
                           placeholder={LOGIN.PASSWORD_PLACEHOLDER}
                           onChange={(e) => setPassword(e.target.value)}
                    />
                  </div>
                  <span className="text-danger error-text password-error"></span>
                  <div className="tool-wrap">
                    <span className="title btn-open-fotgot-password"
                          style={{fontSize:"13px", color:"#294780"}}
                          onClick={() => handleSwitchToOtherDialog(DIALOGS.FORGOT_PASSWORD)}
                    >{LOGIN.FORGOT_PASSWORD_QUESTION}</span>
                  </div>
                  <div className="btn-wrap">
                    <button type="submit" className="btn btn-primary btn-login">
                      {LOGIN.LOGIN_BUTTON}
                    </button>
                  </div>
                </form>
              </div>
              <div className="register-wrap" style={{margin:"50px 0 20px 0"}}>
                <span className="title">
                  {LOGIN.NO_ACCOUNT_QUESTION}
                  <span className="btn-register" onClick={() => handleSwitchToOtherDialog(DIALOGS.REGISTER)}> {LOGIN.REGISTER_HERE}</span>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}

export default LoginDialog;
