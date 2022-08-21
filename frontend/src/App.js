import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import React, { useState } from "react";
import AvailableUsers from "./components/AvailableUsers";
import ChatWindow from "./components/ChatWindow";
import HomePage from "./components/HomePage";

function App() {
  const [showAuthPage, setShowAuthPage] = useState(true);
  const [showHomePage, setHomePage] = useState(true);

  function updateHomePageVar(value) {
    setHomePage(value);
  }

  return (
    // <div>
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <React.Fragment>
              <Link
                className="navbar-brand"
                to={"/"}
                onClick={() => setHomePage(true)}
              >
                chatApp
              </Link>
              {showAuthPage ? (
                <React.Fragment>
                  <div className="search-bar">
                    <input
                      type="text"
                      placeholder="Search..."
                      spellcheck="false"
                      data-ms-editor="true"
                    />
                  </div>

                  <div className="user-settings">
                    <img
                      className="user-profile"
                      src="https://firebasestorage.googleapis.com/v0/b/regex-query-tool.appspot.com/o/user%20(1).png?alt=media&token=db2a2b12-7281-4fa3-b450-f692ba298cbc"
                      alt=""
                    />
                  </div>
                </React.Fragment>
              ) : null}
            </React.Fragment>
          </div>
        </nav>
        <div className="window">
          {console.log("show auth page", showAuthPage)}
          {console.log("show home page", showHomePage)}
          {showAuthPage ? (
            <React.Fragment>
              <AvailableUsers />
              <ChatWindow />
            </React.Fragment>
          ) : (
            <>
              {showHomePage ? (
                <React.Fragment>
                  {/* <HomePage childProp={updateHomePageVar} /> */}
                  <Routes>
                    <Route
                      path="/"
                      element={<HomePage childProp={updateHomePageVar} />}
                    />
                  </Routes>
                </React.Fragment>
              ) : (
                <div>
                  <div className="auth-wrapper">
                    <div className="auth-inner">
                      <Routes>
                        <Route path="/sign-in" element={<Login />} />
                        <Route path="/sign-up" element={<SignUp />} />
                      </Routes>
                    </div>
                  </div>
                </div>
              )}
            </>
          )}
        </div>
      </div>
    </Router>
  );
}

export default App;
