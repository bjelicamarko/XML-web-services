import { Adresa } from "./Adresa";

export interface LicniPodaci {
    Ime: { "@": { }, 
        "#": string},
    Prezime: { "@": { },
        "#": string},
    Datum_rodjenja: { "@": { },
        "#": string},
    Pol: { "@": { }, 
        "#": string},
    Ime_roditelja: { "@": { },
        "#": string},
    Mesto_rodjenja: { "@": { },
        "#": string},
    Adresa: Adresa  
}