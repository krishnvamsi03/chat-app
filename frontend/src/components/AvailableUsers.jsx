import React from "react";
import "../css/AvailableUser.css";
import MessageCard from "./MessageCard";

const AvailableUsers = () => {
  return (
    <React.Fragment>
      <div className="messagesTable">
        <div className="message-header">
          <div className="message-heading">
            <span>Messages</span>
          </div>
          <div className="actions">
            <img
              className="available-user"
              src="https://firebasestorage.googleapis.com/v0/b/regex-query-tool.appspot.com/o/employee.png?alt=media&token=73c6ebe6-fc02-4132-a5cf-e10e52e53927"
              alt=""
              data-bs-toggle="tooltip"
              data-bs-placement="top"
              title="available friends"
            />
            <img
              className="create-group"
              src="https://firebasestorage.googleapis.com/v0/b/regex-query-tool.appspot.com/o/add-user.png?alt=media&token=cddf71c3-8470-4755-a992-66d1c528217f"
              alt=""
              data-bs-toggle="tooltip"
              data-bs-placement="top"
              title="create group"
            />
          </div>
        </div>
        <ul className="list-group">
          {[...Array(20)].map((x, i) => (
            <MessageCard />
          ))}
        </ul>
      </div>
    </React.Fragment>
  );
};

export default AvailableUsers;
