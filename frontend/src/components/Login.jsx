import React from "react";
import { Link } from "react-router-dom";
import { login } from "../js/Login";

const Login = () => {
  return (
    <div>
      <form>
        <h3>Sign In</h3>
        <div className="mb-3">
          <label>Username</label>
          <input
            id="logUsername"
            type="email"
            className="form-control"
            placeholder="Enter username"
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            id="logPassword"
            type="password"
            className="form-control"
            placeholder="Enter password"
          />
        </div>
        <div className="mb-3">
          <div className="custom-control custom-checkbox">
            <input
              type="checkbox"
              className="custom-control-input"
              id="customCheck1"
            />
            <label className="custom-control-label" htmlFor="customCheck1">
              Remember me
            </label>
          </div>
        </div>
        <div className="d-grid">
          <button type="button" className="btn btn-primary" onClick={login}>
            Submit
          </button>
        </div>
        <p className="forgot-password text-right">
          Forgot <Link to={"/"}>password?</Link> | New User click{" "}
          <Link to={"/sign-up"}>here</Link>
        </p>
      </form>
    </div>
  );
};

export default Login;
