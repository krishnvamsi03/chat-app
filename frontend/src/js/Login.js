import configData from "../configurations/config.json";
import axios from "axios";
import { getStompClient } from "./StompConnection";

const usernameInpId = "logUsername";
const passwordInpId = "logPassword";
const tokenKey = "chatJWTToken";

export function sendMessage() {
  const headers = {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem(tokenKey),
  };

  const data = {
    message: "hello",
    type: "MESSAGE",
    sentDateTime: new Date().toUTCString(),
    senderId: "",
    receiverId: "",
  };

  axios
    .post(configData.WEBSOCKET_SERVER_URL, data, {
      headers: headers,
    })
    .then((response) => {
      if (response.data["message"] === "INVALID_TOKEN") {
        alert("session expired, please login again");
      }
    });
}

export function login() {
  const username = document.getElementById(usernameInpId).value;
  const password = document.getElementById(passwordInpId).value;
  loginWithParams(username, password);
}

export function loginWithParams(username, password) {
  const headers = {
    Authorization: "Basic " + btoa(username + ":" + password),
  };

  axios
    .post(configData.AUTH_SERVER_URL + "/authenticate", null, {
      headers: headers,
    })
    .then((response) => {
      if (response && response.data) {
        localStorage.setItem(tokenKey, response.data["token"]);
        getStompClient();
        window.location.href = "/";
        return;
      }
    });
}
