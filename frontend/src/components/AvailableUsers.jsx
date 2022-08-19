import React from "react";
import "../css/AvailableUser.css";
import MessageCard from "./MessageCard";

const AvailableUsers = () => {
  return (
    <React.Fragment>
      <div className="messagesTable">
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
