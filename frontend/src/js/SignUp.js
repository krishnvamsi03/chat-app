import configData from "../configurations/config.json";
import axios from "axios";
import { loginWithParams } from "./Login";

const usernameId = "signUpUsername";
const passwordId = "signUpPassword";
const emailAddressId = "signUpEmailAddress";
const confirmPasswordId = "signUpConfPassword";

export function registerUser() {
  let username = document.getElementById(usernameId).value;
  let password = document.getElementById(passwordId).value;
  let confirmPassword = document.getElementById(confirmPasswordId).value;
  let emailAddress = document.getElementById(emailAddressId).value;

  if (
    username.length === 0 ||
    password.length === 0 ||
    confirmPassword.length === 0 ||
    emailAddress.length === 0
  ) {
    alert("all field are required");
  }

  if (password !== confirmPassword) {
    alert("password not matching");
  }
  const data = {
    username: username,
    password: password,
    emailAddress: emailAddress,
  };
  axios.post(configData.AUTH_SERVER_URL + "/sign-up", data).then((response) => {
    if (response && response.data) {
      if (response.data["message"] === "USERNAME_TAKEN") {
        alert("Username taken");
      }
      loginWithParams(username, password);
      return;
    }
  });
}
