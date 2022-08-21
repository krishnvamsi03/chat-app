import React from "react";
import "../css/ChatWindow.css";

const ChatWindow = () => {
  return (
    <div className="chatWindow">
      <div class="chat-area-header">
        <div class="chat-area-title">Test User</div>
      </div>
      <div className="chat" data-chat="person1">
        <div className="chat-msg">
          <div className="chat-msg-content">
            <div className="chat-msg-text">
              Luctus et ultrices posuere cubilia curae.
            </div>
            <div className="chat-msg-text">
              Neque gravida in fermentum et sollicitudin ac orci phasellus
              egestas. Pretium lectus quam id leo.
            </div>
          </div>
        </div>
        <div className="chat-msg-owner">
          <div className="chat-msg-content-owner">
            <div className="chat-msg-text-owner">
              Luctus et ultrices posuere cubilia curae.
            </div>
            <div className="chat-msg-text-owner">
              Neque gravida in fermentum et sollicitudin ac orci phasellus
              egestas. Pretium lectus quam id leo.
            </div>
          </div>
        </div>
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
