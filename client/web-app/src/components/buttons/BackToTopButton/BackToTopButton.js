import React, { useEffect, useState } from "react";
import btnUp from "./images/up.svg";
import "./style.scss";

const BackToTopButton = () => {
  const [isVisible, setIsVisible] = useState(false);

  const handleClickBtnBackToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  const handleScroll = () => {
    const scrollTop = window.scrollY || document.documentElement.scrollTop;
    setIsVisible(scrollTop > 50);
  };

  useEffect(() => {
    window.addEventListener('scroll', handleScroll);

    handleScroll();

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
      <button
          type="button"
          className={`${isVisible ? "showBtn" : "hideBtn"}`}
          id="btn-back-to-top"
          style={{display: 'block', right: '25px', left: 'auto'}}
          onClick={handleClickBtnBackToTop}
      >
        <img src={btnUp} alt="Back to top" />
      </button>
  );
};

export default BackToTopButton;
