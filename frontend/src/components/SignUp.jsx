import React from "react";
import { Link } from "react-router-dom";
import { registerUser } from "../js/SignUp";

const SignUp = () => {
  return (
    <div>
      <form>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>Username</label>
          <input id="signUpUsername" type="text" className="form-control" placeholder="Username" />
        </div>
        <div className="mb-3">
          <label>Email address</label>
          <input
            id="signUpEmailAddress"
            type="text"
            className="form-control"
            placeholder="Enter Email"
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            id="signUpPassword"
            type="password"
            className="form-control"
            placeholder="Enter password"
          />
        </div>
        <div className="mb-3">
          <label>Confirm password</label>
          <input
            id="signUpConfPassword"
            type="text"
            className="form-control"
            placeholder="Enter password again"
          />
        </div>
        <div className="d-grid">
          <button type="button" className="btn btn-primary" onClick={registerUser}>
            Sign Up
          </button>
        </div>
        <p className="forgot-password text-right">
          Already registered <Link to={"/sign-in"}>sign in?</Link>
        </p>
      </form>
    </div>
  );
};

export default SignUp;
