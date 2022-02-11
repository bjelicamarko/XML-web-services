import { Drzavljanstvo } from "src/modules/shared/models/Drzavljanstvo";
import { Kontakt } from "src/modules/shared/models/Kontakt";
import { Vakcina } from "./Vakcina";

export interface Interesovanje {
    Interesovanje: {
        "@": {}
        Drzavljanstvo: Drzavljanstvo,
        Ime: string,
        Prezime: string,
        Kontakt: Kontakt,
        Opstina_vakcinisanja: string,
        Vakcina: Vakcina[],
        Dobrovoljni_davalac: string,
        Datum: string
    }
}