import configData from "../configurations/config.json";
import axios from "axios";

const tokenKey = "chatJWTToken";

export function sendMessage() {
  const headers = {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem(tokenKey),
  };
  let message = document.getElementById("").value;
  const data = {
    message: message,
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
