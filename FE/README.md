Una volta scaricato Visual Studio Code dal sito ufficiale, possiamo continuare con il download della repository del progetto.

Una volta clonata da Git o scaricata in formato zip possiamo procedere con l’installazione di Node.js
Potrebbe chiederci di dover installare chocolatey (package manager), noi acconsentiamo e procediamo.
Per vedere se node è stato correttamente installato digitiamo sul cmd o sul terminale questo comando node -v , se ci restituirà la versione di node.js lo avremmo installato.

Passiamo all’installazione di Angular 9, essa deve essere esattamente la 9.1.13 perchè il progetto è stato sviluppato con delle dipendenze specifiche per questa versione.
Apriamo il cmd o il terminale in modalità amministratore e digitiamo:
npm install -g @angular/cli@9.1.13

Una volta installato correttamente Angular 9, è possibile visualizzarne la corretta installazione digitando nel terminale ng version e vedere se ci restituisce la versione di Angular corretta.

Per finire apriamo la nostra directory di progetto tramite Visual Studio Code, apriamo un terminale o un cmd direttamente dentro questa directory, e digitiamo il comando npm install per scaricare tutte le librerie necessarie ad Angular.

Una volta terminato possiamo procedere e far partire l’applicazione digitando:    ng serve il quale farà partire il server del progetto Angular il quale molto probabilmente sarà situato presso  http://localhost:4200 , se non risulta tale indirizzo, controllare il terminale e visualizzare l’indirizzo da lui suggerito.

Eventualmente potrebbe dare un errore a causa del file “package.json” in questo caso è possibile risolvere il problema digitando il comando:                                        npm install --save-dev @angular-devkit/build-angular

Se è necessario cambiare la porta e l’indirizzo di collegamento tra back-end e front-end, all’interno del progetto recarsi sul file src/app/ajax.service.ts e cambiare l'URL o la porta tramite le variabili “url” e “port”.

Se tutto è andato a buon fine, aprendo l’applicazione web dal browser tramite l’indirizzo consigliato sul terminale ci ritroveremo sulla pagina di login.
