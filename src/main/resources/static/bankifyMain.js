console.log('js loaded');


  let createClientButton = document.getElementById('create-client-btn');
  let firstNameInput = document.getElementById('first-name')
  let lastNameInput = document.getElementById('last-name')
  let firstNameErr = document.querySelector('.firstname-err')
  let lastNameErr = document.querySelector('.lastname-err')
  console.log(firstNameInput);
  console.log(lastNameInput);
  console.log(firstNameErr);
  console.log(lastNameErr);
  
createClientButton.addEventListener('click', function (e) {
  
    console.log('click');
    if(firstNameInput.value.length === 0) {
      firstNameErr.classList.remove('d-none')
    } else {
      firstNameErr.classList.add('d-none')
    }
    if(lastNameInput.value.length === 0) {
      !lastNameErr.classList.remove('d-none')
    } else { 
      lastNameErr.classList.add('d-none')
    }

    if(firstNameInput.value.length > 0 && lastNameInput.value.length > 0) {
      toggleButton(this);
      let client = {
        firstName: firstNameInput.value,
        lastName: lastNameInput.value
      }
      
      /*
      addBankClient(client).then(getClientById).then(newClient => {
        // SHOW NEW CLIENT HERE

      })*/
      addBankClient(client).then((result) => {
        //console.log(result.text())
        
        if (result.status === 200) {
          console.log(client.firstName + " " + client.lastName + " added to the database")
          emptyFields([firstNameInput, lastNameInput])
          toggleButton(this);
          notify(client.firstName + " " + client.lastName + " added to the database", 'bg-success')
        } else {
          throw new Error();
        }
      }).catch(e => {
        console.log('SOMETHING WENT WRONG')
        toggleButton(createClientButton);
        notify("Something went wrong", 'bg-warning')
      })
    }
  })

  /*
  let iWillHaveNewJob = new Promise(
    function (resolve, reject) {
      if(iHaveNewJob()) {
        let greatSuccess = { beverage: 'Champagne' };
        resolve(greatSuccess); // Executing callback 1
      } else {
        let error = new Error('Drinking Saku Läte...');
        reject(error); // Executing callback 2
      }
  });*/

  // NAVBAR LINKS CONTROL
let clientNav = document.getElementById("nav-link-clients");
clientNav.addEventListener('click', function () {
  mainSpinner();
  clearMain('clientsPage')
})


  


  // SOME FUNCTIONS

  // ANIMATION PROMISE
  const animateCSS = (element, animation, length, prefix = 'animate__') =>
  // We create a Promise and return it
  new Promise((resolve, reject) => {
    const animationName = `${prefix}${animation}`;
    const animationLength = `${prefix}${length}`;
    const node = document.querySelector(element);

    node.classList.add(`${prefix}animated`, animationName, animationLength);

    // When the animation ends, we clean the classes and resolve the Promise
    function handleAnimationEnd() {
      node.classList.remove(`${prefix}animated`, animationName, animationLength);
      resolve('Animation ended');
    }

    node.addEventListener('animationend', handleAnimationEnd, {once: true});
  });


  // CLEAR MAIN AREA
const clearMain = (action) => {
    animateCSS('.main-theme', 'fadeOut', 'faster').then((message) => {
      // Do something after the animation

      console.log(message)
      if (action == 'clientsPage') {
        document.querySelector('.main-theme').remove();
        getClientsPage().then(result => {
          result.json().then(result => {
            console.log(result[0])
            let clients = result[0]
            let html = "<div class='main-theme mt-5 container animate__animated animate__fadeIn animate__faster'>" //<h1>TEST TEST TEST</h1></div>"
            
            let content = "";
            for (let client of clients) {
              console.log(client)
              content += "<div id='bankclient-" + client.id + "' class='bankclient-container default-shadow-border bg-main-theme row mb-3 py-3 px-1 justify-content-around'><div class='bankclient-id col-6 col-md-2'>ID: " + client.id + "</div><div class='bankclient-name col-6 col-md-2'><span>" + client.firstName + "</span> <span>" + client.lastName + "</span></div><div class='bankclient-registered col-12 col-md-2'>" + client.registered + "</div><div class='bankclient-accounts col-6 col-md-2'><button data-id='" + client.id + "' data-name='" + client.firstName + " " + client.lastName + "' class='btn btn-primary accounts-btn' data-toggle='modal' data-target='#bankaccounts-modal'>Accounts(" + client.numberofaccounts + ")</button></div><div class='bankclient-delete col-6 col-md-2'><button id='client-delete-btn' data-id='" + client.id + "' class='btn btn-danger'>Delete</button></div></div>"
            }
            html += content;
            html += "</div>"
            fillMain(html);
          })
        })

      }
      

      
    });
}
  
