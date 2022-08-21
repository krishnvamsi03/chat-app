import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

const HomePage = (props) => {
  return (
    <div className="App">
      <div className="App-header">
        <h1>chatApp</h1>
        <h3>
          Search for your friends, have one to one chat, create groups, share
          file...
        </h3>
        <p>
          click{" "}
          <Link to={"/sign-in"} onClick={() => props.childProp(false)}>
            here
          </Link>{" "}
          to login or sign-up
        </p>
      </div>
    </div>
  );
};

export default HomePage;
