import React from "react";

const MessageCard = () => {
  return (
    <div>
      <li className="list-group-item d-flex justify-content-between align-items-start">
        <div className="ms-2 me-auto">
          <div className="fw-bold">Subheading</div>
          Cras justo odio
        </div>
        <span className="badge bg-primary rounded-pill">14</span>
      </li>
    </div>
  );
};

export default MessageCard;