const fillMain = (html) => {
  mainSpinner();
  document.getElementById('main-theme').innerHTML += html;
  /*
  animateCSS('.main-theme', 'fadeIn', 'faster').then((message) => {
      // Do something after the animation
      console.log('FADEIN DONE')
  });*/
}

const fillAccountsModal = (clientID, accounts) => {
  document.getElementById('accounts-modal-content').innerHTML = "";
  for (let account of accounts) {
    let html = "<div class='bankaccount-container default-border p-1 mb-3' id='client-" + clientID + "-" + account.accountNr + "'><div><button class='btn btn-primary mb-1' type='button' data-toggle='collapse' data-target='#account-management-" + account.accountNr + "' aria-expanded='false' aria-controls='collapseExample'>Transactions</button></div><div class='collapse' id='account-management-" + account.accountNr + "'><div class='text-left mb-1 row'><div class='col-auto'><input type='number' class='form-control' id='deposit-sum' placeholder='0.00€' aria-describedby='deposit-sum'></div><div class='col align-self-end'><button class='deposit-bankaccount btn btn-success' data-accountnr='" + account.accountNr + "'>Deposit</button></div></div><div class='text-left mb-1 row'><div class='col-auto'><input type='number' class='form-control' id='withdraw-sum' placeholder='0.00€' aria-describedby='withdraw-sum'></div><div class='col align-self-end'><button class='withdraw-bankaccount btn btn-info' data-accountnr='" + account.accountNr + "'>Withdraw</button></div></div><div class='text-left mb-1 row'><div class='col-auto'><input type='number' class='form-control' id='transfer-sum' placeholder='0.00€' aria-describedby='transfer-sum'></div><div class='col-auto'><input type='text' class='form-control' id='transfer-account' placeholder='abcs12345' aria-describedby='transfer-account'></div><div class='col align-self-end'><button class='transfer-bankaccount btn btn-primary' data-accountnr='" + account.accountNr + "'>Transfer</button></div></div><div class='text-left mb-1'><button class='delete-bankaccount btn btn-warning' data-accountnr='" + account.accountNr + "'>Delete account</button></div></div><p id='account-" + account.accountNr + "'>Account number: " + account.accountNr + "</p><p id='account-" + account.accountNr + "-registered'>Created at " + account.registered + "</p><p id='account-" + account.accountNr + "-balance'>Balance: <span id='balance-'" + account.accountNr + "'>" + account.balance + "</span>€</p><div class=''><button class='btn btn-primary mb-1' type='button' data-toggle='collapse' data-target='#collapse-account-" + account.accountNr + "' aria-expanded='false' aria-controls='collapseExample'>History</button></div><div class='collapse' id='collapse-account-" + account.accountNr + "'>"
    
    
    //console.log(account)
    
    let collapseHTML = "";
    for (let transaction of account.history) {
      collapseHTML += "<div class='card card-body'><div class='transaction'><p>" + transaction.date + "</p><p>" + transaction.transaction + "</p></div></div>"
    }
    html += collapseHTML;
    html += "</div></div>"

    document.getElementById('accounts-modal-content').innerHTML += html;
    }
  
  

}

  // TOGGLE BUTTON
const toggleButton = (button) => {
   button.disabled = !button.disabled
}

// TOGGLE SPINNER
const mainSpinner = () => {
  let body = document.getElementsByTagName('body')[0]
  if (document.getElementById('main-spinner') == null) {
    
    body.style.minHeight = '100vh'
    //body.style.width = '100vh'
    let spinnerParent = document.createElement('DIV')
    spinnerParent.id = 'main-spinner'
    let spinnerDiv = document.createElement('DIV')
    spinnerDiv.classList.add('spinner-border', 'text-success')
    spinnerDiv.setAttribute('role', 'status');
    let hiddenSpinner = document.createElement('SPAN')
    hiddenSpinner.classList.add('visually-hidden')
    hiddenSpinner.innerText = 'Loading...'
    spinnerDiv.append(hiddenSpinner)
    spinnerParent.append(spinnerDiv)

    document.getElementsByTagName('body')[0].append(spinnerParent)
      
    //"<div id='main-spinner'><div class='spinner-border text-success' role='status'><span class='visually-hidden'>Loading...</span></div></div>";
  } else {

    document.getElementById('main-spinner').remove();
    body.style.minHeight = 'auto'
  }
  
}
  
