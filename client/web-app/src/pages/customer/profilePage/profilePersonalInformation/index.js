import { useEffect, useState} from "react";
import './style.scss';

import {useCookies} from 'react-cookie';

import {toast} from "react-toastify";
import {useLocation, useNavigate} from "react-router-dom";
import queryString from "query-string";
import {API, ERROR, MESSAGE, PROFILE_PAGE, USER_ROLE} from "@Const";
import {Skeleton} from "antd";


const ProfilePersonalInformationPage = () => {
  const [cookies] = useCookies(['access_token']);
  const accessToken = cookies.access_token;

  const location = useLocation();
  const queryParams = queryString.parse(location.search);
  const [userID, setUserID] = useState(queryParams.userID);

  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState();

  const navigate = useNavigate();

  const fetchData = async () => {
    const apiUrl = "http://localhost:8000/api/user/get-user-by-id/" + userID;
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
        setUser(dataResponse);
      } else {
        toast.error(messageResponse)
        console.log(messageResponse);
      }
    } catch (error) {
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchData().then(r => {});
  }, []);

  const handleInfoFormSubmit = async (e) => {
    e.preventDefault();
    console.log(user);

    const apiUrl = "http://localhost:8000/api/user/edit-user";
    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
          "Authorization": `Bearer ${accessToken}`,
          "Content-Type": "application/json"
        },
      });

      let jsonResponse = await response.json();
      const dataResponse = jsonResponse.data;
      const messageResponse = jsonResponse.message;

      if (response.ok) {
        toast.success(messageResponse);

        setTimeout(function() {
          window.location.reload();
        }, 1000);
      } else {
        toast.error(messageResponse)
        console.log(messageResponse);
      }
    } catch (error) {
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  }

  return (
      <div className="col-8 content-children item-row">
        <div className="information-wrap">
          <div className="header-wrap">
            <span className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.EDIT_PERSONAL_INFORMATION}</span>
          </div>

          { isLoading ? <Skeleton active /> :
            <>
              <div className="form-wrap">
                <form method="POST" className="form" id="form-login" onSubmit={handleInfoFormSubmit}>
                  <input type="hidden" name="_token" value="3b5uU0DbQ1xoXiDiljwxaFX7Pa9usSichthgGiHt"/>

                  <div className="input-wrap">
                    <label className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.FULL_NAME}</label>
                    <span className="error--message"></span>
                    <input
                      type="text"
                      placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.FULL_NAME_PLACEHOLDER}
                      className="input__info input"
                      name="name"
                      value={user?.fullName || ''}
                      onChange={(e) => setUser(prevUser => ({...prevUser, fullName: e.target.value}))}
                    />
                  </div>
                  <div className="input-wrap">
                    <label className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.ROLE}</label>
                    <input
                      className="input"
                      type="email"
                      placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.ROLE_PLACEHOLDER}
                      readOnly
                      disabled
                      value={user?.role || ''}
                    />
                  </div>
                  <div className="input-wrap">
                    <label className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.DESIGNATION}</label>
                    <input
                      className="input"
                      type="email"
                      placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.DESIGNATION_PLACEHOLDER}
                      readOnly
                      disabled
                      value={user?.designation || ''}
                    />
                  </div>
                  <div className="input-wrap">
                    <label className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.PHONE_NUMBER}</label>
                    <span className="error--message"></span>
                    <input
                      readOnly
                      disabled
                      type="text"
                      placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.PHONE_NUMBER_PLACEHOLDER}
                      className="input__info input"
                      name="phoneNumber"
                      value={user?.phoneNumber || ''}
                    />
                  </div>

                  <div className="input-wrap">
                    <label className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.EMAIL}</label>
                    <span className="error--message"></span>
                    <input
                      type="text"
                      placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.EMAIL_PLACEHOLDER}
                      className="input__info input"
                      name="email"
                      value={user?.email || ''}
                      onChange={(e) => setUser(prevUser => ({...prevUser, email: e.target.value}))}
                    />
                  </div>

                  {user && user.role == USER_ROLE.BUSINESS &&
                    <>
                      <div className="input-wrap">
                        <label
                          className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.REPRESENTATIVE}</label>
                        <input
                          type="text"
                          placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.REPRESENTATIVE_PLACEHOLDER}
                          className="input__info input"
                          value={user?.managerName || ''}
                          onChange={(e) => setUser(prevUser => ({...prevUser, managerName: e.target.value}))}
                        />
                      </div>
                      <div className="input-wrap">
                        <label
                          className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.BUSINESS_LICENSE_NUMBER}</label>
                        <input
                          type="text"
                          placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.BUSINESS_LICENSE_NUMBER_PLACEHOLDER}
                          className="input__info input"
                          value={user?.businessLicenseNumber || ''}
                          onChange={(e) => setUser(prevUser => ({
                            ...prevUser,
                            businessLicenseNumber: e.target.value
                          }))}
                        />
                      </div>
                      <div className="input-wrap">
                        <label
                          className="title">{PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.ADDRESS}</label>
                        <input
                          type="text"
                          placeholder={PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.ADDRESS_PLACEHOLDER}
                          className="input__info input"
                          value={user?.address || ''}
                          onChange={(e) => setUser(prevUser => ({...prevUser, address: e.target.value}))}
                        />
                      </div>
                    </>
                  }
                  <div className="btn-wrap" style={{marginBottom: "20px"}}>
                    <button type="submit" className="btn btn-primary btn-save-information">
                      {PROFILE_PAGE.PROFILE_PERSONAL_INFORMATION_PAGE.SAVE_INFORMATION}
                    </button>
                  </div>

                </form>
              </div>

            </>
          }

        </div>
      </div>
  );
}

export default ProfilePersonalInformationPage;