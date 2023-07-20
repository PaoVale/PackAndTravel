document.addEventListener("DOMContentLoaded", function () {
  // Codice JavaScript per gestire il clic sul pulsante "Aggiungi al carrello"
  function aggiungiAlCarrello(event) {
    var cartTotalElement = document.getElementById("cart-total");
    var dataIdValue = cartTotalElement.getAttribute("data-id");
    var dataIdNumber = parseFloat(dataIdValue);
    console.log("Il valore di data-id è:", dataIdNumber);

    var pulsante = event.target;
    var quantityElement = pulsante.parentNode.querySelector(".quantity-value");
    var quantita = parseInt(quantityElement.innerText);

    var prodottoId = pulsante.dataset.id;

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          prezzo = parseFloat(document.getElementById('prezzoProdotto'+prodottoId).innerHTML);
          document.getElementById('total'+prodottoId).innerHTML= (prezzo*quantita).toFixed(2);

          prezzoAggiornato = dataIdNumber + prezzo;
          console.log("Il valore aggiornato è:", prezzoAggiornato);
          document.getElementById('cart-total').innerHTML=((prezzoAggiornato).toFixed(2)+' &euro;');
          cartTotalElement.setAttribute("data-id", prezzoAggiornato);

          console.log("Prodotto aggiunto al carrello con successo.");
          
          // Abilita il pulsante "-" quando la quantità è maggiore di 1
          var pulsanteMinus = pulsante.parentNode.querySelector(".decrease-quantity");
          if (quantita > 1) {
            pulsanteMinus.removeAttribute("disabled");
          }
        } else {
          console.error("Errore durante l'aggiunta del prodotto al carrello.");
        }
      }
    };

    var dati = "idProdotto=" + encodeURIComponent(prodottoId);
    dati += "&action=add";

    xhr.open("POST", "/PackAndTravel/CarrelloServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(dati);
  }

  // Codice JavaScript per gestire il clic sul pulsante "Elimina dal carrello"
  function DecrementaDalCarrello(event) {
    var cartTotalElement = document.getElementById("cart-total");
    var dataIdValue = cartTotalElement.getAttribute("data-id");
    var dataIdNumber = parseFloat(dataIdValue);
    console.log("Il valore di data-id è:", dataIdNumber);

    var pulsante = event.target;
    var quantityElement = pulsante.parentNode.querySelector(".quantity-value");
    var quantita = parseInt(quantityElement.innerText);

    var prodottoId = pulsante.dataset.id;

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          prezzo = parseFloat(document.getElementById('prezzoProdotto'+prodottoId).innerHTML);
          document.getElementById('total'+prodottoId).innerHTML= (prezzo*quantita).toFixed(2);

          prezzoAggiornato = dataIdNumber - prezzo;
          console.log("Il valore aggiornato è:", prezzoAggiornato);
          document.getElementById('cart-total').innerHTML=((prezzoAggiornato).toFixed(2)+' &euro;');
          cartTotalElement.setAttribute("data-id", prezzoAggiornato);

          console.log("Prodotto decrementato dal carrello con successo.");
          
          // Disabilita il pulsante "-" quando la quantità è 1
          var pulsanteMinus = pulsante.parentNode.querySelector(".decrease-quantity");
          if (quantita === 1) {
            pulsanteMinus.setAttribute("disabled", "disabled");
          }
        } else {
          console.error("Errore durante il decremento del prodotto al carrello.");
        }
      }
    };

    var dati = "idProdotto=" + encodeURIComponent(prodottoId);
    dati += "&action=delete";

    xhr.open("POST", "/PackAndTravel/CarrelloServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(dati);
  }

  // Seleziona tutti gli elementi con la classe "increase-quantity" e aggiungi un gestore di eventi a ciascuno
  var pulsantiIncrease = document.getElementsByClassName("increase-quantity");
  for (var i = 0; i < pulsantiIncrease.length; i++) {
    pulsantiIncrease[i].addEventListener("click", function(event) {
      var quantityElement = event.target.parentNode.querySelector(".quantity-value");
      var currentQuantity = parseInt(quantityElement.innerText);
      quantityElement.innerText = currentQuantity + 1;

      aggiungiAlCarrello(event); // Chiamata alla funzione per aggiungere al carrello
    });
  }

  // Seleziona tutti gli elementi con la classe "decrease-quantity" e aggiungi un gestore di eventi a ciascuno
 var pulsantiDecrease = document.getElementsByClassName("decrease-quantity");
for (var i = 0; i < pulsantiDecrease.length; i++) {
  pulsantiDecrease[i].addEventListener("click", function(event) {
    var quantityElement = event.target.parentNode.querySelector(".quantity-value");
    var currentQuantity = parseInt(quantityElement.innerText);

    if (currentQuantity > 1) {
      quantityElement.innerText = currentQuantity - 1;
      DecrementaDalCarrello(event);
    } else {
      // Se la quantità è 1, disabilita il pulsante "-"
      event.target.disabled = true;
    }
  });
}
});