// EMPTY INPUT FIELDS (ARRAY OF INPUTS)
const emptyFields = (fields) => {
  if (Array.isArray(fields)) {
    for (let field of fields) {
      field.value = '';
    }
  }
}

// NOTIFY
const notify = (message, background) => {
  let notifyDiv = document.createElement('DIV')
    notifyDiv.classList.add('notify-div', background, 'default-shadow-border', 'animate__animated', 'animate__faster', 'animate__fadeInDown')
    //notifyParent.appendChild(notifyDiv)

    let notifyBody = document.createElement('DIV')
    notifyBody.classList.add('notify-body', 'row')
    notifyDiv.appendChild(notifyBody)

    let notifyMessage = document.createElement('DIV')
    notifyMessage.classList.add('col-9', 'text-left', 'notify-message')
    let messageP = document.createElement('P')
    messageP.classList.add('pl-3', 'mb-0')
    messageP.innerHTML = message
    //notifyMessage.innerHTML = message
    notifyMessage.appendChild(messageP)
    notifyBody.appendChild(notifyMessage)

    let notifyCloseDiv = document.createElement('DIV')
    notifyCloseDiv.classList.add('col-3', 'text-center', 'notify-close-div', 'defaultBorders', 'p-0', background)
    notifyBody.appendChild(notifyCloseDiv)

    let notifyClose = document.createElement('A')
    notifyClose.classList.add('notify-close', 'default-border', 'm-0')
    notifyClose.innerHTML = '&#10006';
    notifyCloseDiv.appendChild(notifyClose)

  // CHECK IF NOTIFY-PARENT EXISTS
  if (document.getElementById('notify-parent') === null) {
    // append to body
    let notifyParent = document.createElement('DIV');
    notifyParent.id = 'notify-parent'
    notifyParent.appendChild(notifyDiv)

    document.getElementsByTagName('body')[0].appendChild(notifyParent)
  } else {
    let notifyParent = document.getElementById('notify-parent')
    notifyParent.appendChild(notifyDiv)

  }
  
}

//notify('HEI', 'bg-primary')
// CLOSE NOTIFY && CLOSE SIDE-NAV(PRAEGUSEKS MAHA VÕETUD)
let accountActions = ['deposit', 'withdraw', 'transfer', 'delete'];
document.querySelector('body').addEventListener('click', function (e) {
  console.log(e.target)
  if (e.target.classList.contains('notify-close') ) {
    e.target.closest('.notify-div').remove();
  }
  // OPEN MODAL
  if (e.target.classList.contains('accounts-btn')) {
    
    let clientID = e.target.getAttribute('data-id')
    let clientName = e.target.getAttribute('data-name')
    document.getElementById('accounts-owner').innerHTML = clientName
    let accountsBody = document.getElementById('accounts-modal-content')
    
    getBankAccountsByClientID(clientID).then(result => result.json().then(accounts => {
      console.log(accounts)
      fillAccountsModal(clientID, accounts)
    }))
    
    // DEPOSIT
    if (e.target.classList.contains('deposit-bankaccount')) {
      
    }
    
    //WITHDRAW
    if (e.target.classList.contains('withdraw-bankaccount')) {
      
    }
    //TRANSFER 
    if (e.target.classList.contains('transfer-bankaccount')) {
      
    }
    //DELETE BANKACCOUNT
    if (e.target.classList.contains('delete-bankaccount')) {
      
    }
  }

}, true) // Use Capturing







// FETCHES
// http://217.159.217.51:8080
  // FETCH POST NEW CLIENT
  function addBankClient(client){
    return fetch('/bank/client',  {
      method:'POST',
      headers:{
        'Content-Type':'application/json'
      },
      body:JSON.stringify(client)
    })
}
  

// GET CLIENTS PAGE & ALL CLIENTS

function getClientsPage() {
  return fetch('/bank/clients', {
    method: 'GET',
    headers: {
      'Content-Type':'application/json'
    }
  })
}

// GET ACCOUNT BY CLIENTID
function getBankAccountsByClientID(clientID) {
  return fetch('/bank/clients/' + clientID + '/accounts', {
    method: 'GET',
    headers: {
      'Content-Type':'application/json'
    }
  })
}

// DEPOSIT TO ACCOUNT
function depositToAccount(accountNr) {
  return fetch('/bank/deposit/' + accountNr), {
    method: 'PUT',
    header: {
      'Content-Type':'application/json'
    },
    body: JSON.stringify(amount)
  }
}