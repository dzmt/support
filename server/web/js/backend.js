'use strict';

(function () {
  let user = window.user;
  let chat = window.chat;
  let getDialogId = window.util.getDialogId;

  let INPUT_SELECTOR = '.input-message';
  let SEND_SELECTOR = '.btn-send';
  let LEAVE_SELECTOR = '.btn-leave';

  const MODAL_ERROR_SELECTOR = '.modal-error';
  const ERROR_BUTTON = '.btn-error';

  let COUNTER = 0;

  const USER = {
    
  };

  const input = document.querySelector(INPUT_SELECTOR);
  const sendButton = document.querySelector(SEND_SELECTOR);
  const leaveExitButton = document.querySelector(LEAVE_SELECTOR);

  const modalError = document.querySelector(MODAL_ERROR_SELECTOR);
  const errorButton = document.querySelector(ERROR_BUTTON);

  let socket = null;

  

  let scrollDialog = function () {
    // let activeDialog = document.querySelector('.dialog__item-active');
    let activeDialog = window.activeDialog;
    activeDialog.scrollTop = activeDialog.scrollHeight;
  }

  let clickSendButtonHandler = evt => {
    let text = input.value;
    if (text) {
      let message = chat.createCurrentMessage(USER.id, text)
    
      socket.send(JSON.stringify(message));
      input.value = '';
      scrollDialog();
    }
  }

  let clickLeaveExitHandler = evt => {
    let type = evt.target.textContent.toLowerCase();
    let message = null;

    if (type === 'leave') {
      let to = window.activeTab.id;
      chat.removeTab(to);
      message = {type: 'LEAVE', text: 'exit', from: USER.id, to: to};
    } else if (type === 'exit') {
      message = {type: 'EXIT', text: 'exit', from: USER.id};
      window.history.back();
    }
    socket.send(JSON.stringify(message));
  }

  leaveExitButton.addEventListener('click', clickLeaveExitHandler);

  sendButton.addEventListener('click', clickSendButtonHandler);

  document.addEventListener('keydown', (evt) => {
    if (evt.keyCode === 13 && input === document.activeElement) {
      clickSendButtonHandler();
    }
  })

  let handleContentMessage = function(message) {
    let from = message.from;
    let text = message.text;
    chat.showRemoteMessage(from, text);
    scrollDialog();
  }

  let serverMessage = function (text) {
    if (COUNTER !== 0) {
      chat.showRemoteMessage(chat.serverTab.id, text);
    }
    COUNTER++;
    scrollDialog();
  }

  let handleUserMesaage = function (message) {
    let tokens = message.text.split(" ");
    USER.id = tokens[0];
    USER.name = tokens[1];
    serverMessage(user.REGISTERED_MESSAGE);
    scrollDialog();
  }

  let handleInterlocutorMessage = function (message) {
    let tokens = message.text.split(" ");
    let id = tokens[0];
    let name = tokens[1];
    chat.createInterlocutor(id, name);
    scrollDialog();
  }

  let handleLeaveMessage = function(message) {
    chat.removeTab(message.from);
  }

  let handleExitMessag = function (message) {
    chat.removeTab(message.from);
  }
  

  let dispatcher = {
    CONTENT: handleContentMessage,
    LEAVE: handleLeaveMessage,
    EXIT: handleExitMessag,
    USER: handleUserMesaage,
    INTERLOCUTOR: handleInterlocutorMessage
  };

  socket = new WebSocket(user.WEBSOCKET_URL);

  socket.addEventListener('message', function (evt) {
      
    let message = JSON.parse(evt.data);
    if (message.type === 'CONTENT' && !message.from) {
      serverMessage(message.text);
    } else {
      dispatcher[message.type](message);
    }
    
  });
  
  socket.addEventListener('error', (evt) => {
    console.log('error');
    modalError.classList.remove('hidden');
  });

  socket.addEventListener('close', (evt) => {
    modalError.classList.remove('hidden');
  });

  let startChat = function() {
    let registerMessage = {
      type: 'REGISTER',
      text: USER.name + ' ' + USER.count
    };
    
    socket.send(JSON.stringify(registerMessage));
  }

  errorButton.addEventListener('click', evt => {
    window.location.href = 'index.html';
  })

  window.backend = {
    USER: USER,
    start: startChat
  }
})();