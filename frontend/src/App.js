import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import React, { useState } from "react";
import AvailableUsers from "./components/AvailableUsers";
import ChatWindow from "./components/ChatWindow";

function App() {
  const [showAuthPage, setShowAuthPage] = useState(true);

  return (
    // <div>
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link
              className="navbar-brand"
              to={"/"}
              onClick={() => setShowAuthPage(false)}
            >
              chatApp
            </Link>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link
                    className="nav-link"
                    to={"/sign-in"}
                    onClick={() => setShowAuthPage(true)}
                  >
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link
                    className="nav-link"
                    to={"/sign-up"}
                    onClick={() => setShowAuthPage(true)}
                  >
                    Sign up
                  </Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <div className="window">
          <AvailableUsers />
          <ChatWindow />
        </div>
      </div>
    </Router>
  );
}

export default App;
