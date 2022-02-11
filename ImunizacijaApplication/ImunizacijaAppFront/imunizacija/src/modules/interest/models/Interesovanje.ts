import { Drzavljanstvo } from "src/modules/shared/models/Drzavljanstvo";
import { Kontakt } from "src/modules/shared/models/Kontakt";
import { Vakcina } from "./Vakcina";

export interface Interesovanje {
    drzavljanstvo: Drzavljanstvo,
    ime: string,
    prezime: string,
    kontakt: Kontakt,
    opstinaVakcinisanja: string,
    vakcine: Vakcina[],
    dobrovoljniDalavac: string,
    datum: Date
}