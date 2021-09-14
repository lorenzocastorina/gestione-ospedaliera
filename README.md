# gestione-ospedaliera
L’idea del progetto nasce da un’esigenza dei moderni ospedali. La mancanza di posti letto, la lentezza dei processi manuali e le lunghe attese dei pazienti ci hanno ispirato nella realizzazione di questo applicativo web semplice ed intuitivo, con il principale scopo di rendere la gestione ospedaliera più efficiente e performante, specialmente in un difficile periodo di pandemia.

## Description

Progetto nato per la tesi di laurea triennale in Ingegneria informatica del Politecnico di Milano. 
<br>Studenti: Lorenzo Castorina & Andrea Potì

Dal punto di vista client-side, l’operatore potrà interfacciarsi tramite un portale web disponibile sui browser più comuni. Proprio per questo la decisione di utilizzare Angular (versione 9.0), framework open-source per la creazione di applicazioni web, è stata la più opportuna, non solo per la possibilità di comporre progetti di elevata complessità, ma anche per le performance che permettono di renderizzare questo software anche tramite una semplice finestra del browser. Inoltre la possibilità di utilizzare HTML5, CSS3 e TypeScript (linguaggio derivato da Javascript) rendono l’esperienza di programmazione tramite Angular ancora più intuitiva e semplice.

Dal punto di vista server-side, si è scelto di utilizzare Spring Boot, un framework per lo sviluppo di applicazioni web basate su codice Java che offre un ulteriore livello di astrazione rispetto a Spring Framework, di più complicato utilizzo e adatto a progetti aziendali più elaborati.

La pagina di login possiede un tema “ospedaliero”, permettendo di eseguire le operazioni classiche, come l’autenticazione, il recupero password e l’accesso alla pagina di supporto.

![ScreenShot](/screenshots/4.jpg?raw=true)
______________________________________________________________________________________________________________________________

![ScreenShot](/screenshots/1.jpg?raw=true)

La piattaforma si suddivide in poche pagine, ma funzionali. Nella prima, dopo che viene effettuato l’accesso, l’utente si troverà dinanzi ad una dashboard che, grazie ad Angular 9 ed alla sua tecnologia di presentazione dei dati, gli “observable”, sempre aggiornata ed in tempo reale, è in continua comunicazione con il server.

![ScreenShot](/screenshots/5.jpg?raw=true)

In questa sezione l’operatore avrà la possibilità di visualizzare le informazioni del paziente ricoverato, tra cui la diagnosi e la data di entrata, ma avrà anche la possibilità di cambiare letto al paziente qualora fosse necessario o di dimettere il paziente dalla clinica.

![ScreenShot](/screenshots/6.jpg?raw=true)
______________________________________________________________________________________________________________________________

![ScreenShot](/screenshots/9.jpg?raw=true)

L’ultima schermata, ma non la meno importante, riguarda la visualizzazione dei pazienti. In questa sezione l’operatore avrà la possibilità di vedere TUTTI i pazienti, sia quelli attualmente in pronto soccorso, sia quelli in ricovero e infine quelli che sono stati dimessi.
È possibile inoltre eseguire dei filtri che permettono all’operatore di semplificare le ricerche.

![ScreenShot](/screenshots/7.jpg?raw=true)

Infine l’applicazione è stata arricchita con delle features che rendono l’esperienza più piacevole e dinamica.

![ScreenShot](/screenshots/8.jpg?raw=true)
______________________________________________________________________________________________________________________________

![ScreenShot](/screenshots/10.jpg?raw=true)


## Installing

Requisiti:

* Java​ (versione 8)
* Java JDK (v​ ersione 1.8)​
* IntelliJ IDEA Ultimate​ (versione 2020.3.1 o superiore)
* MySql server​ (versione 8.0.0 o superiore)
* MySql Workbench​ (versione 8.0.0 o superiore)
* Visual Studio Code
* Node.js​ (versione 14.15.5 LTS o superiore)
* Angular CLI (v​ ersione 9.1.13​)
* Browser web (è consigliato l’utilizzo di C​ hrome​)

Per la procedura di installazione, far riferimento al file "**Documentazione gestione ospedaliera**" presente all'interno della repository.

## Credits

Lorenzo Castorina & Andrea Potì

![ScreenShot](/screenshots/11.jpg?raw=true)