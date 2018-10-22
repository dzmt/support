'use strict';

(function () {
  const MODAL_SELECTOR = '.modal';
  const MODAL_INPUT_SELECTOR = '.modal__input-name';
  const MODAL_BTN_SELECTOR = '.modal .btn';
  const MODAL_COUNT_SELECTOR ='.modal__input-count';
  
  let start = window.backend.start;
  let USER = window.backend.USER;

  const modal = document.querySelector(MODAL_SELECTOR);
  const modalInput = document.querySelector(MODAL_INPUT_SELECTOR);
  const modalBtn = document.querySelector(MODAL_BTN_SELECTOR);
  const modalCount = document.querySelector(MODAL_COUNT_SELECTOR);

  let clickHandler = evt => {
    let name = modalInput.value;
    let count = modalCount.value;

    if (name && count) {
      USER.name = name;
      USER.count = count;
      modal.classList.toggle('hidden');

      modalInput.removeEventListener('keydown', enterKeydownHandler);
      start();
    } else {
      if (!name) {
        modalInput.style.borderColor = 'red';
      } else if (!count) {
        modalCount.style.borderColor = 'red';
      }
    }
  }

  let enterKeydownHandler = function(evt) {
    if(evt.keyCode === 13) {
     clickHandler();
    }
  }


  modalInput.addEventListener('focus', (evt) => {
    evt.currentTarget.style.borderColor = '';

    modalInput.addEventListener('keydown', enterKeydownHandler)
  })

  modalCount.addEventListener('focus', (evt) => {
    evt.currentTarget.style.borderColor = '';
  })
  
  modalBtn.addEventListener('click', clickHandler)
})();