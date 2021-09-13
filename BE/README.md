Impostare il DB

Una volta scaricato l’installer di MySql server si può procedere selezionando il profilo “developer”, accettando i “Termini e condizioni” e terminare l’installazione. 
Durante l’installazione potrebbe richiedere di dover scaricare delle librerie per python e visual studio, ma nel nostro caso non sono necessarie.

Una volta scaricato il tutto aprire il cmd di windows con i permessi da amministratore e digitare  mysqld 
Su Mac/Linux basterà digitare sul terminale la stringa sudo service mysql start

A questo punto si aprirà MySql server dove sarà necessario solamente impostare una password per il profilo (nel nostro caso noi abbiamo utilizzato la pass: root)
ed accendere il server.
Per quanto riguarda MySql Workbench, una volta installato dal sito ed aperto, clicchiamo sul + per creare una nuova connessione, diamogli il nome che più desideriamo, impostiamo una password cliccando su “store in vault”, e testiamo la connessione al server.
Una volta fatto ciò all’interno dell’applicazione clicchiamo su “data import” per importare la struttura del database. Selezioniamo “import from self contained file” e selezioniamo il file presente a questo [link](https://drive.google.com/drive/folders/1vIrk5ZiSDN42EKEMdeFkdLzrJSST64Yc?usp=sharing)
Aggiungiamo un nuovo schema e digitiamo “gestione_ospedale”, una volta fatto ciò confermiamo ed importiamo il tutto.

Se tutto è andato a buon fine sarà possibile visualizzare la struttura del database ed eseguire delle query di prova.



Impostare il Back-end

Una volta scaricato Java dal sito ufficiale, possiamo continuare con il download della repository del progetto. 
Una volta clonata da Git o scaricata in formato zip, possiamo procedere con l’installazione di IntelliJ IDEA.

Nel nostro caso abbiamo utilizzato la versione ULTIMATE in quanto gratuita grazie alla partnership con il Politecnico di Milano. 
Impostiamo il launcher a 64 bit e selezioniamo l’opzione “add launcher DIR to the path”, clicchiamo next e finiamo l’installazione.

Apriamo la cartella del progetto tramite il launcher e aspettiamo che carichi tutte le dipendenze. Ora dovrebbe comparire l’opzione di scaricare Java JDK versione 1.8 (rilevata in automatico da IntelliJ IDEA), la scarichiamo e procediamo con l’avvio del server.
Se non da la possibilità di scaricare la JDK è possibile recarsi a questo link con una guida al download.

Se necessario impostare il DB, recarsi presso src/main/resources/application.properties all’interno del progetto, ed impostare username e password del DB MySql ed eventualmente cambiare l'URL allo schema creato precedentemente.



A questo punto rechiamoci presso src/main/java/com/ospedale/project/GestioneOspedaleApplication.java e clicchiamo sull’icona verde sulla sinistra del codice per avviare il server.

Se tutto è partito correttamente sarà possibile eseguire una chiamata di prova al server copiando ed incollando http://localhost:8080/paziente/getPazienti nel browser  vedendo se compare una lista con tutti i pazienti.
