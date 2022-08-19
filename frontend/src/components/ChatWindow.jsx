import React from "react";
import "../css/ChatWindow.css";

const ChatWindow = () => {
  return (
    <div className="chatWindow">
      <div className="header">
        <span>
          To: <span className="name">Dog Woofson</span>
        </span>
      </div>
      <div className="chat" data-chat="person1">
        <div className="conversation-start">
          <span>Today, 6:48 AM</span>
        </div>
        <div className="bubble you">Hello,</div>
        <div className="bubble you">it's me.</div>
        <div className="bubble you">I was wondering...</div>
      </div>
      <div className="footer">
        <div className="sendInput">
          <div className="form-group inp">
            <input
              type="text"
              className="form-control"
              placeholder="Type a message"
            />
          </div>
          <div className="sendBtn">
            <button type="button" className="btn btn-primary">
              send
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatWindow;
