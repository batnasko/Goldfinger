import React from "react";
import spinner from "./spinner.svg";
import spinnerStyle from "./spinner.css";


const Spinner = props =>
    <div className="spinnerContainer">
        <img src={spinner}/>
    </div>;

export default Spinner;