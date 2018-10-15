'use strict';

(function() {
  const PARTNER_CLASS = 'partner__item';

  const MESSAGE_CLASS = 'message';
  const MESSAGE_CURRENT_CLASS = 'message-current';
  const MESSAGE_REMOTE_CLASS = 'message-remote'; 

  const DIALOG_CLASS = 'dialog__item';

  let getDialogId = function (tabId) {
    return tabId + '-dialog';
  }

  let getMessage = function() {
    let p = document.createElement('p');
    p.classList.add(MESSAGE_CLASS);
    return p;
  }

  const getCurrentMessage = function() {
    let message = getMessage();
    message.classList.toggle(MESSAGE_CURRENT_CLASS);
    return message;
  }
  
  const getRemoteMessage = function() {
    let message = getMessage();
    message.classList.toggle(MESSAGE_REMOTE_CLASS);
    return message;
  }
  
  let createTab = function(id, name) {
    let li = document.createElement('li');
    li.classList.add(PARTNER_CLASS);
    li.id = id;
    li.textContent = name;

    return li;
  }

  let createDialog = function(id) {
    let li = document.createElement('li');
    li.classList.add(DIALOG_CLASS);
    li.id = getDialogId(id);
    
    return li;
  }

  let createMessage = function(type, text) {
    let p = null;
    if(type === 'remote') {
      p = getRemoteMessage();
    } else if (type === 'current') {
      p = getCurrentMessage();
    }
    p.textContent = text;
    return p;
  }

  window.util = {
    createTab: createTab,
    createDialog: createDialog,
    createMessage: createMessage,
    getDialogId: getDialogId
  };

})();