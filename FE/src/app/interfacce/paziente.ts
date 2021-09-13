interface Paziente {
    id: number,
    cf: String,
    nome: String,
    cognome: String,
    sesso: String,
    data_nascita: Date,
    luogo_nascita: String,
    nazionalita: String,
    data_entrata: Date,   // ultima volta che è entrata nel pronto soccorso
    data_dimmisione: Date, // ultima volta che un paziente è uscito dal pronto soccorso  // che coincide con la data di ricovero , se null non è in ricovero
    codice: String,
    diagnosi: String,
    documenti:  String[]
}
