import SockJS from "sockjs-client";
import Stomp from "stompjs";
import configData from "../configurations/config.json";

var stompClient = null;

function connectWebsocket() {
  let sockJs = new SockJS(configData.WEBSOCKET_SERVER_URL + "/pullmessage");
  stompClient = Stomp.over(sockJs);
  const data = {
    username: "test1",
  };
  stompClient.connect(data, function (frame) {
    console.log("connected successfully");
    stompClient.subscribe("/user/queue/messages", (message) => {});
    stompClient.subscribe("/topic/availableUsers", (message) => {});
  });
  stompClient.reconnect_delay = 5000;
}

export function getStompClient() {
  if (stompClient === null) {
    connectWebsocket();
  }
  return stompClient;
}
