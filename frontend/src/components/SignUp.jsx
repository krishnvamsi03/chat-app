import React from "react";

const SignUp = () => {
  return (
    <div>
      <form>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>Username</label>
          <input
            type="text"
            className="form-control"
            placeholder="Username"
          />
        </div>
        <div className="mb-3">
          <label>Email address</label>
          <input type="text" className="form-control" placeholder="Enter Email" />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter password"
          />
        </div>
        <div className="mb-3">
          <label>Confirm password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password again"
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary">
            Sign Up
          </button>
        </div>
        <p className="forgot-password text-right">
          Already registered <a href="/sign-in">sign in?</a>
        </p>
      </form>
    </div>
  );
};

export default SignUp;
