var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#greetings").html("");
}

function connect() {
  var socket = new SockJS("/pullmessage");
  stompClient = Stomp.over(socket);
  stompClient.connect(
    { username: localStorage.getItem("username") },
    function (frame) {
      setConnected(true);
      console.log("Connected: " + frame);
      stompClient.subscribe("/user/queue/messages", function (greeting) {
        showGreeting(JSON.parse(greeting.body).message);
      });
    }
  );
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  $("#status").html("offline");
  $("#status").removeClass("bg-success");
  $("#status").addClass("bg-danger");

  setConnected(false);
  localStorage.removeItem("username");
  console.log("Disconnected");
}

function sendName() {
  let messageBody = {
    message: $("#name").val(),
    type: "MESSAGE",
    sentDateTime: new Date().toUTCString(),
    senderId: localStorage.getItem("username"),
    receiverId: "62fa684955ebf12bf61f423f",
  };
  stompClient.send("/app/put/message", {}, JSON.stringify(messageBody));
}

function showGreeting(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function login() {
  let userName = $("#emailInp").val();
  let password = $("#passwordInp").val();
  if (userName === null || password === null) {
    alert("both username or password required");
  }

  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");
  myHeaders.append("Access-Control-Allow-Origin", "*");
  myHeaders.append("mode", "cors");

  var raw = JSON.stringify({
    username: userName,
    password: password,
  });

  var requestOptions = {
    method: "POST",
    headers: myHeaders,
    body: raw,
    redirect: "follow",
  };

  fetch("http://localhost:8081/api/v1/login", requestOptions)
    .then((response) => response.json())
    .then((result) => {
      if (result.statusCode === 200) {
        localStorage.setItem("username", result.messageId);
        connect();
        $("#emailInp").val("");
        $("#passwordInp").val("");
        $("#status").html("online");
        $("#status").removeClass("bg-danger");
        $("#status").addClass("bg-success");
      }
    })
    .catch((error) => console.log("error", error));
}

$(function () {
  $("form").on("submit", function (e) {
    e.preventDefault();
  });
  $("#login").click(function () {
    login();
  });
  $("#connect").click(function () {
    connect();
  });
  $("#logout").click(function () {
    disconnect();
  });
  $("#send").click(function () {
    sendName();
  });
});
